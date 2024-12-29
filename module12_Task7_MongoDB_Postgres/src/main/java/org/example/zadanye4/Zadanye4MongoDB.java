package org.example.zadanye4;

import org.bson.Document;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
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

public class Zadanye4MongoDB {
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
                4. Создание слоя с данными.

                Решение:\s""");

        System.out.println("""
            На сервере Docker создан контейнер с базой данных MongoDB с именем "mongoTest" при помощи
            команды в терминале среды разработки, например IntelliJ IDEA:
            "docker run --name mongoTest -d -p 27017:27017 mongo".
            
            В ‘MongoDB Compass’ создано соединение ‘localhost:27017’, в котором создана база данных
            ‘mongoTest’, в которой создана коллекция ‘mongoTestCollection’. URL этого соединения:
            mongodb://localhost:27017/mongoTest
            URL непосредственно базы данных 'mongoTest’ такое: ‘mongodb://localhost:27017/mongoTest’
            (как правильно, на практике нигде не используется.
            
            Значения параметров для настройки соединения (можно получить в ответ на команду в терминале:
            'docker inspect mongoTest')
            Сервер (Хост): localhost
            Порт: 27017 (стандартный порт для MongoDB)
            База данных: mongoTest
            Коллекция: mongoTestCollection
            Пользователь: нет
            Пароль: нет\s""");

        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("mongoTest");

            // Удаление предыдущей коллекции, если она существует
            if (database.listCollectionNames().into(new ArrayList<>()).contains("mongoTestCollection")) {
                database.getCollection("mongoTestCollection").drop();
                System.out.println("Предыдущая коллекция успешно удалена");
            }

            // Создание новой коллекции "mongoTestCollection"
            MongoCollection<Document> collection = database.getCollection("mongoTestCollection");

            Document doc = new Document("firstName", "Alice").append("age", 26).append("city", "Paris");
            collection.insertOne(doc);

            System.out.println("Документ успешно добавлен в коллекцию 'mongoTestCollection'");
            System.out.println("\nСлой с данными успешно создан");

            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
