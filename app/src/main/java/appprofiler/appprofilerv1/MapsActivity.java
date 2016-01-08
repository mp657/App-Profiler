package appprofiler.appprofilerv1;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

public class MapsActivity extends Activity {



    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        //Bundle b = i.getExtras();
        ArrayList<String> lats = (ArrayList<String>) getIntent().getSerializableExtra("Latitude");
        ArrayList<String> longs = (ArrayList<String>) getIntent().getSerializableExtra("Longitude");

        String lat = i.getStringExtra("Latitude");
        String lon = i.getStringExtra("Longitude");

        //final LatLng latlng1 = new LatLng(Double.parseDouble(lat) , Double.parseDouble(lon));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            for (int i1 = 0; i1 < lats.size(); i1++) {
                String latco = lats.get(i1);
                String longst = longs.get(i1);

                final LatLng latlng1 = new LatLng(Double.parseDouble(lats.get(i1)),Double.parseDouble(longs.get(i1)));
                    Marker TP = googleMap.addMarker(new MarkerOptions().
                            position(latlng1).title("App Profiler"));
                }
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
