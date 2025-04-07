package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReportGenerator {
    // Точка входа для запуска класса отдельно (если нужно)
    public static void main(String[] args) {
        generateReport();
    }

    public static void generateReport() {
        // Данные для подключения к базе данных H2
        String jdbcUrl = "jdbc:h2:file:./calorieTrackerDB"; // Путь к базе данных
        String username = "sa";
        String password = "";

        // Имя пользователя для отчёта
        String userName = "Иван Иванов";

        // Дневная норма калорий
        double dailyCalorieNorm = 1420.00;

        // Путь к файлу отчёта
        String reportFilePath = "module17_TEST_Calorie-Tracker\\Отчет.txt";

        // Дата для отчёта (используем конкретную дату вместо CURRENT_DATE())
        String reportDate = "2025-03-01";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Шаг 1: Сбор данных для указанной даты
            String dailyQuery = "SELECT SUM(\"калории на порцию\") AS total_calories, COUNT(*) AS meals_count " +
                    "FROM \"ПОЛЬЗОВАТЕЛИ\" " +
                    "WHERE \"ДАТА\" = '" + reportDate + "' AND \"ИМЯ\" = '" + userName + "'";
            Statement statement = connection.createStatement();
            ResultSet dailyResultSet = statement.executeQuery(dailyQuery);

            double totalCalories = 0;
            int mealsCount = 0;

            if (dailyResultSet.next()) {
                totalCalories = dailyResultSet.getDouble("total_calories");
                mealsCount = dailyResultSet.getInt("meals_count");
            }

            // Шаг 2: Формирование отчёта
            StringBuilder report = new StringBuilder();
            report.append("Для пользователя: ").append(userName).append(", рассчитанная дневная норма калорий: ").append(String.format("%.2f", dailyCalorieNorm)).append(" калорий\n\n");

            report.append("Отчёт за день:\n");
            report.append("- Сумма всех калорий: ").append(totalCalories).append(" калорий\n");
            report.append("- Количество приёмов пищи: ").append(mealsCount).append("\n");

            report.append("\nПроверка нормы калорий:\n");
            if (totalCalories <= dailyCalorieNorm) {
                report.append("- Пользователь уложился в дневную норму калорий.\n");
            } else {
                report.append("- Пользователь превысил дневную норму калорий.\n");
            }

            report.append("\nИстория питания по дням:\n");
            String historyQuery = "SELECT \"ДАТА\", SUM(\"калории на порцию\") AS daily_calories, COUNT(*) AS daily_meals " +
                    "FROM \"ПОЛЬЗОВАТЕЛИ\" " +
                    "WHERE \"ИМЯ\" = '" + userName + "' " +
                    "GROUP BY \"ДАТА\" " +
                    "ORDER BY \"ДАТА\" DESC";
            ResultSet historyResultSet = statement.executeQuery(historyQuery);

            while (historyResultSet.next()) {
                String date = historyResultSet.getString("ДАТА");
                double dailyCalories = historyResultSet.getDouble("daily_calories");
                int dailyMeals = historyResultSet.getInt("daily_meals");
                report.append("- Дата: ").append(date).append(", Приёмов пищи: ").append(dailyMeals).append(", Калории: ").append(dailyCalories).append("\n");
            }

            // Шаг 3: Запись в файл (перезапись файла)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilePath))) {
                writer.write(report.toString());
                System.out.println("Отчёт успешно записан в файл: " + reportFilePath);
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
            }

            // Шаг 4: Скачивание файла
            Path filePath = Paths.get(reportFilePath);
            if (Files.exists(filePath)) {
                System.out.println("Файл сохранён в корневой папке проекта: " + filePath.toAbsolutePath());
            } else {
                System.err.println("Файл не найден: " + filePath.toAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Ошибка при работе с базой данных: " + e.getMessage());
        }
    }
}
