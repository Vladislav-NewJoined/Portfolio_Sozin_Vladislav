package org.example;

import com.sun.net.httpserver.HttpServer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        boolean connected = false;
        int retryCount = 0;

        while (!connected && retryCount < 5) {
            try {
                // Запуск Telegram бота
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

                // Создаем экземпляр бота
                MailingBot bot = new MailingBot();

                // Регистрируем бота
                botsApi.registerBot(bot);
                System.out.println("Бот запущен!");

                // Добавляем Shutdown Hook
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    System.out.println("Завершение работы бота...");
                    bot.onClosing();
                    System.out.println("Бот остановлен.");
                }));

                // Создание простого HTTP-сервера для Render
                startHttpServer();

                connected = true;
            } catch (TelegramApiException e) {
                if (e.getMessage() != null && e.getMessage().contains("Conflict") && e.getMessage().contains("409")) {
                    System.err.println("Обнаружен конфликт сессий. Ожидание перед повторной попыткой...");
                    retryCount++;

                    try {
                        // Ждем перед повторной попыткой
                        Thread.sleep(10000 * retryCount); // Увеличиваем время ожидания с каждой попыткой
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.err.println("Ошибка при запуске бота: " + e.getMessage());
                    e.printStackTrace();
                    break;
                }
            } catch (Exception e) {
                System.err.println("Непредвиденная ошибка: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }

        if (!connected) {
            System.err.println("Не удалось подключиться к Telegram API после нескольких попыток.");
        }
    }

    private static void startHttpServer() throws IOException {
        try {
            // Получаем порт из переменной окружения или используем 8080 по умолчанию
            String port = System.getenv("PORT");
            System.out.println("Получена переменная окружения PORT: " + port);

            if (port == null || port.isEmpty()) {
                port = "8080";
                System.out.println("Используется порт по умолчанию: " + port);
            }

            // Создаем HTTP-сервер на указанном порту
            System.out.println("Создание HTTP-сервера на порту: " + port);
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(port)), 0);

            // Добавляем простой обработчик для проверки работоспособности
            System.out.println("Добавление обработчика /health");
            server.createContext("/health", exchange -> {
                String response = "BES_Mailing_bot is running!";
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.getResponseBody().close();
                System.out.println("Обработан запрос к /health");
            });

            // Добавляем обработчик для корневого пути
            System.out.println("Добавление обработчика для корневого пути /");
            server.createContext("/", exchange -> {
                String response = "BES_Mailing_bot is running! Use /health for status check.";
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.getResponseBody().close();
                System.out.println("Обработан запрос к корневому пути /");
            });

            // Запускаем сервер
            System.out.println("Запуск HTTP-сервера...");
            server.start();
            System.out.println("HTTP сервер успешно запущен на порту " + port);
        } catch (Exception e) {
            System.err.println("Ошибка при запуске HTTP-сервера: " + e.getMessage());
            e.printStackTrace();
            throw e; // Перебрасываем исключение, чтобы оно было видно в логах
        }
    }
}
