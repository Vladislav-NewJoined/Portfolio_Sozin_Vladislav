package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JPanel implements ActionListener{
    public static void main(String[] args) {
        System.out.println("""
                Задание:\s
                5. Доработайте змейку, пусть на поле еще могут быть стены. в них тоже нельзя
                   врезаться

                Решение:\s
                Мною добавлено уточняющее условие: победа наступает, если змейка съедает 4 яблока.""");

        jFrame = new JFrame("Snake");
        jFrame.setSize(WIDTH * SCALE + 14, HEIGHT * SCALE + 37);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);

        jFrame.add(new org.example.Main());
        jFrame.setVisible(true);
    }

    public static JFrame jFrame;
    public static final int SCALE = 32;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static int speed = 2;
    int counter;

    static Snake5 s = new Snake5(5, 6, 5, 5, 5, 4);
    Apple5 apple = new Apple5(Math.abs((int) (Math.random() * org.example.Main.WIDTH - 1)), Math.abs((int) (Math.random() * org.example.Main.HEIGHT - 1)));
    Apple5_2 apple2 = new Apple5_2(Math.abs((int) (Math.random() * org.example.Main.WIDTH - 1)), Math.abs((int) (Math.random() * org.example.Main.HEIGHT - 1)));
    Timer timer = new Timer(1000 / speed, this);

    static Obstacle5 o = new Obstacle5(14, 2, 14, 1, 14, 0);
    static Obstacle2_5 o2 = new Obstacle2_5(4, 17, 5, 17, 6, 17, 7, 17);

    public Main() {
        timer.start();
        addKeyListener(new KeyBoard());
        setFocusable(true); //Это чтобы находилось в центре экрана
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

        for (int x = 0; x < WIDTH * SCALE; x += SCALE) {
            g.setColor(Color.black);
            g.drawLine(x, 0, x, HEIGHT * SCALE);
        }
        for (int y = 0; y < HEIGHT * SCALE; y += SCALE) {
            g.setColor(Color.black);
            g.drawLine(0, y, WIDTH * SCALE, y);
        }

        g.setColor(Color.red);
        g.fillOval(apple.posX * SCALE + 4, apple.posY * SCALE + 4, SCALE - 8, SCALE - 8);
        g.fillOval(apple2.posX2 * SCALE + 4, apple2.posY2 * SCALE + 4, SCALE - 8, SCALE - 8);
        for (int l = 1; l < s.length; l++) {
            g.setColor(Color.green);
            g.fillRect(s.sX[l] * SCALE + 3, s.sY[l] * SCALE + 3, SCALE - 6, SCALE - 6);
            g.setColor(Color.white);
            g.fillRect(s.sX[0] * SCALE + 3, s.sY[0] * SCALE + 3, SCALE - 6, SCALE - 6);
        }

        //Отрисовываем стены (препятствия)
        for (int l = 0; l < o.lengthObstacle; l++) {
            g.setColor(Color.gray);
            g.fillRect(o.oX[l] * SCALE, o.oY[l] * SCALE, SCALE, SCALE);
        }

        for (int l = 0; l < o2.lengthObstacle2; l++) {
            g.setColor(Color.gray);
            g.fillRect(o2.oX2[l] * SCALE, o2.oY2[l] * SCALE, SCALE, SCALE);
        }
        //конец раздела: Отрисовываем стены (препятствия)
    }

    public static int count() {
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Появление таблички про победу после 4-х яблок.
        s.move();
        timer.start();

        if (counter == 0) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "To win, snake must eat 4 apples.");
            jFrame.setVisible(true);
            timer.restart();
        }
//            //Конец фрагмента: лучше условие: Появление таблички про победу после 4-х яблок.

        //пишем условие для победы (съедено 4 яблока)
        if ((s.sX[0] == apple.posX) && (s.sY[0] == apple.posY)) {
            s.countApples++;

            if (s.countApples == 4) {
                s.length++;
                repaint();
                timer.stop();
                JOptionPane.showMessageDialog(null, "You win. Snake ate 4 apples. Game over.");
                jFrame.setVisible(false);
                System.exit(0);
            }
        }

        if ((s.sX[0] == apple2.posX2) && (s.sY[0] == apple2.posY2)) {
            s.countApples++;

            if (s.countApples == 4) {
                s.length++;
                repaint();
                timer.stop();
                JOptionPane.showMessageDialog(null, "You win. Snake ate 4 apples. Game over.");
                jFrame.setVisible(false);
                System.exit(0);
            }
        }
        //конец условия для победы

        //Условие, что происходит после съедения яблока и пересечения самой себя.
        if ((s.sX[0] == apple.posX) && (s.sY[0] == apple.posY)) {
            apple.setRandomPosition();
            s.length++;
        }

        if ((s.sX[0] == apple2.posX2) && (s.sY[0] == apple2.posY2)) {
            apple2.setRandomPosition2();
            while (apple2.posX2 == apple.posX) {
                apple2.setRandomPosition2();
            }
            s.length++;
        }

        for (int l = 1; l < s.length; l++) {
            if ((s.sX[l] == apple.posX) && (s.sY[l] == apple.posY)) {
                apple.setRandomPosition();
            }

            if ((s.sX[l] == apple2.posX2) && (s.sY[l] == apple2.posY2)) {
                apple2.setRandomPosition2();
                while (apple2.posX2 == apple.posX) {
                    apple2.setRandomPosition2();
                }
            }
            //Конец условия, что происходит после съедения яблока.

            //Snake crossed itself
            if ((s.sX[0] == s.sX[l]) && (s.sY[0] == s.sY[l])) {
//                System.out.println("crash");
                timer.stop();
                JOptionPane.showMessageDialog(null, "You lost. Snake crossed itself. Game over.");
                jFrame.setVisible(false);
                System.exit(0);
            }
        }
        //Конец условия, что происходит после съедения яблока и пересечения самой себя.

        //Условие, что происходит при столкновении с препятствием.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((s.sX[0] == o.oX[i]) && (s.sY[0] == o.oY[j])) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "CRASH WITH OBSTACLE");
                    jFrame.setVisible(false);
                    System.exit(0);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((s.sX[0] == o2.oX2[i]) && (s.sY[0] == o2.oY2[j])) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "CRASH WITH OBSTACLE");
                    jFrame.setVisible(false);
                    System.exit(0);
                }
            }
        }
        //Конец условия, что происходит при столкновении с препятствием.

        //Snake collided into wall.
        if (s.sX[0] > org.example.Main.WIDTH - 1) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "You lost. Snake collided into wall. Game over.");
            jFrame.setVisible(false);
            System.exit(0);
        }
        if (s.sX[0] < 0) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "You lost. Snake collided into wall. Game over.");
            jFrame.setVisible(false);
            System.exit(0);
        }
        if (s.sY[0] > org.example.Main.HEIGHT - 1) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "You lost. Snake collided into wall. Game over.");
            jFrame.setVisible(false);
            System.exit(0);
        }
        if (s.sY[0] < 0) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "You lost. Snake collided into wall. Game over.");
            jFrame.setVisible(false);
            System.exit(0);
        }
        repaint();

        counter = count () + 1;
    }

    public static class KeyBoard extends KeyAdapter {
        public void keyPressed (KeyEvent event) {
            int key = event.getKeyCode();

            if ((key == KeyEvent.VK_UP) && (s.direction != 2)) s.direction = 0;
            if ((key == KeyEvent.VK_DOWN) && (s.direction != 0)) s.direction = 2;
            if ((key == KeyEvent.VK_RIGHT) && (s.direction != 3)) s.direction = 1;
            if ((key == KeyEvent.VK_LEFT) && (s.direction != 1)) s.direction = 3;
        }
    }
}