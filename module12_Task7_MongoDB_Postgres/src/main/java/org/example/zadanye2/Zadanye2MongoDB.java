package org.example.zadanye2;

import java.sql.SQLException;

// На сервере Docker создан контейнер с базой данных MongoDB с именем "mongoTest" при помощи
// команды в терминале среды разработки, например IntelliJ IDEA:
// "docker run --name mongoTest -d -p 27017:27017 mongo".
// Значения параметров для настройки соединения (пришли в ответ на команду в терминале: docker inspect mongoTest)
// Сервер (Хост): localhost
// Порт: 27017 (стандартный порт для MongoDB)
// База данных: mongoTest
// Коллекция: mongoTestCollection
// Пользователь: нет

public class Zadanye2MongoDB {
    public static void main(String[] args) throws SQLException {
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
                2. Создание контейнеров баз данных.

                Решение:
            \s""");

        System.out.println("""
            На сервере Docker создан контейнер с базой данных MongoDB с именем "mongoTest" при помощи
            команды в терминале среды разработки, например IntelliJ IDEA:
            "docker run --name mongoTest -d -p 27017:27017 mongo".
            \s""");
    }
}
