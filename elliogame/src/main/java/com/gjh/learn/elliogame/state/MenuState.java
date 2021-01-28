package com.gjh.learn.elliogame.state;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.gjh.learn.elliogame.main.Assets;
import com.gjh.learn.elliogame.util.LogUtil;
import com.gjh.learn.elliogame.util.Painter;

public class MenuState extends State {
    private Rect playRect;
    private Rect scoreRect;
    private boolean playDown;
    private boolean scoreDown;

    @Override
    public void init() {
        playRect = new Rect(316, 227, 484, 286);
        scoreRect = new Rect(316, 300, 484, 359);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.welcome, 0, 0);
        if (playDown) {
            g.drawImage(Assets.startDown, playRect.left, playRect.top);
        } else {
            g.drawImage(Assets.start, playRect.left, playRect.top);
        }
        if (scoreDown) {
            g.drawImage(Assets.scoreDown, scoreRect.left, scoreRect.top);
        } else {
            g.drawImage(Assets.score, scoreRect.left, scoreRect.top);
        }
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (playRect.contains(scaledX, scaledY)) {
                playDown = true;
                scoreDown = false;
            } else if (scoreRect.contains(scaledX, scaledY)) {
                playDown = false;
                scoreDown = true;
            }
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (playDown && playRect.contains(scaledX, scaledY)) {
                playDown = false;
                LogUtil.d("Play Button Pressed!");
            } else if (scoreDown && scoreRect.contains(scaledX, scaledY)) {
                scoreDown = false;
                LogUtil.d("Score Button Pressed!");
            } else {
                playDown = false;
                scoreDown = false;
            }
        }
        return true;
    }
}
