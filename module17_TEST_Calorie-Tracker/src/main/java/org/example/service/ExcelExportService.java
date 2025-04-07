package org.example.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    private final UserRepository userRepository;

    public ExcelExportService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String exportToExcel() throws IOException {
        // Получаем всех пользователей из базы данных
        List<User> users = userRepository.findAll();

        // Создаем новый Excel-документ
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ПОЛЬЗОВАТЕЛИ");

        // Создаем заголовки столбцов
        Row headerRow = sheet.createRow(0);
        String[] columns = {
                "ID", "Имя", "Email", "Цель", "Рост (см)", "Вес (кг)", "Уровень активности", "Пол",
                "Калории на порцию", "Углеводы", "Жиры", "Название", "Белки", "Возраст", "Дата"
        };
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Заполняем данные
        int rowNum = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getEmail());
            row.createCell(3).setCellValue(user.getGoal().toString());
            row.createCell(4).setCellValue(user.getHeight());
            row.createCell(5).setCellValue(user.getWeight());
            row.createCell(6).setCellValue(user.getActivityLevel().toString());
            row.createCell(7).setCellValue(user.getGender().toString());
            row.createCell(8).setCellValue(user.getCaloriesPerPortion() != null ? user.getCaloriesPerPortion() : 0.0); // Исправлено
            row.createCell(9).setCellValue(user.getCarbohydrates() != null ? user.getCarbohydrates() : 0.0); // Исправлено
            row.createCell(10).setCellValue(user.getFats() != null ? user.getFats() : 0.0); // Исправлено
            row.createCell(11).setCellValue(user.getMealName() != null ? user.getMealName() : ""); // Исправлено
            row.createCell(12).setCellValue(user.getProteins() != null ? user.getProteins() : 0.0); // Исправлено
            row.createCell(13).setCellValue(user.getAge()); // Возраст
            row.createCell(14).setCellValue(user.getDate() != null ? user.getDate().toString() : ""); // Новый столбец "Дата"
        }

        // Сохраняем документ в файл
        String fileName = "module17_TEST_Calorie-Tracker\\Пользователи.xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        }

        // Закрываем документ
        workbook.close();

        return fileName;
    }
}
