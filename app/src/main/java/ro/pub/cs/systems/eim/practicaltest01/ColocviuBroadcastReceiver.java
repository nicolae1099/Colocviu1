package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ColocviuBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadcastReceiver", "onReceive() callback method has been invoked");
        String action = intent.getAction();
        if (action != null) {
            if (action.equals(Constants.ACTION_STRING)) {
                String result = intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA);
                Log.d("BroadcastReceiver", result);
            }
        }
    }
}
