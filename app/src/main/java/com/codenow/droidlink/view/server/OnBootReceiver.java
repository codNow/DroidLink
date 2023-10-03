

package com.codenow.droidlink.view.server;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;


public class OnBootReceiver extends BroadcastReceiver {

    private static final String TAG = "OnBootReceiver";

    public void onReceive(Context context, Intent arg1)
    {
        if(Intent.ACTION_BOOT_COMPLETED.equals(arg1.getAction())) {
        final SharedPreferences prefs = context.getSharedPreferences("BootPrefs", Context.MODE_PRIVATE);
        Defaults defaults = new Defaults(context);
        if(prefs.getBoolean(Constants.PREFS_KEY_SETTINGS_START_ON_BOOT, defaults.getStartOnBoot())) {
            Intent intent = new Intent(context, MainService.class);
            intent.setAction(MainService.ACTION_START);
            intent.putExtra(MainService.EXTRA_PORT, prefs.getInt(Constants.PREFS_KEY_SETTINGS_PORT, defaults.getPort()));
            intent.putExtra(MainService.EXTRA_PASSWORD, prefs.getString(Constants.PREFS_KEY_SETTINGS_PASSWORD, defaults.getPassword()));
            intent.putExtra(MainService.EXTRA_FILE_TRANSFER, prefs.getBoolean(Constants.PREFS_KEY_SETTINGS_FILE_TRANSFER, defaults.getFileTransfer()));
            intent.putExtra(MainService.EXTRA_VIEW_ONLY, prefs.getBoolean(Constants.PREFS_KEY_SETTINGS_VIEW_ONLY, defaults.getViewOnly()));
            intent.putExtra(MainService.EXTRA_SCALING, prefs.getFloat(Constants.PREFS_KEY_SETTINGS_SCALING, defaults.getScaling()));
            intent.putExtra(MainService.EXTRA_ACCESS_KEY, prefs.getString(Constants.PREFS_KEY_SETTINGS_ACCESS_KEY, defaults.getAccessKey()));

            long delayMillis =  1000L * prefs.getInt(Constants.PREFS_KEY_SETTINGS_START_ON_BOOT_DELAY, defaults.getStartOnBootDelay());
            if(delayMillis > 0) {
                Log.i(TAG, "onReceive: configured to start delayed by " + delayMillis/1000 + "s");
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                PendingIntent pendingIntent;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    pendingIntent = PendingIntent.getForegroundService(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                } else {
                    pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                }
                alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delayMillis, pendingIntent);
            } else {
                Log.i(TAG, "onReceive: configured to start immediately");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent);
                } else {
                    context.startService(intent);
                }
            }
        } else {
            Log.i(TAG, "onReceive: configured NOT to start");
        }
        }
    }

}
