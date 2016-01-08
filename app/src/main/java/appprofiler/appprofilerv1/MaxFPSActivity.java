package appprofiler.appprofilerv1;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MaxFPSActivity extends Activity {

    private MaxFPSView maxFpsView;

    private MaxFPSThread maxFpsThread;;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
    }

    @Override

    protected void onStart() {
        super.onStart();
        startGame();
    }

    private void startGame() {
        maxFpsView = (MaxFPSView) findViewById(R.id.maxfps);

// hack to force the surface view to set the parameters
        maxFpsThread = maxFpsView.getThread();
        maxFpsThread.setRunning(true);
        maxFpsThread.start();
    }
}