package org.example.main2;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("""
                Задание:\s
                Модуль 8. Многопоточность. Задание №6. Проект:\s
                    2. Создать класс реализующий Runnable
                         Создать класс, реализующий интерфейс Runnable.
                         Переопределить run() метод. Создать цикл for. В цикле распечатываем значения от 0 до 100 делящиеся на 10 без остатка.
                         Используем статический метод Thread.sleep(), чтобы сделать паузу.
                         Создать три потока, выполняющих задачу распечатки значений.

                Решение:\s""");

        Thread t1 = new Thread(new T1());
        Thread t2 = new Thread(new T2());
        Thread t3 = new Thread(new T3());
        Thread t4 = new Thread(new T4());

        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
            t4.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class T1 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(12);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("""
            1. Реализуем решение по заданию:
                - Создать класс, реализующий интерфейс Runnable.
                - Реализовано на примере создания счётчика от 1-го до 5-ти:""");

            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Runnable count " + (i + 1));
            }
            System.out.println();
        }
    }

    static class T2 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(12);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("""
            2. Реализуем решение по заданию:
                - Переопределить run() метод. Создать цикл for. В цикле распечатываем значения
                  от 0 до 100 делящиеся на 10 без остатка:""");

            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i % 10 == 0) {
                    System.out.println(i);
                }
            }
            System.out.println();
        }
    }

    static class T3 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(12);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("""
            3. Реализуем решение по заданию:
                - Используем статический метод Thread.sleep(), чтобы сделать паузу.
                - Реализовано путём создания паузы 1200 миллисекунд между выводом на печать\s
                  значений счетчика '1' и '2':""");

            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Counter value: " + (i + 1));
            }
            System.out.println();
        }
    }

    static class T4 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(12);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("""
            4. Реализуем решение по заданию:\s
                - Создать три потока, выполняющих задачу распечатки значений.
                - Реализовано на примере вывода уведомлений о старте и финише трёх потоков:""");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 3; i++) {
                System.out.println("Thread" + (i + 1) + " started");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 3; i++) {
                System.out.println("Thread" + (i + 1) + " finished");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.exit(0);
        }
    }
}