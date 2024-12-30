package org.example;

//import task3_10_4.Task3_10_4;

public class Apple5_2 {
    public int posX2;
    public int posY2;

    public Apple5_2(int x2, int y2) {
        posX2 = x2;
        posY2 = y2;
    }

    public void setRandomPosition2() {
        posX2 = Math.abs((int) (Math.random()* org.example.Main.WIDTH-1));
        posY2 = Math.abs((int) (Math.random()* org.example.Main.HEIGHT-1));
    }
}