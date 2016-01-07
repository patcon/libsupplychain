package org.whispersystems.libsupplychain;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class AlarmBooter extends BroadcastReceiver
{
  public static final String INTENT_ALARM_24_HOURS = "org.whispersystems.libsupplychain.INTENT_ALARM_24_HOURS";
  public static final String INTENT_FORCE_RESTART = "org.whispersystems.libsupplychain.FORCE_RESTART";
  public static final String KEY_TIME_LAST_ALARM = "KEY_TIME_LAST_ALARM";
  private static final String TAG = AlarmBooter.class.getName();

  private static Long getMsSinceLastAlarm(Context paramContext)
  {
    Long localLong = Long.valueOf(PreferenceManager.getDefaultSharedPreferences(paramContext).getLong("KEY_TIME_LAST_ALARM", -1L));
    if ((localLong.longValue() < 0L) || (localLong.longValue() > System.currentTimeMillis()))
      return Long.valueOf(86400000L);
    return Long.valueOf(System.currentTimeMillis() - localLong.longValue());
  }

  private static void handleAlarmFired(Context paramContext)
  {
    PreferenceManager.getDefaultSharedPreferences(paramContext).edit().putLong("KEY_TIME_LAST_ALARM", System.currentTimeMillis()).apply();
  }

  private static void handleDeviceBooted(Context paramContext)
  {
    Long localLong = Long.valueOf(86400000L - getMsSinceLastAlarm(paramContext).longValue());
    if (localLong.longValue() < 0L)
      localLong = Long.valueOf(0L);
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent("org.whispersystems.libsupplychain.INTENT_ALARM_24_HOURS"), 0);
    ((AlarmManager)paramContext.getSystemService("alarm")).setInexactRepeating(1, System.currentTimeMillis() + localLong.longValue(), 86400000L, localPendingIntent);
    Log.d(TAG, "scheduled 24 hour alarm to begin firing repeatedly in " + localLong + "ms");
  }

  public static void scheduleAlarmIfNotExists(Context paramContext)
  {
    if (PreferenceManager.getDefaultSharedPreferences(paramContext).getLong("KEY_TIME_LAST_ALARM", -1L) == -1L)
      handleDeviceBooted(paramContext);
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
      handleDeviceBooted(paramContext);
    do
    {
      return;
      if (paramIntent.getAction().equals("org.whispersystems.libsupplychain.INTENT_ALARM_24_HOURS"))
      {
        handleAlarmFired(paramContext);
        return;
      }
    }
    while (paramIntent.getAction().equals("org.whispersystems.libsupplychain.FORCE_RESTART"));
    Log.e(TAG, "received broadcast intent with unknown action " + paramIntent.getAction());
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.AlarmBooter
 * JD-Core Version:    0.6.2
 */