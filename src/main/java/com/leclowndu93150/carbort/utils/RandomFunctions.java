package com.leclowndu93150.carbort.utils;

public class RandomFunctions {

    public static String convertTicksToSeconds(int ticks) {
        double seconds = ticks / 20.0;
        if (seconds % 1 == 0) {
            return String.valueOf((int) seconds);
        } else {
            return String.format("%.1f", seconds);
        }
    }

}
