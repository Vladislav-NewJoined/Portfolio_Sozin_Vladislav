package org.example.main1;

public class Main1 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("""
                Задание:\s
                Модуль 8. Многопоточность. Задание №6. Проект:\s
                    1. Создать класс расширяющий Thread
                        Создать класс NewThread расширяющий Thread.
                        Переопределить метод run(). В цикле for вывести на консоль символ 100 раз.
                        Создать экземпляр класса и запустить новый поток.

                Решение:\s""");

        // Создаём класс NewThread расширяющий Thread
        System.out.println("Создаём класс NewThread, расширяющий Thread: ");
        NewThread thread = new NewThread();
        thread.start();
        thread.join(); // Дожидаемся завершения потока

        // Выводим на консоль символ '№' 100 раз
        System.out.println("\nВыводим на консоль символ '№' 100 раз: ");
        for (int i = 0; i < 100; i++) {
            System.out.println("№: " + (i+1) + " раз");
        }

        // Создаём экземпляр класса и запускаем новый поток
        System.out.println("\nСоздаём экземпляр класса и запускаем новый поток: ");
        System.out.println("СПОСОБ 1:");
        System.out.println("Main thread started (Способ 1)...");
        for (int i = 0; i < 3; i++) {
            Thread t3 = new Thread(new MyThreadLogic());
            t3.start();
            Thread.sleep(300);
        }
        System.out.println("Main thread finished (Способ 1)...");
        System.out.println();

        System.out.println("СПОСОБ 2:");
        System.out.println("Main thread started (Способ 2)...");
        for (int i = 0; i < 3; i++) {
            Thread t4 = new Thread(() -> {
                System.out.println("Способ 2");
            },"Thread");
            t4.start();
            Thread.sleep(300);
        }
        System.out.println("Main thread finished (Способ 2)...");
    }
}

class NewThread extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("Результат создания класса NewThread, расширяющего Thread: " + getName());
    }
}

class MyThreadLogic implements Runnable {
    @Override
    public void run() {
        System.out.println("Способ 1");
    }
}