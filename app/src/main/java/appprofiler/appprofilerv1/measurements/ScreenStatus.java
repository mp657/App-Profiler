package appprofiler.appprofilerv1.measurements;

import android.content.ContentResolver;
import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;

public class ScreenStatus implements Measurement{

    private PowerManager powermanager;
    private ContentResolver contentResolver;

    public ScreenStatus(Context context) {
        powermanager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        contentResolver = context.getContentResolver();
    }

    public boolean getScreenStatusValue()
    {
        return powermanager.isScreenOn();
    }

    public double getScreenBrightnessValue()
    {
        try
        {
            return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Settings.SettingNotFoundException e) { e.printStackTrace(); }
        return 0;
    }
}
