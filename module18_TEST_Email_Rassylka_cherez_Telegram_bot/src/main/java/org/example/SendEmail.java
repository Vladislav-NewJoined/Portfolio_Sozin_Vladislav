package org.example;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class SendEmail {
    public static void main(String[] args) {
        // Список получателей
        List<String> recipientEmails = List.of(
                "sozin.vladislav@mail.ru",
                "sozin.vladislav@gmail.com"
        );

        String testText = "Тестовое сообщение через JavaMail. Чиглинцев Владимир.";

        // Асинхронная отправка
        sendEmailsAsync(recipientEmails, testText)
                .thenRun(() -> System.out.println("Отправка завершена!"))
                .exceptionally(e -> {
                    System.err.println("Ошибка: " + e.getMessage());
                    return null;
                });

        // Ожидание завершения
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        shutdown();
    }

    // Остальной код без изменений
    private static final String HOST = "smtp.mail.ru";
    private static final String PORT = "465";
    private static final String USERNAME = "sozin.vladislav@mail.ru";
    private static final String PASSWORD = "XE2u0sb8qFyJttebpXuK";
    private static final int DELAY_BETWEEN_EMAILS_MS = 200;

    private static final ExecutorService emailExecutor =
            Executors.newFixedThreadPool(5);

    public static CompletableFuture<Void> sendEmailsAsync(List<String> emails, String text) {
        return CompletableFuture.runAsync(() -> {
            try {
                Session session = createSession();
                for (String email : emails) {
                    sendSingleEmail(session, email, text);
                    Thread.sleep(DELAY_BETWEEN_EMAILS_MS);
                }
            } catch (Exception e) {
                throw new EmailSendingException("Ошибка рассылки: " + e.getMessage(), e);
            }
        }, emailExecutor);
    }

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.timeout", 5000);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }

    private static void sendSingleEmail(Session session, String toEmail, String text)
            throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Сообщение от BES Mailing Bot");
        message.setText(text);
        Transport.send(message);
    }

    public static void shutdown() {
        if (!emailExecutor.isShutdown()) {
            emailExecutor.shutdown();
            try {
                if (!emailExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                    emailExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                emailExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    public static class EmailSendingException extends RuntimeException {
        public EmailSendingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
