package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("""
                Задание:\s
                Модуль 4. Наследование. Задание №6:\s
                    1. Доработайте крестики-нолики; создайте исключение, которое будете бросать при неверном
                       вводе пользователя\s

                Решение:\s""");

        char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        char currentPlayer = 'X';
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Отрисовка игрового поля
            System.out.println("  1 2 3");
            for (int i = 0; i < 3; i++) {
                System.out.print((i + 1) + " ");
                for (int j = 0; j < 3; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }

            // Проверка на победу
            if (checkWin(board, currentPlayer)) {
                System.out.println("Игрок " + currentPlayer + " победил!");
                break;
            }

            // Ввод координат от игрока с обработкой исключения
            try {
                int row, col;
                System.out.print("Игрок " + currentPlayer + ", введите номер строки: ");
                row = scanner.nextInt() - 1;
                System.out.print("Введите номер столбца: ");
                col = scanner.nextInt() - 1;

                // Проверка введенных координат
                if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
                    throw new InputMismatchException("Некорректно введены координаты. Попробуйте снова.");
                }

                // Установка символа на игровое поле
                board[row][col] = currentPlayer;

                // Смена игрока
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine(); // Очистка буфера ввода
            }
        }
    }

    // Проверка на победу
    public static boolean checkWin(char[][] board, char player) {
        // Горизонталь и вертикаль
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }

        // Диагональ
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[2][0] == player && board[1][1] == player && board[0][2] == player)) {
            return true;
        }

        return false;
    }
}