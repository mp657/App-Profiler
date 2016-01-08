package appprofiler.appprofilerv1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.TrafficStats;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.Comparator;


import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.net.TrafficStats;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Comparator;
import java.util.List;

public class ApplicationListDataSniff extends ListActivity {

    private TextView tvSupported, tvDataUsageWiFi, tvDataUsageMobile, tvDataUsageTotal;
    private ListView lvApplications;
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private long dataUsageTotalLast = 0;
    private static ListView list_view ;
    ArrayAdapter<ApplicationItem> adapterApplications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_application_list_data_sniff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvSupported = (TextView) findViewById(R.id.tvSupported);

        tvDataUsageWiFi = (TextView) findViewById(R.id.tvDataUsageWiFi);
        tvDataUsageMobile = (TextView) findViewById(R.id.tvDataUsageMobile);
        tvDataUsageTotal = (TextView) findViewById(R.id.tvDataUsageTotal);

        if (TrafficStats.getTotalRxBytes() != TrafficStats.UNSUPPORTED && TrafficStats.getTotalTxBytes() != TrafficStats.UNSUPPORTED) {
            handler.postDelayed(runnable, 0);
            initAdapter();
            lvApplications = (ListView) findViewById(R.id.lvApplications);
            lvApplications.setAdapter(adapterApplications);


        } else {
            tvSupported.setVisibility(View.VISIBLE);
        }
        listView();

    }
public void listView(){
    list_view = (ListView)findViewById(R.id.lvApplications);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Appname = null;
                try {
                    ApplicationItem selectedFromList = (ApplicationItem) (list_view.getItemAtPosition(position));
                    Appname = selectedFromList.getApplicationLabel(getApplicationContext().getPackageManager());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Intent DS_Intent = new Intent("appprofiler.appprofilerv1.DataSniffer");
                    DS_Intent.putExtra("SelectedValue", Appname);
                        startActivity(DS_Intent);


                } catch (ActivityNotFoundException e) {
                    Toast.makeText(ApplicationListDataSniff.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(ApplicationListDataSniff.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        }

    public Handler handler = new Handler();
        public Runnable runnable = new Runnable() {
            public void run() {
                long mobile = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes();
                long total = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
                tvDataUsageWiFi.setText("" + (total - mobile) / 1024 + " Kb");
                tvDataUsageMobile.setText("" + mobile / 1024 + " Kb");
                tvDataUsageTotal.setText("" + total / 1024 + " Kb");
                if (dataUsageTotalLast != total) {
                    dataUsageTotalLast = total;
                    updateAdapter();
                }
                handler.postDelayed(runnable, 5000);
            }
        };

        public void initAdapter() {

            adapterApplications = new ArrayAdapter<ApplicationItem>(getApplicationContext(), R.layout.item_install_application) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    ApplicationItem app = getItem(position);

                    final View result;
                    if (convertView == null) {
                        result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_install_application, parent, false);
                    } else {
                        result = convertView;
                    }


                    TextView tvAppName = (TextView) result.findViewById(R.id.tvAppName);
                    TextView tvAppTraffic = (TextView) result.findViewById(R.id.tvAppTraffic);

                    // TODO: resize once
                    final int iconSize = Math.round(32 * getResources().getDisplayMetrics().density);
                    tvAppName.setCompoundDrawablesWithIntrinsicBounds(
                            //app.icon,
                            new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(
                                    ((BitmapDrawable) app.getIcon(getApplicationContext().getPackageManager())).getBitmap(), iconSize, iconSize, true)
                            ),
                            null, null, null
                    );
                    tvAppName.setText(app.getApplicationLabel(getApplicationContext().getPackageManager()));
                    tvAppTraffic.setText(Integer.toString(app.getTotalUsageKb()) + " Kb");

                    return result;
                }

                @Override
                public int getCount() {
                    return super.getCount();
                }

                @Override
                public Filter getFilter() {
                    return super.getFilter();
                }

            };

// TODO: resize icon once
            for (ApplicationInfo app : getApplicationContext().getPackageManager().getInstalledApplications(0)) {
                ApplicationItem item = ApplicationItem.create(app);
                if (item != null) {
                    adapterApplications.add(item);
                }
            }


        }



    protected void onListItemClick(ListView l,View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String value = (String)l.getItemAtPosition(position);

        try{
                Intent DS_Intent = new Intent(this,DataSniffer.class);
                DS_Intent.putExtra("SelectedValue",value);

                if(DS_Intent != null) {
                    startActivity(DS_Intent);
                }

        }catch(ActivityNotFoundException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

        public void updateAdapter() {
            for (int i = 0, l = adapterApplications.getCount(); i < l; i++) {
                ApplicationItem app = adapterApplications.getItem(i);
                app.update();
            }

            adapterApplications.sort(new Comparator<ApplicationItem>() {
                @Override
                public int compare(ApplicationItem lhs, ApplicationItem rhs) {
                    return (int) (rhs.getTotalUsageKb() - lhs.getTotalUsageKb());
                }
            });
            adapterApplications.notifyDataSetChanged();
        }

}