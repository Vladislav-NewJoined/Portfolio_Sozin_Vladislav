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
    private boolean waitingForMessage = false;
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
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        long chatId = update.getMessage().getChatId();
        String userInput = update.getMessage().getText();

        if ("/start".equals(userInput)) {
            resetState(chatId);
        } else if (waitingForEmails) {
            processEmailList(chatId, userInput);
        } else if (waitingForMessage) {
            processMessage(chatId, userInput);
        }
    }

    private void resetState(long chatId) {
        emailList.clear();
        waitingForEmails = true;
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
        waitingForMessage = true;
        sendMessage(chatId, "✍️ Теперь введите текст сообщения:");
    }

    private void processMessage(long chatId, String messageText) {
        sendMessage(chatId, "⏳ Идет рассылка " + emailList.size() + " писем...");

        SendEmail.sendEmailsAsync(emailList, messageText)
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
        waitingForEmails = false;
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
