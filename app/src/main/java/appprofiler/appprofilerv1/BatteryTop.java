package appprofiler.appprofilerv1;

/**
 * Created by Maddy on 24-11-2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BatteryTop extends Activity
{
    private BatteryInfo bi = null;
    private SensorInfo si = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main3);

        bi = new BatteryInfo((TextView)findViewById(R.id.battery));

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(bi, mIntentFilter);
            }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }
    public void call(View view)
    {
        Intent i = new Intent(this,PowerManagerActivity.class);
        startActivity(i);
    }

}
