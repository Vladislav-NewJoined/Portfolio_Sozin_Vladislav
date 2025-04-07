package org.example.config;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.ExcelExportService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ExcelExportService excelExportService;

    @PostConstruct
    @Transactional
    public void init() {
        log.info("Инициализация данных...");

        // Удаляем все старые строки
        userRepository.deleteAllUsers();
        userRepository.resetIdSequence(); // Сброс счётчика ID
        log.info("Все старые строки удалены. Счётчик ID сброшен.");

        // Добавляем новую строку
        User user = new User();
        user.setName("Иван Иванов");
        user.setEmail("ivan@example.com");
        user.setAge(30);
        user.setWeight(80.0);
        user.setHeight(180.0);
        user.setGoal(User.Goal.LOSE_WEIGHT);
        user.setGender(User.Gender.MALE);
        user.setActivityLevel(User.ActivityLevel.MODERATELY_ACTIVE);

        userRepository.save(user);
        log.info("Данные инициализированы.");

        // Рассчитываем дневную норму калорий
        double dailyCalories = userService.calculateDailyCalories(user);
        String formattedCalories = String.format("%.2f", dailyCalories);
        System.out.println("Для пользователя: " + user.getName() + ", рассчитанная дневная норма калорий: " + formattedCalories + " калорий");

        // Экспорт таблицы ПОЛЬЗОВАТЕЛИ в Excel
        try {
            excelExportService.exportToExcel();
            System.out.println("Таблица ПОЛЬЗОВАТЕЛИ успешно экспортирована в Excel.");
        } catch (Exception e) {
            System.err.println("Ошибка при экспорте таблицы в Excel: " + e.getMessage());
        }
    }
}
