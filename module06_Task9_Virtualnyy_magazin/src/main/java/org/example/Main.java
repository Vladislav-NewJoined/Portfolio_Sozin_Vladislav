package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("""
                Задание:\s
                Модуль 6. Основные структуры данных. Задание №9 ПРОЕКТ:\s
                    1. Цель создания проекта:
                            Закрепить знания по Основным структурам данных. Познакомиться с базовыми принципами
                            организации кода в проекте.
                       Общая задача создания проекта:
                            Создать виртуальный магазин, с которым можно взаимодействовать через консольный
                            ввод/вывод.
                       Пошаговое выполнение проекта:
                            1. Создание моделей данных
                            2. Продумывание структуры проекта
                            3. Создание слоя с данными DataSource
                            4. Создание сервиса для управления данными
                            5. Работа с выводом данных в консоль\s

                Решение:\s""");

        System.out.println("Создаём виртуальный магазин по продаже смартфонов.\n");

        // Создаем список для хранения смартфонов
        List<Smartphone> catalog = new ArrayList<>();

        // Добавляем шесть смартфонов в каталог
        catalog.add(new Smartphone("Samsung Galaxy S21", 25000, 8, "4301"));
        catalog.add(new Smartphone("iPhone 12", 40000, 11, "8258"));
        catalog.add(new Smartphone("Google Pixel 5", 35000, 17, "4189"));
        catalog.add(new Smartphone("OnePlus 9 Pro", 42000, 5, "8251"));
        catalog.add(new Smartphone("Xiaomi Mi 11", 30000, 3, "3930"));
        catalog.add(new Smartphone("Huawei P40 Pro", 28000, 20, "3851"));

        // Выводим каталог в консоль
        System.out.println("Каталог товаров:");
        for (Smartphone phone : catalog) {
            System.out.println(phone);
        }

        // Сохраняем каталог в файл DataSource
        saveCatalogToFile(catalog, "src/main/java/org/example/DataSource.txt");

        // Запрашиваем у пользователя электронную почту и пароль
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите вашу электронную почту: ");
        String email = scanner.nextLine();
        System.out.print("Введите ваш пароль: ");
        String password = scanner.nextLine();

        // Сохраняем электронную почту и пароль в файл
        saveCredentialsToFile(email, password, "src/main/java/org/example/CustomersID.txt");
//        saveCredentialsToFile(email, password, "src/main/java/org/example/CustomersID.txt");

        // Добавляем товары в корзину
        boolean validInput = false;
        String id = "";
        int quantity = 0;

        while (!validInput) {
            System.out.print("\nДобавление товара в корзину:\nВведите ID товара и количество через запятую (например, '4301,2'): ");
            String input = scanner.nextLine().trim(); // Удаляем лишние пробелы перед и после ввода

            // Парсим введенную строку на ID и количество
            String[] parts = input.split(",");
            if (parts.length != 2) {
                System.out.println("Ошибка ввода, повторите ввод.");
                continue;
            }

            id = parts[0].trim(); // Удаляем лишние пробелы перед и после ID
            quantity = Integer.parseInt(parts[1].trim()); // Удаляем лишние пробелы перед и после количества

            // Проверяем, есть ли товар с таким ID и есть ли в наличии нужное количество
            Smartphone selectedPhone = null;
            for (Smartphone phone : catalog) {
                if (phone.getId().equals(id)) {
                    selectedPhone = phone;
                    break;
                }
            }

            if (selectedPhone == null || selectedPhone.getQuantity() < quantity) {
                System.out.println("Ошибка ввода, повторите ввод.");
            } else {
                validInput = true;
            }
        }

        // Вычисляем общую сумму к оплате
        final String finalId = id;
        final int finalQuantity = quantity;
        int totalSum = catalog.stream()
                .filter(phone -> phone.getId().equals(finalId))
                .mapToInt(phone -> phone.getPrice() * finalQuantity)
                .sum();

        // Выводим информацию о покупке
        System.out.println("\nТовар добавлен в корзину:");
        System.out.println("ID товара: " + id);
        System.out.println("Название товара: " + catalog.stream().filter(phone -> phone.getId().equals(finalId)).findFirst().get().getName());
        System.out.println("Количество: " + quantity);
        System.out.println("Цена товара: " + catalog.stream().filter(phone -> phone.getId().equals(finalId)).findFirst().get().getPrice() + " руб.");
        System.out.println("Сумма к оплате: " + totalSum + " руб.");

        // Сохраняем информацию о покупке в файл корзины
        savePurchaseInfoToFile(id, catalog, quantity, totalSum, "src/main/java/org/example/ShoppingCart.txt");

        // Запрашиваем пользователя об очистке корзины
        System.out.print("\nХотите ли Вы очистить корзину? Ответьте 'да' или 'нет': ");
        String clearCartChoice = scanner.nextLine().trim().toLowerCase();
        if (clearCartChoice.equals("да")) {
            clearShoppingCart("src/task6_9_1/ShoppingCart.txt");
        } else if (clearCartChoice.equals("нет")) {
            System.out.println("Корзина окончательно сформирована. Работа программы завершена.");
        } else {
            System.out.println("Некорректный ввод. Корзина окончательно сформирована. Работа программы завершена.");
        }
    }

    // Метод для сохранения каталога в файл
    private static void saveCatalogToFile(List<Smartphone> catalog, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Smartphone phone : catalog) {
                writer.write(phone.toString() + "\n");
            }
            System.out.println("Каталог товаров сохранен в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении каталога в файл: " + e.getMessage());
        }
    }

    // Метод для сохранения электронной почты и пароля в файл
    private static void saveCredentialsToFile(String email, String password, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Email: " + email + "\n");
            writer.write("Password: " + password + "\n");
            System.out.println("Электронная почта и пароль сохранены в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении электронной почты и пароля в файл: " + e.getMessage());
        }
    }

    // Метод для сохранения информации о покупке в файл корзины
    private static void savePurchaseInfoToFile(String id, List<Smartphone> catalog, int quantity, int totalSum, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            Smartphone selectedPhone = catalog.stream().filter(phone -> phone.getId().equals(id)).findFirst().get();
            writer.write("ID товара: " + id + "\n");
            writer.write("Название товара: " + selectedPhone.getName() + "\n");
            writer.write("Количество: " + quantity + "\n");
            writer.write("Цена товара: " + selectedPhone.getPrice() + " руб.\n");
            writer.write("Сумма к оплате: " + totalSum + " руб.\n");
            System.out.println("Информация о покупке сохранена в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении информации о покупке в корзину: " + e.getMessage());
        }
    }

    // Метод для очистки корзины
    private static void clearShoppingCart(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
            System.out.println("Корзина очищена.");
        } catch (IOException e) {
            System.out.println("Ошибка при очистке корзины: " + e.getMessage());
        }
    }
}

// Класс представляющий смартфон
class Smartphone {
    private String id;
    private String name;
    private int price;
    private int quantity;

    public Smartphone(String name, int price, int quantity, String id) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Название: " + name + ", Цена: " + price + " руб., Количество: " + quantity;
    }
}