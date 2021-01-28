package com.gjh.learn.elliogame.util;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * created on 2021/1/28
 *
 * @author kevinlights
 */
public class UIButton {
    private Rect btnRect;
    private Bitmap btnImg, btnDownImg;
    private boolean btnDown = false;

    public UIButton(int left, int top, int right, int bottom, Bitmap btnImg, Bitmap btnDownImg) {
        btnRect = new Rect(left, top, right, bottom);
        this.btnImg = btnImg;
        this.btnDownImg = btnDownImg;
    }

    public void render(Painter g) {
        Bitmap curBtnImg = btnDown ? btnDownImg : btnImg;
        g.drawImage(curBtnImg, btnRect.left, btnRect.top, btnRect.width(), btnRect.height());
    }

    public void onTouchDown(int touchX, int touchY) {
        if (btnRect.contains(touchX, touchY)) {
            btnDown = true;
        } else {
            btnDown = false;
        }
    }

    public void cancel() {
        btnDown = false;
    }

    public boolean isPressed(int touchX, int touchY) {
        return btnDown && btnRect.contains(touchX, touchY);
    }
}
