package org.example.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class TextExportService {

    public String exportToText(String message) throws IOException {
        // Создаем текстовый файл
        String fileName = "module17_TEST_Calorie-Tracker\\Отчет.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            // Записываем фразу в файл
            writer.write(message + "\n");
        }

        return fileName;
    }
}
