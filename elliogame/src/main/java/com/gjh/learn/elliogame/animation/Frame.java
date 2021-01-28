package com.gjh.learn.elliogame.animation;

import android.graphics.Bitmap;

/**
 * created on 2021/1/28
 *
 * @author kevinlights
 */
public class Frame {
    private Bitmap image;
    private double duration;

    public Frame(Bitmap image, double duration) {
        this.image = image;
        this.duration = duration;
    }

    public Bitmap getImage() {
        return image;
    }

    public double getDuration() {
        return duration;
    }
}
