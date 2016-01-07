package org.whispersystems.libsupplychain;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;
import java.io.IOException;
import java.util.Date;
import org.whispersystems.jobqueue.JobManager;
import org.whispersystems.libsupplychain.model.ClientCrashPost;
import org.whispersystems.libsupplychain.util.LogcatUtil;

public class CrashReporter
  implements Thread.UncaughtExceptionHandler
{
  private static final String KEY_CRASH_TIME = "CrashReporter.KEY_CRASH_TIME";
  private static final String KEY_CRASH_TYPE = "CrashReporter.KEY_CRASH_TYPE";
  private static final String TAG = CrashReporter.class.getName();
  private final ClientInfo clientInfo;
  private final ConnectionFactory connectionFactory;
  private final Context context;
  private final JobManager jobManager;
  private final SharedPreferences preferences;

  public CrashReporter(Context paramContext, JobManager paramJobManager, ConnectionFactory paramConnectionFactory, ClientInfo paramClientInfo)
  {
    this.context = paramContext;
    this.jobManager = paramJobManager;
    this.connectionFactory = paramConnectionFactory;
    this.clientInfo = paramClientInfo;
    this.preferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
  }

  private CrashRecord getRecordedCrash()
  {
    String str = this.preferences.getString("CrashReporter.KEY_CRASH_TYPE", null);
    Long localLong = Long.valueOf(this.preferences.getLong("CrashReporter.KEY_CRASH_TIME", -1L));
    if ((str == null) || (localLong.longValue() < 0L))
      return null;
    return new CrashRecord(str, localLong);
  }

  private void handleReportCrash(CrashRecord paramCrashRecord)
  {
    try
    {
      ClientCrashPost localClientCrashPost = new ClientCrashPost(this.clientInfo, paramCrashRecord.getCrashType(), paramCrashRecord.getCrashTime(), LogcatUtil.grabLogcat());
      this.jobManager.add(new CrashReportingJob(this.context, this.connectionFactory, localClientCrashPost));
      return;
    }
    catch (IOException localIOException)
    {
      Log.e(TAG, "error grabbing logcat 0.o", localIOException);
    }
  }

  private void recordCrash(Throwable paramThrowable)
  {
    Long localLong = Long.valueOf(-1L);
    String str = null;
    if (paramThrowable != null)
    {
      str = paramThrowable.getClass().getName();
      localLong = Long.valueOf(new Date().getTime());
      Log.d(TAG, "recording crash of type >> " + str);
      Log.w(TAG, "traces >> ", paramThrowable);
    }
    this.preferences.edit().putString("CrashReporter.KEY_CRASH_TYPE", str).putLong("CrashReporter.KEY_CRASH_TIME", localLong.longValue()).commit();
  }

  private void scheduleRestart()
  {
    PendingIntent localPendingIntent;
    if (this.clientInfo.getAppRestartDelay().longValue() < 0L)
    {
      Intent localIntent1 = new Intent("org.whispersystems.libsupplychain.FORCE_RESTART");
      localPendingIntent = PendingIntent.getBroadcast(this.context, 1337, localIntent1, 0);
    }
    for (Long localLong = Long.valueOf(5000L); ; localLong = this.clientInfo.getAppRestartDelay())
    {
      ((AlarmManager)this.context.getSystemService("alarm")).set(0, new Date().getTime() + localLong.longValue(), localPendingIntent);
      return;
      Intent localIntent2 = this.context.getPackageManager().getLaunchIntentForPackage(this.clientInfo.getPackageName());
      localPendingIntent = PendingIntent.getActivity(this.context, 1337, localIntent2, 1073741824);
    }
  }

  public void start()
  {
    CrashRecord localCrashRecord = getRecordedCrash();
    if (localCrashRecord != null)
    {
      handleReportCrash(localCrashRecord);
      recordCrash(null);
    }
  }

  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    recordCrash(paramThrowable);
    scheduleRestart();
    System.exit(1);
  }

  protected class CrashRecord
  {
    private final Long crashTime;
    private final String crashType;

    public CrashRecord(String paramLong, Long arg3)
    {
      this.crashType = paramLong;
      Object localObject;
      this.crashTime = localObject;
    }

    public Long getCrashTime()
    {
      return this.crashTime;
    }

    public String getCrashType()
    {
      return this.crashType;
    }
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.CrashReporter
 * JD-Core Version:    0.6.2
 */