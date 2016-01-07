package org.whispersystems.libsupplychain;

import android.content.Context;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;
import java.net.URL;
import org.whispersystems.jobqueue.Job;
import org.whispersystems.jobqueue.JobParameters;
import org.whispersystems.jobqueue.JobParameters.Builder;
import org.whispersystems.jobqueue.requirements.NetworkRequirement;
import org.whispersystems.libsupplychain.model.ClientCrashPost;
import org.whispersystems.libsupplychain.util.MapperUtil;

public class CrashReportingJob extends Job
{
  private static final String TAG = CrashReportingJob.class.getName();
  private final ConnectionFactory connectionFactory;
  private final ClientCrashPost crashReport;

  public CrashReportingJob(Context paramContext, ConnectionFactory paramConnectionFactory, ClientCrashPost paramClientCrashPost)
  {
    super(JobParameters.newBuilder().withRequirement(new NetworkRequirement(paramContext)).create());
    this.connectionFactory = paramConnectionFactory;
    this.crashReport = paramClientCrashPost;
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
    URL localURL = this.connectionFactory.buildCrashPostUrl();
    HttpURLConnection localHttpURLConnection = this.connectionFactory.getConnection(localURL);
    localHttpURLConnection.setRequestProperty("Content-Type", "application/json");
    localHttpURLConnection.setRequestMethod("POST");
    localHttpURLConnection.setDoOutput(true);
    localHttpURLConnection.connect();
    MapperUtil.getMapper().writeValue(localHttpURLConnection.getOutputStream(), this.crashReport);
    Integer localInteger = Integer.valueOf(localHttpURLConnection.getResponseCode());
    switch (localInteger.intValue())
    {
    default:
      Log.w(TAG, "server returned unexpected status " + localInteger);
      return;
    case 204:
    }
    Log.d(TAG, "server returned 204, crash reported :D");
  }

  public boolean onShouldRetry(Exception paramException)
  {
    return false;
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.CrashReportingJob
 * JD-Core Version:    0.6.2
 */