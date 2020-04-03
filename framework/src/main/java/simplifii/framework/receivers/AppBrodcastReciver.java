package simplifii.framework.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashSet;
import java.util.Set;

public class AppBrodcastReciver extends BroadcastReceiver {

    public Set<SetTabListener> listeners = new HashSet<>();

    public AppBrodcastReciver(SetTabListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        for (SetTabListener listener : listeners) {
            listener.onReceive(intent);
        }
    }

    public void removeListener(SetTabListener listener) {
        this.listeners.remove(listener);
    }

    public static interface SetTabListener {
        public void onReceive(Intent intent);
    }

    public static void sendBroadcast(Context context, String action) {
        Intent intent = new Intent(action);
        context.sendBroadcast(intent);
    }
    public static void sendBroadcast(Context context, String action, Bundle b) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtras(b);
        context.sendBroadcast(intent);
    }
}
