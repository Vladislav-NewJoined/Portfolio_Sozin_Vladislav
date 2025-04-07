package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class StepByStepTetris {
    public static void main(String[] args) {
        new org.example.StepByStepTetris();
    }

    int score = 0;

    JLabel scoreJLabel = new JLabel() {{
        setHorizontalAlignment(CENTER);
        setText("Score: " + score);
    }};
    int NumCurrentBrick = 0;
    brick[] bricks = new brick[1000];

    Field field = new Field();

    {
        System.out.println("""
                Задание:\s
                Кейс 3. Пошаговый тетрис.\s
                   Пусть у Вас будет поле 10х10 (двумерный массив chart). Считайте количество очков:
                   полностью выстроенных линий (таковые сгорают). Когда фигуре некуда упасть, игра закончена.
                 
                Решение:\s
                   Клавиши управления: Сдвиг: Up, Down, Right, Left. Вращение: Insert, Delete.
                   Следующая фигура: Down.""");

        bricks[NumCurrentBrick] = new brick();

        field.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                step(KeyEvent.getKeyText(e.getKeyCode()));
            }
        });

        field.paintAllBrick(bricks);
    }

    void step(String dir_) {
        // задаем клавиши движения
        int dirX = 0;
        int dirY = 0;

        if (dir_.equals("Down"))
            dirY++;
        if (dir_.equals("Up"))
            dirY--;
        if (dir_.equals("Right"))
            dirX++;
        if (dir_.equals("Left"))
            dirX--;

        if (dir_.equals("Insert"))
            rotateLeft();
        if (dir_.equals("Delete"))
            rotateRight();

        //вычисляем коллизию
        boolean collision = collision(dirX, dirY);
        // если направление вниз - поставить, иначе просто подвинуть
        if (collision && dirY > 0) {
            for(int[] cell: bricks[NumCurrentBrick].form)
                if(cell[1]+bricks[NumCurrentBrick].posY== 0){
                    System.exit(0);
                }
            NumCurrentBrick++;
            bricks[NumCurrentBrick] = new brick();
            ClearFullLines();

        } else if (!collision) {
            bricks[NumCurrentBrick].posX += dirX;
            bricks[NumCurrentBrick].posY += dirY;
        }

        field.paintAllBrick(bricks);
        scoreJLabel.setText("Score: " + score);
        field.repaint();
    }

    void rotateRight() {
        int maxX = 0;
        int maxY = 0;

        for (int[] currentCell : bricks[NumCurrentBrick].form) {
            if (currentCell[0] > maxX)
                maxX = currentCell[0];
            if (currentCell[1] > maxY)
                maxY = currentCell[1];
        }

        int[][] newForm = new int[bricks[NumCurrentBrick].form.length][2];

        for (int currentCellNum = 0; currentCellNum < newForm.length; currentCellNum++) {
            newForm[currentCellNum][0] = maxY - bricks[NumCurrentBrick].form[currentCellNum][1];
            newForm[currentCellNum][1] = bricks[NumCurrentBrick].form[currentCellNum][0];
        }
        int[][] temp = bricks[NumCurrentBrick].form;

        bricks[NumCurrentBrick].form = newForm;
        if (collision(0, 0))
            bricks[NumCurrentBrick].form = temp;
    }

    void rotateLeft() {
        int maxX = 0;
        int maxY = 0;
        for (int[] currentCell : bricks[NumCurrentBrick].form) {
            if (currentCell[0] > maxX)
                maxX = currentCell[0];
            if (currentCell[1] > maxY)
                maxY = currentCell[1];
        }
        int[][] newForm = new int[bricks[NumCurrentBrick].form.length][2];

        for (int currentCellNum = 0; currentCellNum < newForm.length; currentCellNum++) {
            newForm[currentCellNum][0] = bricks[NumCurrentBrick].form[currentCellNum][1];
            newForm[currentCellNum][1] = maxX - bricks[NumCurrentBrick].form[currentCellNum][0];
        }
        int[][] temp = bricks[NumCurrentBrick].form;

        bricks[NumCurrentBrick].form = newForm;
        if (collision(0, 0))
            bricks[NumCurrentBrick].form = temp;
    }

    boolean collision(int dirX_, int dirY_) {
        for (brick currentBrick : bricks) // проходимся по каждому кирпичику
            if (currentBrick != null) {
                for (int[] currentCell : currentBrick.form)
                    if (currentBrick == bricks[NumCurrentBrick]) {

                        int nextX = currentCell[0] + currentBrick.posX + dirX_;
                        int nextY = currentCell[1] + currentBrick.posY + dirY_;

                        // коллизия краёв
                        if (nextY < 0 ||
                                nextY >= field.height ||
                                nextX >= field.width ||
                                nextX < 0)
                            return true;
                        //коллизия с каждым квадратиком на поле
                        for (int NumTargetBrick = 0; NumTargetBrick < bricks.length; NumTargetBrick++)
                            if (bricks[NumTargetBrick] != null && NumTargetBrick != NumCurrentBrick)
                                for (int[] targetCell : bricks[NumTargetBrick].form)
                                    if (targetCell != null) {
                                        int nextTargetY = targetCell[1] + bricks[NumTargetBrick].posY;
                                        int nextTargetX = targetCell[0] + bricks[NumTargetBrick].posX;
                                        if (nextTargetY == nextY && nextTargetX == nextX)
                                            return true;
                                    }
                    }
            }

        return false;
    }

    int give_a_random_number_from(int min_, int max_) {
        return min_ + (int) (Math.random() * max_);
    }

    void ClearFullLines() {

        //ищем заполненные строки
        int[] countOfCellsInLine = new int[field.height];
        for (brick currentBrick : bricks)
            if (currentBrick != null)
                for (int[] currentCell : currentBrick.form)
                    if (currentCell != null)
                        countOfCellsInLine[currentCell[1] + currentBrick.posY]++;

        int newMaxScore = 0;

        //если среди всех строк есть заполненные
        for (int currentLineNum = 0; currentLineNum < countOfCellsInLine.length; currentLineNum++) {
            if (countOfCellsInLine[currentLineNum] == field.width) {
                score++;

                File myFile = new File("module01_Task3_Tetris/src/main/java/org/example/maxScore.txt");
                try {
                    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(myFile/*, true*/ /*"maxScore.txt"*/)));

                    if (score > newMaxScore) {
                        newMaxScore = score;
                        writer.write("Max Score is: \n" + newMaxScore);
                        writer.flush();
                        writer.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // ищем все ячейки, лежащие на заполненных строках
                for (int currentBrickNum = 0; currentBrickNum < bricks.length; currentBrickNum++)
                    if (bricks[currentBrickNum] != null && currentBrickNum != NumCurrentBrick)
                        for (int currentCellNum = 0; currentCellNum < bricks[currentBrickNum].form.length;
                             currentCellNum++) {
                            // удалить заполненные строки
                            if (bricks[currentBrickNum].form[currentCellNum] != null)
                                if (bricks[currentBrickNum].form[currentCellNum][1] + bricks[currentBrickNum].posY
                                        == currentLineNum)
                                    bricks[currentBrickNum].form[currentCellNum] = null;
                            // те ячейки, что выше - опустить
                            if (bricks[currentBrickNum].form[currentCellNum] != null)
                                if (bricks[currentBrickNum].form[currentCellNum][1] + bricks[currentBrickNum].posY
                                        < currentLineNum)
                                    bricks[currentBrickNum].form[currentCellNum][1]++;
                        }
            }
        }
    }

    class brick {
        int[][][] typicalForms = new int[][][]{{{1, 0}, {1, 1}, {1, 2}, {0, 2}}, {{0, 0}, {0, 1}, {0, 2}, {1, 2}},
                {{1, 0}, {0, 1}, {1, 1}, {2, 1}}, {{0, 0}, {0, 1}, {0, 2}, {0, 3}}, {{0, 0}, {0, 1}, {1, 0}, {1, 1}}};

        int[][] form = typicalForms[give_a_random_number_from(0, typicalForms.length)];
        Color color = Color.BLUE;
        int posX = 0;
        int posY = 0;
    }

    class Field extends JFrame {
        int width = 10;
        int height = 10;
        JPanel fieldPanel = new JPanel() {{
            setLayout(new GridLayout(height, width));
        }};

        {
            for (int currentX = 0; currentX < width; currentX++)
                for (int currentY = 0; currentY < height; currentY++)
                    fieldPanel.add(new JButton() {{
                        setFocusable(false);
                    }});

            setLayout(new BorderLayout());
            add(fieldPanel);
            add(scoreJLabel, "North");
            //стандартные настройки окна
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(420, 430);
            clear();
            revalidate();

        }

        void paintDot(int x_, int y_, Color color_) {
            fieldPanel.getComponents()[x_ + (y_ * width)].setBackground(color_);
        }

        void clear() {
            for (Component currentComponent : fieldPanel.getComponents())
                currentComponent.setBackground(Color.WHITE);
        }

        void paintAllBrick(brick[] bricks_) {
            clear();
            for (brick currentBrick : bricks_) // проходимся по каждому кирпичику
                if (currentBrick != null)
                    for (int[] currentCell : currentBrick.form) // проходимся по каждой ячейке кирпичика
                        if (currentCell != null)
                            paintDot(currentCell[0] + currentBrick.posX, currentCell[1] + currentBrick.posY,
                                    currentBrick.color);
            repaint();
        }
    }
}