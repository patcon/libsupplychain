package org.whispersystems.libsupplychain;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.IOUtils;
import org.whispersystems.jobqueue.JobManager;
import org.whispersystems.libsupplychain.model.ApkRelease;
import org.whispersystems.libsupplychain.util.DigestUtil;
import org.whispersystems.libsupplychain.util.MapperUtil;

public class SupplyChain extends BroadcastReceiver
{
  private static final String KEY_APK_DOWNLOAD_ID = "SupplyChain.KEY_APK_DOWNLOAD_ID";
  private static final String TAG = SupplyChain.class.getName();
  private final ApkInstaller apkInstaller;
  private final ConnectionFactory connectionFactory;
  private final Context context;
  private final CrashReporter crashReporter;
  private final JobManager jobManager;
  private final SharedPreferences preferences;

  protected SupplyChain(Context paramContext, JobManager paramJobManager, ClientInfo paramClientInfo, ConnectionFactory paramConnectionFactory)
  {
    this.context = paramContext;
    this.jobManager = paramJobManager;
    this.connectionFactory = paramConnectionFactory;
    this.preferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.apkInstaller = new ApkInstaller(paramContext, paramClientInfo);
    this.crashReporter = new CrashReporter(paramContext, paramJobManager, paramConnectionFactory, paramClientInfo);
  }

  private static DownloadManager.Request buildApkRequest(Uri paramUri)
  {
    DownloadManager.Request localRequest = new DownloadManager.Request(paramUri);
    localRequest.setAllowedOverRoaming(false);
    localRequest.setVisibleInDownloadsUi(true);
    localRequest.setMimeType("application/vnd.android.package-archive");
    return localRequest;
  }

  private static String buildKeyForEnqueuedDownload(Long paramLong)
  {
    return "apk_download_" + paramLong;
  }

  private ApkRelease getStoredEnqueuedApkDownload(Long paramLong)
    throws IOException
  {
    String str = this.preferences.getString(buildKeyForEnqueuedDownload(paramLong), null);
    if (str == null)
      return null;
    return (ApkRelease)MapperUtil.getMapper().readValue(str, ApkRelease.class);
  }

  // ERROR //
  private void handleDownloadComplete(Intent paramIntent)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 130
    //   3: ldc2_w 131
    //   6: invokevirtual 138	android/content/Intent:getLongExtra	(Ljava/lang/String;J)J
    //   9: invokestatic 144	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   12: astore 4
    //   14: aload_0
    //   15: aload 4
    //   17: invokespecial 146	org/whispersystems/libsupplychain/SupplyChain:getStoredEnqueuedApkDownload	(Ljava/lang/Long;)Lorg/whispersystems/libsupplychain/model/ApkRelease;
    //   20: astore 5
    //   22: aload 5
    //   24: ifnonnull +36 -> 60
    //   27: getstatic 31	org/whispersystems/libsupplychain/SupplyChain:TAG	Ljava/lang/String;
    //   30: new 87	java/lang/StringBuilder
    //   33: dup
    //   34: invokespecial 88	java/lang/StringBuilder:<init>	()V
    //   37: ldc 148
    //   39: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: aload 4
    //   44: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   47: ldc 150
    //   49: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: invokestatic 156	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   58: pop
    //   59: return
    //   60: aload_0
    //   61: getfield 37	org/whispersystems/libsupplychain/SupplyChain:context	Landroid/content/Context;
    //   64: ldc 158
    //   66: invokevirtual 164	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   69: checkcast 166	android/app/DownloadManager
    //   72: astore 7
    //   74: new 168	android/app/DownloadManager$Query
    //   77: dup
    //   78: invokespecial 169	android/app/DownloadManager$Query:<init>	()V
    //   81: astore 8
    //   83: iconst_1
    //   84: newarray long
    //   86: astore 9
    //   88: aload 9
    //   90: iconst_0
    //   91: aload 4
    //   93: invokevirtual 173	java/lang/Long:longValue	()J
    //   96: lastore
    //   97: aload 8
    //   99: aload 9
    //   101: invokevirtual 177	android/app/DownloadManager$Query:setFilterById	([J)Landroid/app/DownloadManager$Query;
    //   104: pop
    //   105: aload 7
    //   107: aload 8
    //   109: invokevirtual 181	android/app/DownloadManager:query	(Landroid/app/DownloadManager$Query;)Landroid/database/Cursor;
    //   112: astore 11
    //   114: aload 11
    //   116: invokeinterface 187 1 0
    //   121: ifne +32 -> 153
    //   124: getstatic 31	org/whispersystems/libsupplychain/SupplyChain:TAG	Ljava/lang/String;
    //   127: ldc 189
    //   129: invokestatic 192	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   132: pop
    //   133: aload 11
    //   135: invokeinterface 195 1 0
    //   140: return
    //   141: astore_2
    //   142: getstatic 31	org/whispersystems/libsupplychain/SupplyChain:TAG	Ljava/lang/String;
    //   145: ldc 197
    //   147: aload_2
    //   148: invokestatic 200	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   151: pop
    //   152: return
    //   153: aload 11
    //   155: aload 11
    //   157: ldc 202
    //   159: invokeinterface 206 2 0
    //   164: invokestatic 211	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   167: invokevirtual 215	java/lang/Integer:intValue	()I
    //   170: invokeinterface 219 2 0
    //   175: invokestatic 211	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   178: astore 13
    //   180: aload 13
    //   182: invokevirtual 215	java/lang/Integer:intValue	()I
    //   185: lookupswitch	default:+51->236, 1:+192->377, 2:+192->377, 4:+192->377, 8:+96->281, 16:+128->313
    //   237: nop
    //   238: lload_1
    //   239: new 87	java/lang/StringBuilder
    //   242: dup
    //   243: invokespecial 88	java/lang/StringBuilder:<init>	()V
    //   246: ldc 221
    //   248: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   251: aload 13
    //   253: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   256: ldc 223
    //   258: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: aload 4
    //   263: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   266: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   269: invokestatic 192	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   272: pop
    //   273: aload 11
    //   275: invokeinterface 195 1 0
    //   280: return
    //   281: aload_0
    //   282: aload 5
    //   284: aload 11
    //   286: aload 4
    //   288: invokespecial 227	org/whispersystems/libsupplychain/SupplyChain:handleDownloadSuccessful	(Lorg/whispersystems/libsupplychain/model/ApkRelease;Landroid/database/Cursor;Ljava/lang/Long;)V
    //   291: aload_0
    //   292: aload 4
    //   294: aconst_null
    //   295: invokespecial 231	org/whispersystems/libsupplychain/SupplyChain:storeEnqueuedApkDownload	(Ljava/lang/Long;Lorg/whispersystems/libsupplychain/model/ApkRelease;)V
    //   298: goto -25 -> 273
    //   301: astore 12
    //   303: aload 11
    //   305: invokeinterface 195 1 0
    //   310: aload 12
    //   312: athrow
    //   313: getstatic 31	org/whispersystems/libsupplychain/SupplyChain:TAG	Ljava/lang/String;
    //   316: new 87	java/lang/StringBuilder
    //   319: dup
    //   320: invokespecial 88	java/lang/StringBuilder:<init>	()V
    //   323: ldc 233
    //   325: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   328: aload 4
    //   330: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   333: ldc 235
    //   335: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   338: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   341: invokestatic 192	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   344: pop
    //   345: iconst_1
    //   346: newarray long
    //   348: astore 16
    //   350: aload 16
    //   352: iconst_0
    //   353: aload 4
    //   355: invokevirtual 173	java/lang/Long:longValue	()J
    //   358: lastore
    //   359: aload 7
    //   361: aload 16
    //   363: invokevirtual 239	android/app/DownloadManager:remove	([J)I
    //   366: pop
    //   367: aload_0
    //   368: aload 4
    //   370: aconst_null
    //   371: invokespecial 231	org/whispersystems/libsupplychain/SupplyChain:storeEnqueuedApkDownload	(Ljava/lang/Long;Lorg/whispersystems/libsupplychain/model/ApkRelease;)V
    //   374: goto -101 -> 273
    //   377: getstatic 31	org/whispersystems/libsupplychain/SupplyChain:TAG	Ljava/lang/String;
    //   380: new 87	java/lang/StringBuilder
    //   383: dup
    //   384: invokespecial 88	java/lang/StringBuilder:<init>	()V
    //   387: ldc 241
    //   389: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   392: aload 4
    //   394: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   397: ldc 243
    //   399: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   402: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   405: invokestatic 246	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   408: pop
    //   409: goto -136 -> 273
    //
    // Exception table:
    //   from	to	target	type
    //   0	22	141	java/io/IOException
    //   27	59	141	java/io/IOException
    //   60	114	141	java/io/IOException
    //   133	140	141	java/io/IOException
    //   273	280	141	java/io/IOException
    //   303	313	141	java/io/IOException
    //   114	133	301	finally
    //   153	236	301	finally
    //   236	273	301	finally
    //   281	298	301	finally
    //   313	374	301	finally
    //   377	409	301	finally
  }

  private void handleDownloadSuccessful(ApkRelease paramApkRelease, Cursor paramCursor, Long paramLong)
  {
    try
    {
      String str1 = paramCursor.getString(Integer.valueOf(paramCursor.getColumnIndex("local_uri")).intValue());
      if (!DigestUtil.calculateDigest("SHA-256", IOUtils.toByteArray(this.context.getContentResolver().openInputStream(Uri.parse(str1)))).equals(paramApkRelease.getDigestSha256()))
      {
        Log.e(TAG, "SHA-256 DIGEST OF DOWNLOADED APK DOES NOT MATCH WHAT WAS EXPECTED, hax hax hax, nsa nsa nsa");
        Log.e(TAG, "but really though, probably just something weird, won't install though");
        return;
      }
      storeDownloadedApkId(this.context, paramLong);
      String str2 = paramCursor.getString(Integer.valueOf(paramCursor.getColumnIndex("local_filename")).intValue());
      this.apkInstaller.notifyInstall(Uri.parse("file://" + str2));
      return;
    }
    catch (IOException localIOException)
    {
      Log.e(TAG, "unable to read downloaded apk from filesystem", localIOException);
      return;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      Log.e(TAG, "lol, wut?", localNoSuchAlgorithmException);
    }
  }

  private void handleScheduleApkDownload(Intent paramIntent)
  {
    Bundle localBundle = paramIntent.getBundleExtra("org.whispersystems.libsupplychain.KEY_APK_RELEASE_BUNDLE");
    if (localBundle == null)
    {
      Log.e(TAG, "unable to get ApkRelease bundle from intent");
      return;
    }
    ApkRelease localApkRelease = ApkRelease.build(localBundle);
    Uri localUri = Uri.parse(localApkRelease.getDownloadLink());
    DownloadManager localDownloadManager = (DownloadManager)this.context.getSystemService("download");
    try
    {
      storeEnqueuedApkDownload(Long.valueOf(localDownloadManager.enqueue(buildApkRequest(localUri))), localApkRelease);
      Log.d(TAG, "queued apk download using URI of " + localUri);
      return;
    }
    catch (JsonProcessingException localJsonProcessingException)
    {
      Log.e(TAG, "unable to serialize ApkRelease, fatal D:", localJsonProcessingException);
    }
  }

  private void handleScheduleApkReleasePollJob()
  {
    this.jobManager.add(new ApkReleasePollJob(this.context, this.connectionFactory));
    Log.d(TAG, "scheduled apk release poll job");
  }

  public static void removeDownloadedApk(Context paramContext)
  {
    Long localLong = Long.valueOf(PreferenceManager.getDefaultSharedPreferences(paramContext).getLong("SupplyChain.KEY_APK_DOWNLOAD_ID", -1L));
    if (localLong.longValue() >= 0L)
    {
      DownloadManager localDownloadManager = (DownloadManager)paramContext.getSystemService("download");
      long[] arrayOfLong = new long[1];
      arrayOfLong[0] = localLong.longValue();
      localDownloadManager.remove(arrayOfLong);
      Log.d(TAG, "removed download " + localLong);
    }
  }

  private static void storeDownloadedApkId(Context paramContext, Long paramLong)
  {
    PreferenceManager.getDefaultSharedPreferences(paramContext).edit().putLong("SupplyChain.KEY_APK_DOWNLOAD_ID", paramLong.longValue()).apply();
  }

  private void storeEnqueuedApkDownload(Long paramLong, ApkRelease paramApkRelease)
    throws JsonProcessingException
  {
    if (paramApkRelease == null)
    {
      this.preferences.edit().putString(buildKeyForEnqueuedDownload(paramLong), null).apply();
      return;
    }
    String str = MapperUtil.getMapper().writeValueAsString(paramApkRelease);
    this.preferences.edit().putString(buildKeyForEnqueuedDownload(paramLong), str).apply();
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction().equals("org.whispersystems.libsupplychain.INTENT_ALARM_24_HOURS"))
    {
      handleScheduleApkReleasePollJob();
      return;
    }
    if (paramIntent.getAction().equals("org.whispersystems.libsupplychain.INTENT_NEW_RELEASE_AVAILABLE"))
    {
      handleScheduleApkDownload(paramIntent);
      return;
    }
    if (paramIntent.getAction().equals("android.intent.action.DOWNLOAD_COMPLETE"))
    {
      handleDownloadComplete(paramIntent);
      return;
    }
    Log.e(TAG, "received broadcast intent with unknown action " + paramIntent.getAction());
  }

  public void start()
  {
    Thread.setDefaultUncaughtExceptionHandler(this.crashReporter);
    this.context.registerReceiver(this, new IntentFilter("org.whispersystems.libsupplychain.INTENT_ALARM_24_HOURS"));
    this.context.registerReceiver(this, new IntentFilter("org.whispersystems.libsupplychain.INTENT_NEW_RELEASE_AVAILABLE"));
    this.context.registerReceiver(this, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
    AlarmBooter.scheduleAlarmIfNotExists(this.context);
    this.crashReporter.start();
    Log.d(TAG, "started apk download scheduler");
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.SupplyChain
 * JD-Core Version:    0.6.2
 */