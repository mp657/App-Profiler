package appprofiler.appprofilerv1;

/**
 * Created by soory_000 on 12/1/2015.
 */
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class LastMod {
     static List<String> ipadresss;


    public LastMod(String location) {
        File dir = new File(location);
        File[] files = dir.listFiles();

     /*   Arrays.sort(files, new Comparator() {
            public int compare(Object o1, Object o2) {
                return compare((File) o1, (File) o2);
            }

            private int compare(File f1, File f2) {
                long result = f2.lastModified() - f1.lastModified();
                if (result > 0) {
                    return 1;
                } else if (result < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });*/
        System.out.println(Arrays.asList(files));

        String s = String.valueOf(Arrays.asList(files).get(0));
        IPExtract exe = new IPExtract(s);
        exe.main(null);
        ipadresss = exe.ipaddresscollection();
        sendip();
    }

    public List<String> sendip() {
        return ipadresss;
    }
}