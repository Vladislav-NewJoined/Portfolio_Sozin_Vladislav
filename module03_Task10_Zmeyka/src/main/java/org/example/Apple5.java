package org.example;

//import task3_10_4.Task3_10_4;

public class Apple5 {
    public int posX;
    public int posY;

    public Apple5(int x, int y) {
        posX = x;
        posY = y;
    }

    public void setRandomPosition() {
        posX = Math.abs((int) (Math.random() * org.example.Main.WIDTH-1));
        posY = Math.abs((int) (Math.random() * org.example.Main.HEIGHT-1));
    }
}
