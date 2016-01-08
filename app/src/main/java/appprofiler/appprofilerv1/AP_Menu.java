package appprofiler.appprofilerv1;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class AP_Menu extends ListActivity{

    private ArrayList<String> MenuList = null;
    private MenuLstPopulator menuListPopulator = null;
    private String sAppName;
    private ListView list;
    String[] itemname = {"Usage Monitor","Data Sniffer","Battery Radar", "Log Manager", "Version Updates","Frames Calculation","Task Manager","App Advisor"};
    Integer[] imgid = {R.drawable.cpu,R.drawable.mobiledata,R.drawable.battery,R.drawable.logmanager,R.drawable.version,R.drawable.fps,
            R.drawable.taskmanager,R.drawable.app_advisor};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ap__menu);
            Intent DS_Intent = new Intent(this,ActivityMain.class);
            CustomAdapter adapter = new CustomAdapter(this, itemname, imgid);
            list = (ListView) findViewById(android.R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    String Selecteditem = itemname[+position];
                    if(Selecteditem.equals("Usage Monitor")){
                        Intent appInfo = new Intent(AP_Menu.this, ActivityMain.class);
                        startActivity(appInfo);
                    }
                    else if(Selecteditem.equals("Data Sniffer")){
                        Intent appInfo = new Intent(AP_Menu.this, ApplicationListDataSniff.class);
                        startActivity(appInfo);
                    }
                    else if(Selecteditem.equals("Log Manager")){
                        Intent appInfo = new Intent(AP_Menu.this, CallLogger.class);
                        startActivity(appInfo);
                    }
                    else if(Selecteditem.equals("Task Manager")){
                        Intent appInfo = new Intent(AP_Menu.this, AndroidTaskManager.class);
                        startActivity(appInfo);
                    }
                    else if(Selecteditem.equals("Version Updates")){
                        Intent appInfo = new Intent(AP_Menu.this, ApkListActivity.class);
                        startActivity(appInfo);
                    }
                    else if(Selecteditem.equals("Frames Calculation")){
                        Intent appInfo = new Intent(AP_Menu.this, MaxFPSActivity.class);
                        startActivity(appInfo);
                    }
                    else if(Selecteditem.equals("Battery Radar")){
                        Intent appInfo = new Intent(AP_Menu.this, BatteryTop.class);
                        startActivity(appInfo);
                    }
                    else if(Selecteditem.equals("App Advisor")){
                        Intent appInfo = new Intent(AP_Menu.this, PowerManagerActivity.class);
                        startActivity(appInfo);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
