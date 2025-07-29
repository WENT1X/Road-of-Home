package utils;

import java.util.Random;

public class RandomEvent {
    private static Random random = new Random();

    public static boolean occurs(int chancePercent) {
        return random.nextInt(100) < chancePercent;
    }
}