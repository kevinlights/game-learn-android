package com.gjh.learn.elliogame.state;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.gjh.learn.elliogame.main.Assets;
import com.gjh.learn.elliogame.util.LogUtil;
import com.gjh.learn.elliogame.util.Painter;
import com.gjh.learn.elliogame.util.UIButton;

public class MenuState extends State {

    private UIButton playBtn, scoreBtn;

    @Override
    public void init() {
        playBtn = new UIButton(316, 227, 484, 286, Assets.start, Assets.startDown);
        scoreBtn = new UIButton(316, 300, 484, 359, Assets.score, Assets.scoreDown);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.welcome, 0, 0);
        playBtn.render(g);
        scoreBtn.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            playBtn.onTouchDown(scaledX, scaledY);
            scoreBtn.onTouchDown(scaledX, scaledY);
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (playBtn.isPressed(scaledX, scaledY)) {
                playBtn.cancel();
                LogUtil.d("Play Button Pressed!");
                setCurrentState(new PlayState());
            } else if (scoreBtn.isPressed(scaledX, scaledY)) {
                scoreBtn.cancel();
                LogUtil.d("Score Button Pressed!");
            } else {
                playBtn.cancel();
                scoreBtn.cancel();
            }
        }
        return true;
    }
}
