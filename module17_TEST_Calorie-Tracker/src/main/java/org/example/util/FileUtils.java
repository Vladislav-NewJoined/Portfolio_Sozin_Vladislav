package org.example.util;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static boolean isFileAvailable(File file) {
        if (!file.exists()) {
            return true; // Файл не существует, значит доступен
        }

        // Пытаемся переименовать файл
        File tempFile = new File(file.getParent(), file.getName() + ".temp");
        boolean renamed = file.renameTo(tempFile);
        if (renamed) {
            // Если переименование удалось, возвращаем файл в исходное состояние
            tempFile.renameTo(file);
            return true;
        }
        return false; // Файл занят
    }
}