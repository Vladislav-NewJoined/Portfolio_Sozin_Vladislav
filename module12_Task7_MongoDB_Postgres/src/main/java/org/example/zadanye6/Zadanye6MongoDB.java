package org.example.zadanye6;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.SQLException;
import java.util.ArrayList;

// На сервере Docker создан контейнер с базой данных MongoDB с именем "mongoTest" при помощи
// команды в терминале среды разработки, например IntelliJ IDEA:
// "docker run --name mongoTest -d -p 27017:27017 mongo".
// Значения параметров для настройки соединения (можно получить в ответ на команду в терминале: docker inspect mongoTest)
// Сервер (Хост): localhost
// Порт: 27017 (стандартный порт для MongoDB)
// База данных: mongoTest
// Коллекция: mongoTestCollection
// Пользователь: нет
// Пароль: нет

public class Zadanye6MongoDB {
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
                6. Работа с выводом данных в консоль.

                Решение:\s""");

        System.out.println("""
            ВНИМАНИЕ: КОД ПО ВЫВОДУ ДАННЫХ В КОНСОЛЬ ЗАПИСАН В КЛАССЕ Zadanye6Postgres.\s""");

        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("mongoTest");

            // Удаление предыдущей коллекции, если она существует
            if (database.listCollectionNames().into(new ArrayList<>()).contains("mongoTestCollection")) {
                database.getCollection("mongoTestCollection").drop();
            }

            // Создание новой коллекции "mongoTestCollection"
            MongoCollection<Document> collection = database.getCollection("mongoTestCollection");

            Document doc = new Document("firstName", "Steben").append("age", 26).append("city", "Paris");
            collection.insertOne(doc);

            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
