package com.gjh.learn.android.game.state;

import android.view.MotionEvent;

import com.gjh.learn.android.game.main.GameMainActivity;
import com.gjh.learn.android.game.util.Painter;

public abstract class State {

    protected boolean inited = false;
    protected boolean rendered = false;

    public void setCurrentState(State newState) {
        GameMainActivity.sGame.setCurrentState(newState);
    }

    public abstract void init();

    public abstract void update(float delta);

    public abstract void render(Painter g);

    public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);
}
