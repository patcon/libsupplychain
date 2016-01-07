package org.whispersystems.libsupplychain;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.whispersystems.jobqueue.Job;
import org.whispersystems.jobqueue.JobParameters;
import org.whispersystems.jobqueue.JobParameters.Builder;
import org.whispersystems.jobqueue.requirements.NetworkRequirement;
import org.whispersystems.libsupplychain.model.ApkRelease;
import org.whispersystems.libsupplychain.util.MapperUtil;

public class ApkReleasePollJob extends Job
{
  protected static final String INTENT_NEW_RELEASE_AVAILABLE = "org.whispersystems.libsupplychain.INTENT_NEW_RELEASE_AVAILABLE";
  protected static final String KEY_APK_RELEASE_BUNDLE = "org.whispersystems.libsupplychain.KEY_APK_RELEASE_BUNDLE";
  private static final String TAG = ApkReleasePollJob.class.getName();
  private final ConnectionFactory connectionFactory;
  private final Context context;

  protected ApkReleasePollJob(Context paramContext, ConnectionFactory paramConnectionFactory)
  {
    super(JobParameters.newBuilder().withRequirement(new NetworkRequirement(paramContext)).create());
    this.context = paramContext;
    this.connectionFactory = paramConnectionFactory;
  }

  private void handleReleaseAvailable(ApkRelease paramApkRelease)
  {
    SupplyChain.removeDownloadedApk(this.context);
    Intent localIntent = new Intent();
    localIntent.setAction("org.whispersystems.libsupplychain.INTENT_NEW_RELEASE_AVAILABLE");
    localIntent.putExtra("org.whispersystems.libsupplychain.KEY_APK_RELEASE_BUNDLE", paramApkRelease.toBundle());
    this.context.sendBroadcast(localIntent);
  }

  public void onAdded()
  {
  }

  public void onCanceled()
  {
  }

  public void onRun()
    throws Exception
  {
    URL localURL = this.connectionFactory.buildApkReleaseUrl();
    HttpURLConnection localHttpURLConnection = this.connectionFactory.getConnection(localURL);
    Integer localInteger = Integer.valueOf(localHttpURLConnection.getResponseCode());
    InputStream localInputStream = localHttpURLConnection.getInputStream();
    switch (localInteger.intValue())
    {
    default:
      Log.w(TAG, "server returned unexpected status " + localInteger);
      return;
    case 200:
      Log.d(TAG, "server returned 200, update available :D");
      handleReleaseAvailable((ApkRelease)MapperUtil.getMapper().readValue(localInputStream, ApkRelease.class));
      return;
    case 304:
    }
    Log.d(TAG, "server returned 304, no update available");
  }

  public boolean onShouldRetry(Exception paramException)
  {
    return false;
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.ApkReleasePollJob
 * JD-Core Version:    0.6.2
 */