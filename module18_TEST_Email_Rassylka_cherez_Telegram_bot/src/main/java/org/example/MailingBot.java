package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MailingBot extends TelegramLongPollingBot {
    private final List<String> emailList = new ArrayList<>();
    private boolean waitingForEmails = false;
    private boolean waitingForSubject = false;
    private boolean waitingForMessage = false;
    private boolean waitingForAttachmentAnswer = false;
    private boolean waitingForAttachment = false;
    private boolean waitingForInlineImageAnswer = false;
    private boolean waitingForInlineImage = false;

    private String emailSubject = "";
    private String emailText = "";
    private File attachment = null;
    private File inlineImage = null;

    private static final int MAX_EMAILS_PER_REQUEST = 100;
    private static final String TEMP_DIR = "temp_files";

    public MailingBot() {
        // Создаем временную директорию для файлов
        try {
            Files.createDirectories(Paths.get(TEMP_DIR));
        } catch (IOException e) {
            System.err.println("Не удалось создать временную директорию: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return "BES_Mailing_bot";
    }

    @Override
    public String getBotToken() {
        return "7658983765:AAFaNCkR8D5O74u-YkMVZyjxuePRXY1NZ64";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Получено обновление: " + update);

        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();

            // Обработка документов (вложений)
            if (waitingForAttachment && update.getMessage().hasDocument()) {
                processAttachment(chatId, update.getMessage().getDocument());
                return;
            }

            // Обработка фотографий (встроенных изображений)
            if (waitingForInlineImage && update.getMessage().hasPhoto()) {
                processInlineImage(chatId, update.getMessage().getPhoto());
                return;
            }

            // Обработка текстовых сообщений
            if (update.getMessage().hasText()) {
                String userInput = update.getMessage().getText();
                System.out.println("Получено сообщение: '" + userInput + "' от пользователя с chatId: " + chatId);

                try {
                    if ("/start".equals(userInput)) {
                        System.out.println("Обработка команды /start");
                        resetState(chatId);
                        System.out.println("Команда /start обработана успешно");
                    } else if (waitingForEmails) {
                        System.out.println("Обработка списка email-адресов");
                        processEmailList(chatId, userInput);
                    } else if (waitingForSubject) {
                        System.out.println("Обработка темы сообщения");
                        processSubject(chatId, userInput);
                    } else if (waitingForMessage) {
                        System.out.println("Обработка текста сообщения");
                        processMessage(chatId, userInput);
                    } else if (waitingForAttachmentAnswer) {
                        System.out.println("Обработка ответа о вложении");
                        processAttachmentAnswer(chatId, userInput);
                    } else if (waitingForInlineImageAnswer) {
                        System.out.println("Обработка ответа о встроенном изображении");
                        processInlineImageAnswer(chatId, userInput);
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка при обработке сообщения: " + e.getMessage());
                    e.printStackTrace();

                    // Отправляем сообщение об ошибке пользователю
                    sendMessage(chatId, "❌ Произошла ошибка: " + e.getMessage());
                }
            } else if (!waitingForAttachment && !waitingForInlineImage) {
                sendMessage(chatId, "⚠️ Пожалуйста, отправьте текстовое сообщение.");
            }
        }
    }

    private void resetState(long chatId) {
        emailList.clear();
        emailSubject = "";
        emailText = "";
        deleteTemporaryFiles();
        attachment = null;
        inlineImage = null;

        waitingForEmails = true;
        waitingForSubject = false;
        waitingForMessage = false;
        waitingForAttachmentAnswer = false;
        waitingForAttachment = false;
        waitingForInlineImageAnswer = false;
        waitingForInlineImage = false;

        sendMessage(chatId, "📩 Введите список email-адресов (каждый с новой строки):");
    }

    private void processEmailList(long chatId, String input) {
        String[] emails = input.split("\n");
        for (String email : emails) {
            String trimmedEmail = email.trim();
            if (!trimmedEmail.isEmpty()) {
                emailList.add(trimmedEmail);
            }
        }

        if (emailList.isEmpty()) {
            sendMessage(chatId, "❌ Список email-адресов пуст. Попробуйте снова.");
            return;
        }

        if (emailList.size() > MAX_EMAILS_PER_REQUEST) {
            sendMessage(chatId, String.format(
                    "⚠️ Вы ввели %d адресов. Лимит: %d за раз.\nОтправьте меньше адресов.",
                    emailList.size(),
                    MAX_EMAILS_PER_REQUEST
            ));
            emailList.clear();
            return;
        }

        waitingForEmails = false;
        waitingForSubject = true;
        sendMessage(chatId, "📝 Теперь введите тему сообщения:");
    }

    private void processSubject(long chatId, String subject) {
        emailSubject = subject.trim();

        if (emailSubject.isEmpty()) {
            sendMessage(chatId, "⚠️ Тема сообщения не может быть пустой. Пожалуйста, введите тему:");
            return;
        }

        waitingForSubject = false;
        waitingForMessage = true;
        sendMessage(chatId, "✍️ Теперь введите текст сообщения:");
    }

    private void processMessage(long chatId, String messageText) {
        emailText = messageText;

        waitingForMessage = false;
        waitingForAttachmentAnswer = true;

        // Создаем клавиатуру с кнопками Да/Нет
        sendMessageWithYesNoKeyboard(chatId, "📎 Хотите прикрепить файл к сообщению?");
    }

    private void processAttachmentAnswer(long chatId, String answer) {
        waitingForAttachmentAnswer = false;

        if (answer.equalsIgnoreCase("Да")) {
            waitingForAttachment = true;
            sendMessage(chatId, "📤 Пожалуйста, отправьте файл, который нужно прикрепить к сообщению.");
        } else {
            // Переходим к вопросу о встроенном изображении
            waitingForInlineImageAnswer = true;
            sendMessageWithYesNoKeyboard(chatId, "🖼️ Хотите добавить изображение в тело сообщения?");
        }
    }

    private void processAttachment(long chatId, Document document) {
        try {
            // Получаем файл из Telegram
            org.telegram.telegrambots.meta.api.objects.File telegramFile = execute(new GetFile(document.getFileId()));
            String filePath = telegramFile.getFilePath();

            // Создаем временный файл
            String fileName = document.getFileName();
            if (fileName == null || fileName.isEmpty()) {
                fileName = "attachment_" + System.currentTimeMillis();
            }

            File localFile = new File(TEMP_DIR, fileName);
            downloadFile(telegramFile, localFile);

            attachment = localFile;
            waitingForAttachment = false;
            waitingForAttachment = false;

            // Переходим к вопросу о встроенном изображении
            waitingForInlineImageAnswer = true;
            sendMessageWithYesNoKeyboard(chatId, "🖼️ Хотите добавить изображение в тело сообщения?");

        } catch (TelegramApiException e) {
            System.err.println("Ошибка при загрузке файла: " + e.getMessage());
            sendMessage(chatId, "❌ Не удалось загрузить файл. Пожалуйста, попробуйте еще раз или отправьте 'Нет' для пропуска.");
        }
    }

    private void processInlineImageAnswer(long chatId, String answer) {
        waitingForInlineImageAnswer = false;

        if (answer.equalsIgnoreCase("Да")) {
            waitingForInlineImage = true;
            sendMessage(chatId, "🖼️ Пожалуйста, отправьте изображение, которое нужно вставить в тело сообщения.");
        } else {
            // Переходим к отправке сообщения
            sendEmailsToRecipients(chatId);
        }
    }

    private void processInlineImage(long chatId, List<PhotoSize> photos) {
        try {
            // Получаем самое большое фото (лучшее качество)
            PhotoSize photo = photos.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElseThrow(() -> new IllegalStateException("Не удалось получить фото"));

            // Получаем файл из Telegram
            org.telegram.telegrambots.meta.api.objects.File telegramFile = execute(new GetFile(photo.getFileId()));
            String filePath = telegramFile.getFilePath();

            // Создаем временный файл
            String fileName = "inline_image_" + System.currentTimeMillis() + ".jpg";
            File localFile = new File(TEMP_DIR, fileName);
            downloadFile(telegramFile, localFile);

            inlineImage = localFile;
            waitingForInlineImage = false;

            // Переходим к отправке сообщения
            sendEmailsToRecipients(chatId);

        } catch (TelegramApiException e) {
            System.err.println("Ошибка при загрузке изображения: " + e.getMessage());
            sendMessage(chatId, "❌ Не удалось загрузить изображение. Пожалуйста, попробуйте еще раз или отправьте 'Нет' для пропуска.");
        }
    }

    private void sendEmailsToRecipients(long chatId) {
        sendMessage(chatId, "⏳ Идет рассылка " + emailList.size() + " писем...");

        SendEmail.sendEmailsAsync(emailList, emailSubject, emailText, attachment, inlineImage)
                .thenRun(() -> {
                    sendMessage(chatId, "✅ Рассылка успешно завершена!");
                    clearState();
                })
                .exceptionally(e -> {
                    sendMessage(chatId, "❌ Ошибка при отправке: " + e.getCause().getMessage());
                    clearState();
                    return null;
                });
    }

    private void clearState() {
        emailList.clear();
        emailSubject = "";
        emailText = "";
        deleteTemporaryFiles();
        attachment = null;
        inlineImage = null;

        waitingForEmails = false;
        waitingForSubject = false;
        waitingForMessage = false;
        waitingForAttachmentAnswer = false;
        waitingForAttachment = false;
        waitingForInlineImageAnswer = false;
        waitingForInlineImage = false;
    }

    private void deleteTemporaryFiles() {
        if (attachment != null && attachment.exists()) {
            attachment.delete();
        }
        if (inlineImage != null && inlineImage.exists()) {
            inlineImage.delete();
        }
    }

    private void sendMessage(long chatId, String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(String.valueOf(chatId))
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }

    private void sendMessageWithYesNoKeyboard(long chatId, String text) {
        try {
            // Создаем клавиатуру
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            keyboardMarkup.setSelective(true);
            keyboardMarkup.setResizeKeyboard(true);
            keyboardMarkup.setOneTimeKeyboard(true);

            // Создаем строку кнопок
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton("Да"));
            row.add(new KeyboardButton("Нет"));
            keyboard.add(row);

            keyboardMarkup.setKeyboard(keyboard);

            // Отправляем сообщение с клавиатурой
            execute(SendMessage.builder()
                    .chatId(String.valueOf(chatId))
                    .text(text)
                    .replyMarkup(keyboardMarkup)
                    .build());
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения с клавиатурой: " + e.getMessage());
        }
    }

    @Override
    public void onClosing() {
        deleteTemporaryFiles();
        SendEmail.shutdown();
        super.onClosing();
    }
}
