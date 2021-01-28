package com.gjh.learn.elliogame.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;

import com.gjh.learn.elliogame.animation.Animation;
import com.gjh.learn.elliogame.animation.Frame;
import com.gjh.learn.elliogame.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;

public class Assets {

    private static SoundPool soundPool;
    public static Bitmap welcome, block, cloud1, cloud2, duck, grass, jump, run1, run2, run3, run4, run5, scoreDown, score, startDown, start;
    public static Animation runAnim;
    public static int hitID, onJumpID;

    public static void load() {
        welcome = loadBitMap("welcome.png", false);
        block = loadBitMap("block.png", false);
        cloud1 = loadBitMap("cloud1.png", true);
        cloud2 = loadBitMap("cloud2.png", true);
        duck = loadBitMap("duck.png", true);
        grass = loadBitMap("grass.png", false);
        jump = loadBitMap("jump.png", true);
        run1 = loadBitMap("run_anim1.png", true);
        run2 = loadBitMap("run_anim2.png", true);
        run3 = loadBitMap("run_anim3.png", true);
        run4 = loadBitMap("run_anim4.png", true);
        run5 = loadBitMap("run_anim5.png", true);
        scoreDown = loadBitMap("score_button_down.png", true);
        score = loadBitMap("score_button.png", true);
        startDown = loadBitMap("start_button_down.png", true);
        start = loadBitMap("start_button.png", true);
        runAnim = new Animation(new Frame(run1, .1f), new Frame(run2, .1f), new Frame(run3, .1f), new Frame(run4, .1f), new Frame(run5, .1f));
        hitID = loadSound("hit.wav");
        onJumpID = loadSound("onjump.wav");
    }

    private static Bitmap loadBitMap(String filename, boolean transparency) {
        InputStream inputStream = null;
        try {
            inputStream = GameMainActivity.asserts.open(filename);
        } catch (IOException e) {
            LogUtil.e("loadBitmap error: %s", e);
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (transparency) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    private static int loadSound(String filename) {
        int soundID = 0;
        if (soundPool == null) {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            soundID = soundPool.load(GameMainActivity.asserts.openFd(filename), 1);
        } catch (IOException e) {
            LogUtil.e("loadSound error: %s", e);
        }
        return soundID;
    }

    public static void playSound(int soundID) {
        soundPool.play(soundID, 1, 1, 1, 0, 1);
    }
}
