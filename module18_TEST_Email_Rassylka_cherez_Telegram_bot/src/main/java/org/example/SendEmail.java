package org.example;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class SendEmail {
    private static final String HOST = "smtp.mail.ru";
    private static final String PORT = "465";
    private static final String USERNAME = "sozin.vladislav@mail.ru";
    private static final String PASSWORD = "XE2u0sb8qFyJttebpXuK";
    private static final int DELAY_BETWEEN_EMAILS_MS = 200;
    private static final ExecutorService emailExecutor =
            Executors.newFixedThreadPool(5);

    // Обновляем существующий метод, чтобы он поддерживал старые вызовы
    public static CompletableFuture<Void> sendEmailsAsync(List<String> emails, String subject, String text) {
        return sendEmailsAsync(emails, subject, text, null, null);
    }

    // Добавляем новый метод с поддержкой вложений и изображений
    public static CompletableFuture<Void> sendEmailsAsync(List<String> emails, String subject, String text,
                                                          File attachment, File inlineImage) {
        return CompletableFuture.runAsync(() -> {
            try {
                Session session = createSession();
                for (String email : emails) {
                    sendSingleEmail(session, email, subject, text, attachment, inlineImage);
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

    // Обновляем существующий метод, чтобы он поддерживал старые вызовы
    private static void sendSingleEmail(Session session, String toEmail, String subject, String text)
            throws MessagingException {
        sendSingleEmail(session, toEmail, subject, text, null, null);
    }

    private static void sendSingleEmail(Session session, String toEmail, String subject, String text,
                                        File attachment, File inlineImage) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);

        // Если нет ни вложений, ни встроенных изображений, отправляем простой текст
        if (attachment == null && inlineImage == null) {
            message.setText(text);
            Transport.send(message);
            return;
        }

        // Создаем multipart сообщение
        Multipart multipart = new MimeMultipart("related");

        // Первая часть - текст/HTML
        BodyPart messageBodyPart = new MimeBodyPart();

        // Если есть встроенное изображение, используем HTML
        if (inlineImage != null) {
            String htmlContent = text + "<br><br><img src=\"cid:image\">";
            messageBodyPart.setContent(htmlContent, "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            // Добавляем изображение
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(inlineImage);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            messageBodyPart.setDisposition(MimeBodyPart.INLINE);
            multipart.addBodyPart(messageBodyPart);
        } else {
            // Просто текст
            messageBodyPart.setText(text);
            multipart.addBodyPart(messageBodyPart);
        }

        // Добавляем вложение, если оно есть
        if (attachment != null) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(attachment.getName());
            multipart.addBodyPart(attachmentPart);
        }

        // Устанавливаем содержимое сообщения
        message.setContent(multipart);

        // Отправляем сообщение
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
