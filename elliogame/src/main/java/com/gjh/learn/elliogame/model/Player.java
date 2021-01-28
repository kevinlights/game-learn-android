package com.gjh.learn.elliogame.model;

import android.graphics.Rect;

import com.gjh.learn.elliogame.main.Assets;

/**
 * created on 2021/1/26
 *
 * @author kevinlights
 */
public class Player {
    private float x, y;
    private int width, height, velY;
    private Rect rect, duckRect, ground;

    private boolean isAlive;
    private boolean isDucked;
    private float duckDuration = DUCK_DURATION;

    private static final int JUMP_VELOCITY = -600;
    private static final int ACCEL_GRAVITY = 1800;
    private static final float DUCK_DURATION = .6f;

    public Player(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        ground = new Rect(0, 405, 800, 500);
        rect = new Rect();
        duckRect = new Rect();
        isAlive = true;
        isDucked = false;
        updateRects();
    }

    public void update(float delta) {
        if (duckDuration > 0 && isDucked) {
            duckDuration -= delta;
        } else {
            isDucked = false;
            duckDuration = DUCK_DURATION;
        }
        if (isGrounded()) {
            y = 406 - height;
            velY = 0;
        } else {
            velY += ACCEL_GRAVITY * delta;
        }
        y += velY * delta;
        updateRects();
    }

    public void jump() {
        if (isGrounded()) {
            Assets.playSound(Assets.onJumpID);
            isDucked = false;
            duckDuration = DUCK_DURATION;
            y -= 10;
            velY = JUMP_VELOCITY;
            updateRects();
        }
    }

    public void duck() {
        if (isGrounded()) {
            isDucked = true;
        }
    }

    public void pushBack(int dX) {
        Assets.playSound(Assets.hitID);
        x -= dX;
        if (x < -width / 2) {
            isAlive = false;
        }
        rect.set((int) x, (int) y, (int) x + width, (int) y + height);
    }

    public boolean isGrounded() {
        return Rect.intersects(rect, ground);
    }

    private void updateRects() {
        rect.set((int) x + 10, (int) y, (int) x + width - 20, (int) y + height);
        duckRect.set((int) x, (int) y + 20, (int) x + width, (int) y + height - 20);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVelY() {
        return velY;
    }

    public Rect getRect() {
        return rect;
    }

    public Rect getDuckRect() {
        return duckRect;
    }

    public Rect getGround() {
        return ground;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isDucked() {
        return isDucked;
    }

    public float getDuckDuration() {
        return duckDuration;
    }
}
