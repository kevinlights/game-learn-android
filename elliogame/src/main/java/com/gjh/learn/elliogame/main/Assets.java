package com.gjh.learn.elliogame.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;

import com.gjh.learn.elliogame.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;

public class Assets {

    private static SoundPool soundPool;
    public static Bitmap welcome;

    public static void load() {
        welcome = loadBitMap("welcome.png", false);
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
