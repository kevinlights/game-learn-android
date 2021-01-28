package com.gjh.learn.elliogame.model;

import android.graphics.Rect;

import com.gjh.learn.elliogame.util.RandomNumberGenerator;

/**
 * created on 2021/1/27
 *
 * @author kevinlights
 */
public class Block {
    private float x, y;
    private int width, height;
    private Rect rect;
    private boolean visible;

    private static final int UPPER_Y = 275;
    private static final int LOWER_Y = 355;

    public Block(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rect = new Rect((int) x, (int) y, width, height);
        visible = false;
    }

    public void update(float delta, float velX) {
        x += velX * delta;
        // 离开屏幕时，重置
        if (x <= -50) {
            reset();
        }
        updateRect();
    }

    public void updateRect() {
        if (null != rect) {
            rect.set((int) x, (int) y, (int) x + width, (int) y + height);
        }
    }

    public void reset() {
        visible = true;
        // 随机生成高度，按照此频率，出现在低位的可能性更大些
        if (RandomNumberGenerator.getRandInt(3) == 0) {
            y = UPPER_Y;
        } else {
            y = LOWER_Y;
        }
        // 平均隔开 5 个 block 对象，每个对象间 200 像素的距离
        x += 1000;
    }

    public void onCollide(Player p) {
        // 碰撞到 player 时，会消失，同时将其向后推 30 像素
        visible = false;
        p.pushBack(30);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rect getRect() {
        return rect;
    }

    public boolean isVisible() {
        return visible;
    }
}
