package org.example.zadanye1;

// На сервере Docker создан контейнер с базой данных PostgreSQL с именем "postgresTest" при помощи
// команды в терминале Docker Desktop или в терминале среды разработки, например IntelliJ IDEA:
// "docker run --name postgresTest -d -p 5432:5432 -e POSTGRES_DB=somedbPGtest -e POSTGRES_USER=someuser
//  -e POSTGRES_PASSWORD=123 postgres:alpine".
// В приложении DBeaver создано соединение с базой данных с именем "somedbPGtest".
// Для настройки соединения в DBeaver использованы следующие параметры (можно получить в ответ на команду в терминале: docker inspect postgresTest):
// Сервер (Хост): localhost (для соединения не со своего компьютера: 172.17.0.2
// Порт: 5432
// База данных: somedbPGtest
// Пользователь: someuser
// Пароль: 123
// Для проверки настроек можно сделать такой тестовый запрос:  "select * from users1" в DB Browser в папке "Consoles -→ somedbPGtest"

public class Zadanye1Postgres {
    public static void main(String[] args) {
        System.out.println("""
            Задание:\s
            Модуль 12. Базы данных и Git. Задание №7, Проект:\s
                Общая задача создания проекта
                    Создать подключение к реляционной базе данных Postgres и к не реляционной
                    базе MongoDb.
                    Суть функции проекта сводится к сохранению мета-информации каждого запроса
                    в отдельную базу данных без заранее закрепленной структуры, в то время как
                    основная информация будет поступать в реляционную базу Postgres.
                Задание:
                1. Продумывание структуры проекта.

                Решение:
            \s""");

        System.out.println("""
            Для организации структуры проекта я планирую создать отдельные пакеты для каждой
            задачи, разделив их на реляционную и нереляционную части для удобства управления.
            Каждый пакет будет содержать соответствующие классы для работы с базами данных
            Postgres и MongoDb.
            Я планирую организовать их разместив в шести папках (‘Package’), соответствующих
            заданиям проекта.
            Они будут находится внутри общей папки "task12_7_1".
            Папки будут иметь следующие названия:
            zadanye1
            zadanye2
            …
            zadanye6
            
            Непосредственно сами классы содержащие java-код будут иметь следующие названия:
            Zadanye1Postgres
            Zadanye1MongoDb
            ..
            Zadanye2Postgres
            Zadanye2MongoDb
            ..
            Zadanye3Postgres
            Zadanye3MongoDb
            и так далее.
            
            Такая структура проекта позволит эффективно реализовать задачу сохранения мета-информации
            запросов и обработки основной информации в соответствующих базах данных.
            \s""");
    }
}
