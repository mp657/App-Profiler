package appprofiler.appprofilerv1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public CustomAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        extratxt.setText("Description : "  + Description(itemname[position]));
        return rowView;

    }

    public String Description(String name) {
        String result = null;
        if (name.equals("Data Sniffer")) {
            result = "Locates destination server(s) in Google map";
        } else if (name.equals("Battery Radar")) {
            result = "Provides battery usuage per Application";
        } else if (name.equals("Usage Monitor")) {
            result = "CPU & Memory Consumed by Application";
        } else if (name.equals("Version Updates")) {
            result = "Looks for update in Play Store for Application";
        } else if (name.equals("Log Manager")) {
            result = "List calls made and received";
        } else if (name.equals("Frames Calculation")) {
            result = "Displays frames per second ";
        } else if (name.equals("Task Manager")) {
            result = "Provides User to kill or Uninstall or Detail Application";
        }
        else if (name.equals("App Advisor")) {
            result = "Monitors the health rate of application providing usage based on activity";
        }


        return result;
    }
}