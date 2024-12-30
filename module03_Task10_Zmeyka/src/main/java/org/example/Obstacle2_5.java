package org.example;

public class Obstacle2_5 {

    public int lengthObstacle2 = 4;

    public int oX2[] = new int[400];
    public int oY2[] = new int[400];

    public Obstacle2_5(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        oX2[0] = x1;
        oX2[1] = x2;
        oX2[2] = x3;
        oX2[3] = x4;
        oY2[0] = y1;
        oY2[1] = y2;
        oY2[2] = y3;
        oY2[3] = y4;
    }
}
