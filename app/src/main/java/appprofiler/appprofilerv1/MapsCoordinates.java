/*package appprofiler.appprofilerv1;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;


public class MapsCoordinates {
new Thread(){
    public HashMap<String, String> getLatitude(String IPAddress) {
        URL url;
        HashMap<String, String> latlong = new HashMap<String, String>();
        try {
            String a = "https://freegeoip.net/json/" + IPAddress;
            url = new URL(a);


            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            String Latitude;
            String Longitude;

            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
                JSONObject js = new JSONObject(inputLine);
                Latitude = js.getString("latitude");
                System.out.println(Latitude);
                Longitude = js.getString("longitude");
                System.out.println(Longitude);
                latlong.put("Latitude", Latitude);
                latlong.put("Longitude", Longitude);


            }
            br.close();
            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return latlong;
    }
}*/
