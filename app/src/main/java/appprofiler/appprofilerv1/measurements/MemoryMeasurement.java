package appprofiler.appprofilerv1.measurements;

import android.app.ActivityManager;
import android.content.Context;

public class MemoryMeasurement implements Measurement{
    private Context context;

    public MemoryMeasurement(Context context)
    {
        this.context = context;
    }


    public Double getMeasurement() {

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double total = mi.totalMem;
        double free = mi.availMem;
        return Double.valueOf(free/total);
    }

    public double getMemoryFreeValue()
    {
        return getMeasurement().doubleValue();
    }
}
