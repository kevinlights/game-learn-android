package com.gjh.learn.elliogame.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.gjh.learn.elliogame.main.GameMainActivity;
import com.gjh.learn.elliogame.util.Painter;

/**
 * created on 2021/1/28
 *
 * @author kevinlights
 */
public class GameOverState extends State {

    private String playerScore;

    public GameOverState(int score) {
        this.playerScore = score + "";
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.rgb(255, 145, 0));
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        g.setColor(Color.DKGRAY);
        g.setFont(Typeface.DEFAULT_BOLD, 50);
        g.drawString("GAME OVER", 257, 175);
        g.drawString(playerScore, 285, 250);
        g.drawString("Touch the screen", 220, 350);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            setCurrentState(new MenuState());
        }
        return true;
    }
}
