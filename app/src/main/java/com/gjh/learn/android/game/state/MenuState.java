package com.gjh.learn.android.game.state;

import android.view.MotionEvent;

import com.gjh.learn.android.game.main.Assets;
import com.gjh.learn.android.game.util.Painter;

public class MenuState extends State {
    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.welcome, 0, 0);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }
}
