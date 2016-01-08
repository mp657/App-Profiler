package appprofiler.appprofilerv1;

/**
 * Created by Maddy on 24-11-2015.
 */

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.List;

/* Handle all battery change events */
public class SensorInfo {
    private SensorManager sm = null;
    private SensorView sv = null;

    private class SensorView {
        private TextView tv = null;

        /* Show the sensor informations on screen */
        public void showStat(StringBuilder str) {
            tv.setText(str);
        }

        public SensorView(TextView _tv) {
            tv = _tv;
        }
    }

    public SensorInfo(SensorManager sm, TextView tv) {
        sv = new SensorView(tv);

        StringBuilder builder = new StringBuilder("Sensors consumption\n");
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensors) {
            builder.append("  " + s.getName() + ": " + s.getPower() + "mA\n");
        }
        sv.showStat(builder);
    }
}
