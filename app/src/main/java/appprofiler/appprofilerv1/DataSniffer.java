package appprofiler.appprofilerv1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DataSniffer extends Activity {
    public static String    IPAddress;

    public static String    country_code;
    public static String    Country;
    static ArrayList<String> Latitude = new ArrayList<String>();
    static ArrayList<String> Longitude = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sniffer);

        try {
            Networkanalyser();

        } catch (IOException e) {
            e.printStackTrace();

        }
        new Thread(new Threadcheck()).start();

    }

    /*Thread*/
    class Threadcheck  implements Runnable {
        String line = null;
        URL url;
        String IPAddress, a;


        //  TextView temp = (TextView) findViewById(R.id.textView);
        @Override
        public void run() {
           // Intent i = new Intent(this,IPExtractData.class);
            //IPExtract e1 = new IPExtract("E:/test1.pcap");
            //e1.main();
       //     IPExtractData ie = new IPExtractData();

            List<String> ipe =new ArrayList<String>();
            ipe.add("69.171.230.68");
            ipe.add("98.139.183.24");

            //e1.ipaddresscollection();
            try {
                int value = ipe.size();
                for (int i = 0; i < ipe.size(); i++) {
                    IPAddress = ipe.get(i);
                    if (!(IPAddress.equals(null))) {
                        a = "https://freegeoip.net/json/" + IPAddress;
                        try {
                            url = new URL(a);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {

                            URLConnection conn = url.openConnection();

                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(conn.getInputStream()));

                            String inputLine;
                            while ((inputLine = br.readLine()) != null) {
                                System.out.println(inputLine);
                                JSONObject js = new JSONObject(inputLine);
                                Latitude.add(js.getString("latitude"));
                                System.out.println(Latitude);
                                Longitude.add(js.getString("longitude"));
                                System.out.println(Longitude);


                                //  Intent i = new Intent(this,MapsActivity.class);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_sniffer, menu);
        return true;
    }

    public void Networkanalyser() throws IOException {
        Intent NA_intent;
        NA_intent = getIntent();

        Bundle bundle = NA_intent.getExtras();
        String text = bundle.getString("SelectedValue");
        TextView Appname = (TextView) findViewById(R.id.AppName);
        Appname.setText(text.toString().toUpperCase());
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> lstAppInfo = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        String canonicalName;
        try {
            for (ApplicationInfo ai : lstAppInfo) {
                String n = (String) pm.getApplicationLabel(ai);
                if (n.equals(text) || text.equals(n)) {
                    canonicalName = ai.packageName;
                    String inputLine;

                            NetTrafficAnalyser nt = new NetTrafficAnalyser();
                            String datareceived = nt.Networktraffic(this, getPackageManager(), canonicalName);
                            String datasent = nt.Networktrafficsent(this, getPackageManager(), canonicalName);

                            if (!(datareceived.equals("Unsupported"))) {
                                //TextView t = (TextView) findViewById(R.id.otvBR);
                                TextView t = (TextView) findViewById(R.id.tvRBR);
                                float datareceivedMb = Float.parseFloat(datareceived) / (1024 * 1024);
                                t.setText(String.format("%.2f Mb", datareceivedMb));
                            }
                            if (!(datasent.equals("Unsupported"))) {
                                TextView t = (TextView) findViewById(R.id.tvRBS);
                                float datasentMb = Float.parseFloat(datasent) / (1024 * 1024);
                                t.setText(String.format("%.2f Mb", datasentMb));
                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                                alert.setMessage("Not supported");
                                alert.show();
                            }
                            //TextView IPtv=(TextView) findViewById(R.id.tvRIP);
                            //IPtv.setText(IPAddress);

                        }
                    }

                }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void map(View view)
    {
        try {
            Intent gmap = new Intent(DataSniffer.this,MapsActivity.class);
           gmap.putExtra("Latitude", Latitude);
            gmap.putExtra("Longitude", Longitude);
            startActivity(gmap);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}

