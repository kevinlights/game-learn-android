package com.gjh.learn.elliogame.util;

import android.util.Log;

public class LogUtil {
    public static final String LOG_TAG = "game-base-framework";

    public static final int VERBOSE = 0;
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;

    private static int level = 1;

    /**
     * debug
     * @param msg
     * @param args
     */
    public static void d(String msg, Object... args) {
        if (level < INFO) {
            Log.d(LOG_TAG, String.format(msg, args));
        }
    }

    /**
     * info
     * @param msg
     * @param args
     */
    public static void i(String msg, Object... args) {
        if (level < WARN) {
            Log.i(LOG_TAG, String.format(msg, args));
        }
    }

    /**
     * warn
     * @param msg
     * @param args
     */
    public static void w(String msg, Object... args) {
        if (level < ERROR) {
            Log.w(LOG_TAG, String.format(msg, args));
        }
    }

    /**
     * error
     * @param msg
     * @param args
     */
    public static void e(String msg, Object... args) {
        if (level <= ERROR) {
            Log.e(LOG_TAG, String.format(msg, args));
        }
    }

    /**
     * verbose
     * @param msg
     * @param args
     */
    public static void v(String msg, Object... args) {
        if (level < DEBUG) {
            Log.v(LOG_TAG, String.format(msg, args));
        }
    }
}
