package org.example;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.ExcelExportService;
import org.example.service.TextExportService;
import org.example.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.LocalDate;

@SpringBootApplication(scanBasePackages = {"org.example", "org.example.config"})
public class Application {
	public static void main(String[] args) {

		System.out.println("ВНИМАНИЕ: 	Инструкция по использованию программного кода этого проекта находится в файле:  README_ИНСТРУКЦИИ.txt, в корневой папке проекта");

		// Информация о H2 Console
		System.out.println("Сайт-консоль, где отображается база данных и результаты обработки базы данных: http://localhost:8080/h2-console");
		System.out.println("При входе на страницу: http://localhost:8080/h2-console");
		System.out.println("Укажите следующие параметры:");
		System.out.println("Saved Settings: Generic H2 (Embedded)");
		System.out.println("Setting Name: Generic H2 (Embedded)");
		System.out.println("Driver Class: org.h2.Driver");
		System.out.println("JDBC URL: jdbc:h2:file:./calorieTrackerDB");
		System.out.println("User Name: sa");
		System.out.println("Password: (оставьте поле пустым)");
		System.out.println("После запуска главного класса база данных экспортируется в таблицу Excel, в корневую папку проекта.");
		System.out.println("Для экспорта таблицы Excel в корневую папку проекта, зайдите в браузере по адресу: http://localhost:8080/export/excel");
		System.out.println("Для извлечения/просмотра данных из таблицы, например \"ПОЛЬЗОВАТЕЛИ\", в \"H2 Console\" нужно использовать следующий SQL запрос: SELECT * FROM \"ПОЛЬЗОВАТЕЛИ\";");

		// Запуск приложения
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserService userService, UserRepository userRepository, ExcelExportService excelExportService, TextExportService textExportService) {
		return args -> {
			// Удаляем все старые строки
			userRepository.deleteAll();
			System.out.println("Удалены все старые строки из таблицы ПОЛЬЗОВАТЕЛИ.");

			// Сбрасываем счётчик ID
			userRepository.resetIdSequence();
			System.out.println("Счётчик ID сброшен. Новые строки будут начинаться с ID = 1.");

			// Создаем тестового пользователя
			User user = new User();
			user.setName("Иван Иванов");
			user.setEmail("ivan@example.com");
			user.setAge(30);
			user.setWeight(80.0);
			user.setHeight(180.0);
			user.setGoal(User.Goal.LOSE_WEIGHT);
			user.setGender(User.Gender.MALE);
			user.setActivityLevel(User.ActivityLevel.MODERATELY_ACTIVE);
			user.setDate(LocalDate.of(2025, 3, 1)); // Устанавливаем значение для нового столбца

			// Сохраняем пользователя в базу данных
			userRepository.save(user);
			System.out.println("Добавлена новая строка в таблицу ПОЛЬЗОВАТЕЛИ вместо старой строки.");

			// Рассчитываем дневную норму калорий
			double dailyCalories = userService.calculateDailyCalories(user);
			String formattedCalories = String.format("%.2f", dailyCalories);
			String message = "Для пользователя: " + user.getName() + ", рассчитанная дневная норма калорий: " + formattedCalories + " калорий";

			// Выводим фразу в консоль
			System.out.println(message);

			// Записываем фразу в текстовый файл
			try {
				String fileName = textExportService.exportToText(message);
				System.out.println("Фраза успешно записана в текстовый файл: " + fileName);
			} catch (IOException e) {
				System.err.println("Ошибка при записи в текстовый файл: " + e.getMessage());
			}

			// Экспорт таблицы ПОЛЬЗОВАТЕЛИ в Excel
			try {
				excelExportService.exportToExcel();
				System.out.println("Таблица ПОЛЬЗОВАТЕЛИ успешно экспортирована в Excel.");
			} catch (IOException e) {
				System.err.println("Ошибка при экспорте таблицы в Excel: " + e.getMessage());
			}

			// Генерация отчёта
			ReportGenerator.generateReport();
		};
	}
}