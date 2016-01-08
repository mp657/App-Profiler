package appprofiler.appprofilerv1;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.TestCase;

/**
 * Created by soory_000 on 11/25/2015.
 */
public class AP_MenuListCountTest extends ActivityInstrumentationTestCase2<AP_Menu> {

    private AP_Menu AP;
    private ListView mFirstTestText;

    public AP_MenuListCountTest()
    {
        super(AP_Menu.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AP = getActivity();
        mFirstTestText =
                (ListView) AP
                        .findViewById(android.R.id.list);

    }
    public void testMyFirstTestTextView_labelText() {

        final int expected = 7;
        assertEquals(expected, mFirstTestText.getAdapter().getCount());
    }


}