package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("""
                Задание:\s
                Модуль 4. Наследование. Задание №6:\s
                    2. Доработайте калькулятор: при неверном вводе выбрасывайте исключение\s

                Решение:\s""");

        System.out.println("Пишем калькулятор с обработкой исключений: ");

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите первое число (можно целое, можно дробное, если дробное, то разделитель - запятая): ");
            double num1 = scanner.nextDouble();

            System.out.println("Введите знак операции (+, -, /, *): ");
            char operation = scanner.next().charAt(0);

            System.out.println("Введите второе число: ");
            double num2 = scanner.nextDouble();

            double result;

            switch(operation) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '/':
                    if(num2 != 0) {
                        result = num1 / num2;
                    } else {
                        throw new ArithmeticException("Ошибка: деление на ноль!");
                    }
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неверная операция!");
            }

            System.out.println("Результат: " + result);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}