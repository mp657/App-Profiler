package appprofiler.appprofilerv1;

import android.util.Log;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MaxFPSView extends SurfaceView implements Callback {
    private static final String TAG = "LightRacerView";

    /** The thread that actually draws the animation */
    private MaxFPSThread thread;

    private boolean isSurfaceCreated;

    private int surfaceWidth, surfaceHeight;

    public MaxFPSView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "New Instance");
// register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.setFormat(PixelFormat.OPAQUE);
        holder.addCallback(this);

        setFocusable(true); // make sure we get key events
    }

    /**
     * Fetches the game thread corresponding to this LightRacerView.
     *
     * @return the game thread
     */
    public MaxFPSThread getThread() {
        if (thread == null || !thread.isAlive()) {
// create thread only; it's started in surfaceCreated()
            thread = new MaxFPSThread(getHolder(), getContext());
            updateThreadSurfaceState();
            if (surfaceHeight > 0 && surfaceWidth > 0) {
                thread.setSurfaceSize(surfaceWidth, surfaceHeight);
            }
        }
        return thread;
    }

    /**
     * Standard window-focus override. Notice focus lost so we can pause on focus lost. e.g. user switches to take a
     * call.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            if (thread != null) {
                thread.pause();
            }
        } else {
            if (thread != null) {
                thread.unpause();
            }
        }
    }

    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surfaceWidth = width;
        surfaceHeight = height;
        thread.setSurfaceSize(width, height);
    }

    /*
    * Callback invoked when the Surface has been created and is ready to be used.
    */
    public void surfaceCreated(SurfaceHolder holder) {
        isSurfaceCreated = true;
        updateThreadSurfaceState();
        if (!hasFocus()) {
            requestFocusFromTouch();
        }
    }

    /*
    * Callback invoked when the Surface has been destroyed and must no longer be touched. WARNING: after this method
    * returns, the Surface/Canvas must never be touched again!
    */
    public void surfaceDestroyed(SurfaceHolder holder) {
        isSurfaceCreated = false;
        updateThreadSurfaceState();
// we have to tell thread to shut down & wait for it to finish, or else
// it might touch the Surface after we return and explode
    }

    private void updateThreadSurfaceState() {
        if (thread != null) {
            thread.setSurfaceCreated(isSurfaceCreated);
        }
    }

    private void stopThread() {
        if (thread != null) {
            Log.d(TAG, "Stopping Thread");
            boolean retry = true;
            thread.setRunning(false);
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
            Log.d(TAG, "Thread Stopped");
        }
    }

    public void releaseAllResources() {
        Log.d(TAG, "Releasing Resources");
        if (getHolder() != null) {
            getHolder().removeCallback(this);
        }
        stopThread();
    }
}
