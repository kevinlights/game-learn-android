package com.gjh.learn.elliogame.state;

import android.view.MotionEvent;

import com.gjh.learn.elliogame.main.Assets;
import com.gjh.learn.elliogame.util.Painter;

public class LoadState extends State {
    @Override
    public void init() {
        Assets.load();
    }

    @Override
    public void update(float delta) {
        setCurrentState(new MenuState());
    }

    @Override
    public void render(Painter g) {

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }
}
