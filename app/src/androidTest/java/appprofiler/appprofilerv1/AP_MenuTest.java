package appprofiler.appprofilerv1;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.ListActivity;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.TextView;


/**
 * Created by soory_000 on 11/25/2015.
 */


    public final class AP_MenuTest extends ActivityInstrumentationTestCase2<AP_Menu> {

    private AP_Menu AP;
    private TextView mFirstTestText;

    public AP_MenuTest() {
        super(AP_Menu.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AP = getActivity();
        mFirstTestText =
                (TextView) AP
                        .findViewById(R.id.TxtVw_AppName);

    }
    public void testMyFirstTestTextView_labelText() {
        final String expected ="App Profiler";
        final String actual =mFirstTestText.getText().toString();
        assertEquals(expected, actual);
    }
}
