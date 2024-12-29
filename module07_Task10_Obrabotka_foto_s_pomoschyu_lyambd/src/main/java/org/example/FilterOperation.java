package org.example;

import java.util.Random;

public class FilterOperation {

    public static float[] onlyRed(float[] rgb) {

        rgb[1] = 0;
        rgb[2] = 0;
        return rgb;
    }

    public static float[] onlyGreen(float[] rgb) {

        rgb[0] = 0;
        rgb[2] = 0;
        return rgb;
    }

    public static float[] onlyBlue(float[] rgb) {

        rgb[0] = 0;
        rgb[1] = 0;
        return rgb;
    }

    public static float[] sepia(float[] rgb) {
        Random random = new Random();
        final float randomValue = random.nextFloat() * 100 / 255;
        rgb[0] += randomValue;
        rgb[1] += randomValue;
        rgb[2] += randomValue;
        for (int i = 0; i < rgb.length; i++) {
            if (rgb[i] > 1) {
                rgb[i] = 1;
            }
        }
        return rgb;
    }
}
