package com.gjh.learn.elliogame.util;

import android.view.MotionEvent;
import android.view.View;

import com.gjh.learn.elliogame.main.GameMainActivity;
import com.gjh.learn.elliogame.state.State;

public class InputHandler implements View.OnTouchListener {

    private State currentState;

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 事件坐标 与 屏幕大小 v.getWidth()/v.getHeight() 以及游戏的大小，即可得到缩放的相对坐标
        int scaledX = (int) ((event.getX() / v.getWidth()) * GameMainActivity.GAME_WIDTH);
        int scaledY = (int) ((event.getY() / v.getHeight()) * GameMainActivity.GAME_HEIGHT);
        return currentState.onTouch(event, scaledX, scaledY);
    }
}
