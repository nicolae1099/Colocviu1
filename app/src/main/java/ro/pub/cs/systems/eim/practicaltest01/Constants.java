package ro.pub.cs.systems.eim.practicaltest01;

public class Constants {
    public static final String INPUT1 = "input1";
    public static final String INPUT2 = "input2";
    public static final String SUM = "sum";
    public static final long SLEEP_TIME = 1000;
    public static final String BROADCAST_RECEIVER_EXTRA =  "broadcast_receiver_extra";

    public static final String BROADCAST_RECEIVER_TAG = "[Message]";

    final public static String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01.arithmeticmean",
            "ro.pub.cs.systems.eim.practicaltest01.geometricmean"
    };

    public static final int NUMBER_OF_CLICKS_THRESHOLD = 5;
}
