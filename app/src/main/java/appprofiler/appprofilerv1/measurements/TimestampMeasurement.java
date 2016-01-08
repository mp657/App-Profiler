package appprofiler.appprofilerv1.measurements;


public class TimestampMeasurement implements Measurement{

    public long getTimestampValue()
    {
        return (System.currentTimeMillis() / 1000);
    }
}
