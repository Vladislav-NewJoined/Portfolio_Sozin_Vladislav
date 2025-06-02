package task12_2_1.zadanye6;

import java.sql.*;

public class Zadanye6 {
    public static void main(String[] args) {
        System.out.println("""
            Задание:\s
            Модуль 12. Базы данных и Git. Задание №2:\s
            Цель задания: знакомство и формирование базовых навыков с по работе  с SQLite\s
                Задание:
                6. Напишите запрос, чтобы выбрать первые 5 записей из таблицы.
            
                Решение:
            \s""");

        String url = "jdbc:sqlite:src/main/java/task12_2_1/sqlite.dbase";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Создание таблицы 'Users'
            String createTableQuery = "create table if not exists Users (" +
                    "id integer primary key autoincrement, " +
                    "first_name varchar(20) not null, " +
                    "last_name varchar(20) not null);";
            stmt.execute(createTableQuery);

            // Удаление всех строк из таблицы Users
            String deleteDataQuery = "DELETE FROM Users";
            stmt.execute(deleteDataQuery); // Удаление всех строк из таблицы Users

            // Сброс автоинкрементного значения для таблицы Users, чтобы в новом запросе таблица заполнялась с нуля.
            String resetAutoIncrementQuery = "DELETE FROM sqlite_sequence WHERE name='Users'";
            stmt.execute(resetAutoIncrementQuery);

            // Вставка данных в таблицу 'Users'
            String insertDataQuery = "insert into Users (first_name, last_name) values " +
                    "('Petya', 'Ivanov'), " +
                    "('Vasya', 'Petrov'), " +
                    "('Katya', 'Sidorova'), " +
                    "('Sasha', 'Chernov'), " +
                    "('Pasha', 'Belov'), " +
                    "('Misha', 'Smirnov');";
//            System.out.println(insertDataQuery);
            stmt.execute(insertDataQuery);

            // Запрос на выборку данных
            String selectQuery = "select id, first_name, last_name from Users LIMIT 5";
            ResultSet rs = stmt.executeQuery(selectQuery);

            // Вывод результатов запроса
            while (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                System.out.println("ID: " + id + ", Имя: " + first_name + ", Фамилия: " + last_name);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения SQL запроса: " + e.getMessage());
        }
    }
}
