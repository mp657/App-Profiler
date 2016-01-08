package appprofiler.appprofilerv1;

import android.graphics.Canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

public class MaxFPSThread extends Thread {
    private static final String TAG = "MaxFPSThread";
    public static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    public static final Bitmap.Config FAST_BITMAP_CONFIG = Bitmap.Config.RGB_565;

    private Context mContext;
    private SurfaceHolder surfaceHolder;
    private int width;
    private int height;
    private boolean isSurfaceCreated;
    private boolean isRunning;
    private boolean isPaused;

    private long lastTickMs;
    private long curTickMs;
    private int tickDelta;

    private long lastFrameDraw = 0;
    private int frameSamplesCollected = 0;
    private int frameSampleTime = 0;
    private int fps = 0;

    private Paint basePaint;
    private Paint fpsPaint;
    private Bitmap fullScreenBitmap;
    private Bitmap layerBitmap;
    private Canvas layerCanvas;

    public MaxFPSThread(SurfaceHolder surfaceHolder, Context context) {
        Log.d(TAG, "New Instance");
// get handles to some important objects
        this.surfaceHolder = surfaceHolder;
        this.mContext = context;
        init();
    }

    public void setSurfaceCreated(boolean surfaceCreated) {
        isSurfaceCreated = surfaceCreated;
    }

    public void setSurfaceSize(int width, int height) {
// synchronized to make sure these all change atomically
        synchronized (surfaceHolder) {
            if (width != this.width && height != this.height) {
                this.width = width;
                this.height = height;
            }
        }
    }

    public void setRunning(boolean b) {
        isRunning = b;
    }

    public void pause() {
        synchronized (surfaceHolder) {
            isPaused = true;
        }
    }

    public void unpause() {
        synchronized (surfaceHolder) {
            isPaused = false;
        }
    }

    @Override
    public void run() {
// wait for surface to become available
        while (!isSurfaceCreated && isRunning) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
// Set the last tick to right now.
        lastTickMs = System.currentTimeMillis();
        while (isRunning) {
            while (isPaused && isRunning) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
// coming out of pause, we don't want to jump ahead so we have to set the last tick to now.
                lastTickMs = System.currentTimeMillis();
            }
            Canvas c = null;
            try {
                synchronized (surfaceHolder) {
                    c = surfaceHolder.lockCanvas();
                    if (isSurfaceCreated) {
                        doDraw(c);
                    }
                }
            } catch (Throwable t) {
                throw new RuntimeException(t);
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
        cleanUp();
    }

    private void doDraw(Canvas canvas) {
        int worldWidth = this.width;
        int worldHeight = this.height;
        Paint basePaint = this.basePaint;
        canvas.drawRect(0, 0, worldWidth, worldHeight, basePaint);

// BEGIN TEST CODE
        Bitmap fullScreenBitmap = this.fullScreenBitmap;
        Canvas layerCanvas = this.layerCanvas;
        if (fullScreenBitmap == null) {
// init
            this.fullScreenBitmap = Bitmap.createBitmap(worldWidth, worldHeight, FAST_BITMAP_CONFIG);
            fullScreenBitmap = this.fullScreenBitmap;
        }
        if (layerCanvas == null) {
            this.layerBitmap = Bitmap.createBitmap(worldWidth, worldHeight, BITMAP_CONFIG);
            this.layerCanvas = new Canvas(this.layerBitmap);
            layerCanvas = this.layerCanvas;
        }
        int howMany = 0;
// Test full screen layer draws in different modes
        for (int i = 0; i < howMany; i++) {
            layerCanvas.drawBitmap(fullScreenBitmap, 0, 0, basePaint);
        }
        howMany = 100000;
// Test divide vs multiply
        int a;
        int numerator = 10;
        for (int i = 0; i < howMany; i++) {
            a = (int) (numerator * .5f);
        }
// END TEST CODE
        canvas.drawBitmap(layerBitmap, 0, 0, basePaint);
        long now = System.currentTimeMillis();
        if (lastFrameDraw != 0) {
            int time = (int) (now - lastFrameDraw);
            frameSampleTime += time;
            frameSamplesCollected++;
            if (frameSamplesCollected == 10) {
                fps = (int) (10000 / frameSampleTime);
                frameSampleTime = 0;
                frameSamplesCollected = 0;
            }
            canvas.drawText(fps + " fps", worldWidth - 275, worldHeight - 400, fpsPaint);
        }
        lastFrameDraw = now;

    }

    private void init() {
        basePaint = new Paint();
        fpsPaint = new Paint();
        fpsPaint.setARGB(255,255,255,255);
        fpsPaint.setTextSize(65);
    }

    private void cleanUp() {
        basePaint = null;
        fpsPaint = null;
        fullScreenBitmap = null;
    }

}