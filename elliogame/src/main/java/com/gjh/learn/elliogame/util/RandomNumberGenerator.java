package com.gjh.learn.elliogame.util;

import java.util.Random;

/**
 * created on 2021/1/26
 *
 * @author kevinlights
 */
public class RandomNumberGenerator {
    private static Random rand = new Random();

    /**
     * [low, up)
     * @param lowerBound
     * @param upperBound
     * @return
     */
    public static int getRandIntBetween(int lowerBound, int upperBound) {
        return rand.nextInt(upperBound - lowerBound);
    }

    public static int getRandInt(int upperBound) {
        return rand.nextInt(upperBound);
    }
}
