package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MailingBot extends TelegramLongPollingBot {
    private final List<String> emailList = new ArrayList<>();
    private boolean waitingForEmails = false;
    private boolean waitingForSubject = false;
    private boolean waitingForMessage = false;
    private String emailSubject = "";
    private static final int MAX_EMAILS_PER_REQUEST = 100;

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

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            System.out.println("Обновление не содержит текстового сообщения");
            return;
        }

        long chatId = update.getMessage().getChatId();
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
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обработке сообщения: " + e.getMessage());
            e.printStackTrace();

            // Отправляем сообщение об ошибке пользователю
            sendMessage(chatId, "❌ Произошла ошибка: " + e.getMessage());
        }
    }

    private void resetState(long chatId) {
        emailList.clear();
        emailSubject = "";
        waitingForEmails = true;
        waitingForSubject = false;
        waitingForMessage = false;
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
        sendMessage(chatId, "⏳ Идет рассылка " + emailList.size() + " писем...");

        SendEmail.sendEmailsAsync(emailList, emailSubject, messageText)
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
        waitingForEmails = false;
        waitingForSubject = false;
        waitingForMessage = false;
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

    @Override
    public void onClosing() {
        SendEmail.shutdown();
        super.onClosing();
    }
}
