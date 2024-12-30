package org.example;

public class Obstacle5 {

    public int lengthObstacle = 3;

    public int oX[] = new int[400];
    public int oY[] = new int[400];

    public Obstacle5(int x1, int y1, int x2, int y2, int x3, int y3) {
        oX[0] = x1;
        oX[1] = x2;
        oX[2] = x3;
        oY[0] = y1;
        oY[1] = y2;
        oY[2] = y3;
    }
}