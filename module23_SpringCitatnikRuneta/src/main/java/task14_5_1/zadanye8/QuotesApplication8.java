package task14_5_1.zadanye8;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

// Здесь выполнять инициализацию проекта: https://start.spring.io/

// Здесь инструкции, как работать с Postman:
// https://www.youtube.com/watch?v=S142sn__F_0
// https://www.youtube.com/watch?v=A_jGdrGRLd0

//Данные для Postman
//https://www.youtube.com/watch?v=S142sn__F_0
//Ссылка на скачивание Postman - https://www.postman.com/downloads/
//Ссылка на скачивание кода API - https://github.com/bestLessons/postma...
//sozin.vladislav@mail.ru
//sozinvladislav
//пароль 1234567

// В качестве тестовой используется эта база данных 'Postgress':
// Сначала нужно подключить Docker
// В Docker'е подключение (контейнер) называется:
// postgresTest2
// 3dfb0f477632946d5dde15a0728bfadbd431bc14431e59e0caca68c468241cf0
// База данных в приложении DBeaver: somedbPGtest2
// URL базы данных: jdbc:postgresql://localhost:5432/somedbPGtest2
// Пользователь: someuser
// Пароль: 123

@SpringBootApplication
public class QuotesApplication8 implements CommandLineRunner {

    public static void main(String[] args) {
        System.out.println("""
            Модуль 14. Spring. Задание №5. Проект:\s
                Задания:
                8. Написание контроллера бота Telegram с сохранением юзера в соответствующую таблицу, методы
                   итерирования “вперед”/”назад” по цитатам в интерфейсе бота.

                Решение:\s""");

        System.out.println("""
           Цитаты берём с этого сайта: http://ibash.org.ru/\n""");

        System.out.println("""
           При помощи следующей команды в терминале IntelliJ IDEA:
           docker run --name postgresTest2 -d -p 5432:5432 -e POSTGRES_DB=somedbPGtest2 -e POSTGRES_USER=someuser -e POSTGRES_PASSWORD=123 postgres:alpine
           Создан контейнер в Docker Desktop:
           Имя контейнера: postgresTest2
           ID контейнера: 3dfb0f477632946d5dde15a0728bfadbd431bc14431e59e0caca68c468241cf0
           Также создана база данных PostgreSQL в DBeaver:
           Имя базы данных: somedbPGtest2
           URL базы данных: jdbc:postgresql://localhost:5432/somedbPGtest2
           Пользователь: someuser
           Пароль: 123
           IMAGE базы данных: postgres:alpine
           В DB Browser в проекте в IntelliJ IDEA создано подключение к этой базе данных.
           \s""");

        System.out.println("""
           Данные для Postman
           почта при регистрации: sozin.vladislav@mail.ru
           имя пользователя: sozinvladislav
           пароль 1234567\n""");

        System.out.println("""
           Для данного проекта cоздан тестовый Telegram бот:
           botName: TestBot_005
           userName: @kkkllll_005_bot или kkkllll_005_bot
           token: 6882256834:AAH5Fg-wUdKw7Rdqj8s9kXDgVt0R08tDnlY
           ChatId бота: "5799431854".
           \s""");

        System.out.println("""
           Для проверки создания таблиц,
           создаём и выполняем в приложении Dbeaver в окне 'Редактор SQL' или 'Script'
           следующие запросы:\s

           -- Удаление таблицы "Quotes2" при ее существовании
           DROP TABLE IF EXISTS "Quotes2";
           
           -- Создание таблицы "Quotes2" с колонкой "quoteid"
           CREATE TABLE "Quotes2" (
               id SERIAL PRIMARY KEY,
               "text" TEXT NOT NULL,
               "quoteid" INT4 NOT NULL  -- Добавлена колонка "quoteid" типа int4
           );
           
           -- Удаление таблицы "Chats2" при ее существовании
           DROP TABLE IF EXISTS "Chats2";
           
           -- Создание таблицы "Chats2"
           CREATE TABLE "Chats2" (
               id BIGSERIAL PRIMARY KEY,
               "chatId" BIGINT NOT NULL,
               "lastId" INT NOT NULL
           );
           
           -- Удаление таблицы "Users" при ее существовании
           DROP TABLE IF EXISTS "Users";
           
           -- Создание таблицы "Users"
           CREATE TABLE "Users" (
               id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
               name VARCHAR(20) NOT NULL
           );
           
           -- Удаление таблицы "Contacts" при ее существовании
           DROP TABLE IF EXISTS "Contacts";
           
           -- Создание таблицы "Contacts"
           CREATE TABLE "Contacts" (
               id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
               "customerId" INT,
               "contactName" VARCHAR(255) NOT NULL,
               phone VARCHAR(15),
               email VARCHAR(100)
           );
           );\s""");

        System.out.println("""
           Чтобы бот начал свою работу, открываем его и отправляем команду "/start".
           Впоследствии можем отправлять команды: "/next", /prev", "/rand".
           \s""");


        SpringApplication.run(QuotesApplication8.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
