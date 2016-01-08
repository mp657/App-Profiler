package appprofiler.appprofilerv1;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.ListActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.TestCase;

/**
 * Created by soory_000 on 11/25/2015.
 */public class AP_MenuLogManagerTest extends ActivityInstrumentationTestCase2<AP_Menu> {

    private Activity activity;
    private ListView lv;
    private CallLogger contextInfoActivity;


    private TextView tvInfo;

    public AP_MenuLogManagerTest() {
        super(AP_Menu.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = (ListActivity) getActivity();
        lv = (ListView) activity.findViewById(android.R.id.list);

    }

    public void testCase1() {
        assertNotNull(activity);
        assertNotNull(lv);
    }

    public void testCase2() {

        Instrumentation instrumentation = getInstrumentation();
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(CallLogger.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                lv.performItemClick(lv, 2, 0);
                //lv is listview,4 is item position,0 is default id
            }
        });

        Activity currentActivity = getInstrumentation().waitForMonitor(monitor);
        contextInfoActivity = (CallLogger) currentActivity;

        assertNotNull(contextInfoActivity);
        tvInfo = (TextView) contextInfoActivity.findViewById(R.id.textView3);

        assertNotNull(tvInfo);
        assertEquals("Call Log Manager", tvInfo.getText().toString());

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        activity = null;
        lv = null;
        tvInfo = null;
        contextInfoActivity = null;
    }
}