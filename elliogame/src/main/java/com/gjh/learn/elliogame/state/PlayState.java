package com.gjh.learn.elliogame.state;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.gjh.learn.elliogame.main.Assets;
import com.gjh.learn.elliogame.main.GameMainActivity;
import com.gjh.learn.elliogame.model.Block;
import com.gjh.learn.elliogame.model.Cloud;
import com.gjh.learn.elliogame.model.Player;
import com.gjh.learn.elliogame.util.Painter;

import java.util.ArrayList;

/**
 * created on 2021/1/28
 *
 * @author kevinlights
 */
public class PlayState extends State {

    private Player player;
    private ArrayList<Block> blocks;
    private Cloud cloud, cloud2;
    private int playScore = 0;
    private static final int BLOCK_HEIGHT = 50;
    private static final int BLOCK_WIDTH = 20;
    private int blockSpeed = -200;
    private static final int PLAYER_WIDTH = 66;
    private static final int PLAYER_HEIGHT = 92;

    // 上一次手指按下的位置
    private float recentTouchY;

    @Override
    public void init() {
        player = new Player(160, GameMainActivity.GAME_HEIGHT - 45 - PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
        blocks = new ArrayList<>();
        cloud = new Cloud(100, 100);
        cloud2 = new Cloud(500, 50);
        for (int i = 0; i < 5; i++) {
            blocks.add(new Block(i * 200, GameMainActivity.GAME_HEIGHT - 95, BLOCK_WIDTH, BLOCK_HEIGHT));
        }
        inited = true;
    }

    @Override
    public void update(float delta) {
        if (!rendered) {
            return;
        }
        if (!player.isAlive()) {
            setCurrentState(new GameOverState(playScore / 100));
            return;
        }
        playScore++;
        if (playScore % 500 == 0 && blockSpeed > -280) {
            blockSpeed -= 10;
        }
        cloud.update(delta);
        cloud2.update(delta);
        Assets.runAnim.update(delta);
        player.update(delta);
        updateBlocks(delta);
    }

    private void updateBlocks(float delta) {
        for (Block b : blocks) {
            b.update(delta, blockSpeed);
            if (b.isVisible()) {
                if ((player.isDucked() && Rect.intersects(b.getRect(), player.getDuckRect()))
                        || (!player.isDucked() && Rect.intersects(b.getRect(), player.getRect()))) {
                    b.onCollide(player);
                }
            }
        }
    }

    @Override
    public void render(Painter g) {
        if (!inited) {
            return;
        }
        g.setColor(Color.rgb(208, 244, 247));
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);

        if (null != player) {
            renderPlayer(g);
        }
        if (blocks != null) {
            renderBlocks(g);
        }
        renderSun(g);
        if (cloud != null && cloud2 != null) {
            renderClouds(g);
        }

        g.drawImage(Assets.grass, 0, 405);

        renderScore(g);
        rendered = true;
    }

    private void renderScore(Painter g) {
        g.setFont(Typeface.SANS_SERIF, 25);
        g.setColor(Color.GRAY);
        g.drawString("" + playScore / 100, 20, 30);
    }

    private void renderClouds(Painter g) {
        g.drawImage(Assets.cloud1, (int) cloud.getX(), (int) cloud.getY(), 100, 60);
        g.drawImage(Assets.cloud2, (int) cloud2.getX(), (int) cloud2.getY(), 100, 60);
    }

    private void renderSun(Painter g) {
        g.setColor(Color.rgb(255, 165, 0));
        g.fillOval(715, -85, 170, 170);
        g.setColor(Color.YELLOW);
        g.fillOval(725, -75, 150, 150);
    }

    private void renderBlocks(Painter g) {
        for (Block b : blocks) {
            if (b.isVisible()) {
                g.drawImage(Assets.block, (int) b.getX(), (int) b.getY(), BLOCK_WIDTH, BLOCK_HEIGHT);
            }
        }
    }

    private void renderPlayer(Painter g) {
        if (player.isGrounded()) {
            if (player.isDucked()) {
                g.drawImage(Assets.duck, (int) player.getX(), (int) player.getY());
            } else {
                Assets.runAnim.render(g, (int) player.getX(), (int) player.getY(), player.getWidth(), player.getHeight());
            }
        } else {
            g.drawImage(Assets.jump, (int) player.getX(), (int) player.getY());
        }
    }

    /**
     * 手指上下滑动一定的距离时触发 jump 或 duck
     * @param e
     * @param scaledX
     * @param scaledY
     * @return
     */
    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            recentTouchY = scaledY;
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            if (scaledY - recentTouchY < -50) {
                player.jump();
            } else if (scaledY - recentTouchY > 50) {
                player.duck();
            }
        }
        return true;
    }
}
