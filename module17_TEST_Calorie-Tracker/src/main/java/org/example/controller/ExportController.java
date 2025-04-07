package org.example.controller;

import org.example.service.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping("/excel")
    public String exportToExcel() {
        try {
            String fileName = excelExportService.exportToExcel();
            return "Файл успешно экспортирован: " + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Файл занят другим процессом. Закройте файл и попробуйте снова.")) {
                return "Ошибка: файл занят другим процессом. Закройте файл и попробуйте снова.";
            }
            return "Ошибка при экспорте данных: " + e.getMessage();
        }
    }
}
