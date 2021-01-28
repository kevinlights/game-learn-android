package com.gjh.learn.android.game.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.gjh.learn.android.game.state.LoadState;
import com.gjh.learn.android.game.state.State;
import com.gjh.learn.android.game.util.InputHandler;
import com.gjh.learn.android.game.util.LogUtil;
import com.gjh.learn.android.game.util.Painter;

/**
 * 绘制策略：
 * 创建一个离屏图像，并且在准备好的时候将其渲染到屏幕。<br/>
 * gameImage 给出一个返回，创建一个变量来表示这个 gameImage 的 Canvas 对象，以将其传递给名为 graphics 的 Painter, <br/>
 * Painter 会处理 currentState 的绘制调用，将请求绘制的图像绘制到 gameCanvas <br/>
 *
 * created on 2021/1/28
 * @author kevinlights
 */
public class GameView extends SurfaceView implements Runnable{
    private Bitmap gameImage;
    private Rect gameImageSrc;
    private Rect gameImageDst;
    private Canvas gameCanvas;
    private Painter graphics;
    private Thread gameThread;
    private volatile boolean running = false;
    private volatile State currentState;
    private InputHandler inputHandler;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, int gameWidth, int gameHeight) {
        super(context);
        // 创建不透明的固定宽高的图像
        gameImage = Bitmap.createBitmap(gameWidth, gameHeight, Bitmap.Config.RGB_565);
        // 指定 gameImage 的哪一个区域应该绘制到屏幕上，这里是整个图像都绘制
        gameImageSrc = new Rect(0, 0, gameImage.getWidth(), gameImage.getHeight());
        // 指定当 gameImage 绘制到屏幕上的时候，应该如何缩放
        gameImageDst = new Rect();
        // 要把图像绘制到 gameImage 上，必须绘制到其 Canvas 上
        gameCanvas = new Canvas(gameImage);
        // 接受 gameCanvas 并执行当前状态所请求的绘制调用
        graphics = new Painter(gameCanvas);

        SurfaceHolder holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                LogUtil.d("surfaceCreated");
                initInput();
                if (currentState == null) {
                    setCurrentState(new LoadState());
                }
                initGame();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                LogUtil.d("surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                LogUtil.d("surfaceDestroyed");
                pauseGame();
            }
        });
    }

    public void setCurrentState(State newState) {
        newState.init();
        currentState = newState;
        inputHandler.setCurrentState(currentState);
    }

    private void initInput() {
        if (inputHandler == null) {
            inputHandler = new InputHandler();
        }
        setOnTouchListener(inputHandler);
    }

    private void initGame() {
        running = true;
        gameThread = new Thread(this, "game-thread");
        gameThread.start();
    }

    private void pauseGame() {
        running = false;
        while (gameThread.isAlive()) {
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                LogUtil.e("pause game error: %s", e);
            }
        }
    }

    private void updateAndRender(long delta) {
        currentState.update(delta / 1000f);
        currentState.render(graphics);
        renderGameImage();
    }

    private void renderGameImage() {
        // 锁定 Canvas 进行绘制，一次只允许一个 Thread 来绘制
        Canvas screen = getHolder().lockCanvas();
        if (null != screen) {
            screen.getClipBounds(gameImageDst);
            screen.drawBitmap(gameImage, gameImageSrc, gameImageDst, null);
            getHolder().unlockCanvasAndPost(screen);
        }
    }

    @Override
    public void run() {
        long updateDurationMillis = 0;
        long sleepDurationMillis = 0;
        while (running) {
            long beforeUpdateRender = System.nanoTime();
            long deltaMillis = sleepDurationMillis + updateDurationMillis;
            updateAndRender(deltaMillis);
            updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;
            sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);
            try {
                Thread.sleep(sleepDurationMillis);
            } catch (Exception e) {
                LogUtil.e("sleep game error: %s", e);
            }
        }
    }
}
