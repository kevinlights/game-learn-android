package com.gjh.learn.android.game.animation;

import com.gjh.learn.android.game.util.Painter;

/**
 * created on 2021/1/28
 *
 * @author kevinlights
 */
public class Animation {
    private Frame[] frames;
    private double[] frameEndTime;
    private int currentFrameIndex = 0;
    private double totalDuration = 0;
    private double currentTime = 0;

    public Animation(Frame... frames) {
        this.frames = frames;
        frameEndTime = new double[frames.length];
        for (int i = 0; i < frames.length; i++) {
            totalDuration += frames[i].getDuration();
            frameEndTime[i] = totalDuration;
        }
    }

    public synchronized void update(float increment) {
        currentTime += increment;
        if (currentTime > totalDuration) {
            wrapAnimation();
        }
        while (currentTime > frameEndTime[currentFrameIndex]) {
            currentFrameIndex++;
        }
    }

    private void wrapAnimation() {
        currentFrameIndex = 0;
        currentTime %= totalDuration;
    }

    public synchronized void render(Painter g, int x, int y) {
        g.drawImage(frames[currentFrameIndex].getImage(), x, y);
    }

    public synchronized void render(Painter g, int x, int y, int width, int height) {
        g.drawImage(frames[currentFrameIndex].getImage(), x, y, width, height);
    }
}
