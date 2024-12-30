package org.example;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        System.out.println("""
            Задание:\s
            Модуль 3. Основы ООП. Задание №10:\s
                1. Напишите сами крестики-нолики, не подглядывая в наш код.\s

            Решение:\s""");

        X0Game game = new X0Game();
        game.play();
    }
}

class FieldXO {
    char[][] field;
    int size = 3;
    int countToWin = 3;

    void initField() {
        this.field = new char[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                field[row][col] = ' ';
            }
        }
        System.out.println("Field initialized");
        this.printField();
    }

    void printField() {
        System.out.print("   ");
        for (int i = 1; i <= size; i++) {
            System.out.print(i + "  ");
        }
        System.out.println();

        for (int row = 0; row < size; row++) {
            int rowNumber = row + 1;
            System.out.print(rowNumber + " ");
            for (int col = 0; col < size; col++) {
                System.out.print("[" + this.field[row][col] + "]");
            }
            System.out.println();
        }
    }

    boolean isPlaceFree(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= size || colIndex < 0 || colIndex >= size) {
            return false;
        } else if (this.field[rowIndex][colIndex] == ' ') {
            return true;
        } else {
            return false;
        }
    }

    void setValue(int rowIndex, int colIndex, char value) {
        this.field[rowIndex][colIndex] = value;
    }

    boolean isGameOver(char player) {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                if (checkRightDirection(row, col, player)) {
                    return true;
                } else if (checkDownDirection(row, col, player)) {
                    return true;
                } else if (checkRightDiagonal(row, col, player)) {
                    return true;
                } else if (checkLeftDiagonal(row, col, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkRightDirection(int row, int col, char player) {
        if (col > this.size - this.countToWin) {
            return false;
        }
        for (int i = col; i < col + this.countToWin; i++) {
            if (this.field[row][i] != player) {
                return false;
            }
        }
        return true;
    }

    boolean checkDownDirection(int row, int col, char player) {
        if (row > this.size - this.countToWin) {
            return false;
        }
        for (int i = row; i < row + this.countToWin; i++) {
            if (this.field[i][col] != player) {
                return false;
            }
        }
        return true;
    }

    boolean checkRightDiagonal(int row, int col, char player) {
        if (row > this.size - this.countToWin) {
            return false;
        }
        if (col > this.size - this.countToWin) {
            return false;
        }

        for (int sdvig = 0; sdvig < this.countToWin; sdvig++) {
            int rowToCheck = row + sdvig;
            int colToCheck = col + sdvig;
            if (this.field[rowToCheck][colToCheck] != player) {
                return false;
            }
        }
        return true;
    }

    boolean checkLeftDiagonal(int row, int col, char player) {
        if (row > this.size - this.countToWin) {
            return false;
        }
        if (col < this.countToWin - 1) {
            return false;
        }

        for (int sdvig = 0; sdvig < this.countToWin; sdvig++) {
            int rowToCheck = row + sdvig;
            int colToCheck = col - sdvig;
            if (this.field[rowToCheck][colToCheck] != player) {
                return false;
            }

        }
        return true;
    }
}

class X0Game {
    FieldXO gameField;
    Scanner scanner = new Scanner(System.in);
    char whoMakeNextTurn;
    boolean gameOver = false;

    void setupNewGame() {
        System.out.println("Will play new X0 game");
        this.gameField = new FieldXO();
        this.gameField.initField();
    }

    void play() {
        this.setupNewGame();
        System.out.println("Who will make first turn (Enter X or 0 (zero) in Latin alphabet): ");
        char first = this.scanner.next().charAt(0);
        if (first == 'X' || first == '0') {
            this.whoMakeNextTurn = first;
        } else {
            System.out.println("I recognizing only X or 0 (zero). So first will be X");
            this.whoMakeNextTurn = 'X';
        }

        while (!gameOver) {
            turn();
            this.gameOver = this.gameField.isGameOver(whoMakeNextTurn);
            if (this.gameOver) {
                System.out.println(this.whoMakeNextTurn + " win!");
            }
            if (this.whoMakeNextTurn == 'X') {
                this.whoMakeNextTurn = '0';
            } else {
                this.whoMakeNextTurn = 'X';
            }
        }
        System.out.println("Game over.");
    }

    void turn() {
        System.out.println(this.whoMakeNextTurn + ", your turn.");
        System.out.print("Chose row: ");
        int rowNumber = this.scanner.nextInt();
        System.out.print("Chose column: ");
        int colNumber = this.scanner.nextInt();
        int rowIndex = rowNumber - 1;
        int colIndex = colNumber - 1;
        if (this.gameField.isPlaceFree(rowIndex, colIndex)) {
            this.gameField.setValue(rowIndex, colIndex, whoMakeNextTurn);
            this.gameField.printField();
        } else {
            System.out.println("Wrong number (maybe this place is not free?). Make turn again.");
            turn();
        }
    }
}