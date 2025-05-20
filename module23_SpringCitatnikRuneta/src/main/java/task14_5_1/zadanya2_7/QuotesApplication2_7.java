package task14_5_1.zadanya2_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import task14_5_1.zadanya2_7.services.QuoteService;

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
@EntityScan(basePackages = "task14_5_1.zadanya2_7.models")
@ComponentScan(basePackages = {"task14_5_1.zadanya2_7.services", "task14_5_1.zadanya2_7.repositories"})
public class QuotesApplication2_7 implements CommandLineRunner {

	@Autowired
    QuoteService service;

	public static void main(String[] args) {
		System.out.println("""
            Модуль 14. Spring. Задание №5. Проект:\s
                Задания:
                2. Расширение возможностей парсера для получение конкретной страницы выдачи, а также конкретной
                   цитаты.
                3. Продумывание структуры проекта.
                4. Создание контейнеров базы данных.
                5. Создание конфигов подключения к базе вместе со слоем данных.
                6. Создание сервиса для управления данными.
                7. Написание контроллера для свободного получения цитат по странице, конкретную по id.

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
           );\n\s""");

		System.out.println("""
           Результаты данного класса (т.е. цитаты) сохранены в таблице "Quotes2".
           \s""");

		SpringApplication.run(QuotesApplication2_7.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var index = service.getIndex();
		System.out.println(index);
	}
}
