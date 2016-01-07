package org.whispersystems.libsupplychain;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.Builder;

public class ApkInstaller
{
  private final ClientInfo clientInfo;
  private final Context context;

  public ApkInstaller(Context paramContext, ClientInfo paramClientInfo)
  {
    this.context = paramContext;
    this.clientInfo = paramClientInfo;
  }

  public void notifyInstall(Uri paramUri)
  {
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(this.context);
    localBuilder.setSmallIcon(this.clientInfo.getUpdateNotificationDrawable().intValue());
    if (this.clientInfo.getUpdateNotificationColor().intValue() != 0)
      localBuilder.setColor(this.clientInfo.getUpdateNotificationColor().intValue());
    localBuilder.setContentTitle(this.clientInfo.getUpdateNotificationTitle());
    localBuilder.setContentText(this.clientInfo.getUpdateNotificationText());
    localBuilder.setAutoCancel(true);
    Intent localIntent = new Intent("android.intent.action.VIEW").setDataAndType(paramUri, "application/vnd.android.package-archive");
    localBuilder.setContentIntent(PendingIntent.getActivity(this.context, 0, localIntent, 0));
    ((NotificationManager)this.context.getSystemService("notification")).notify(1337, localBuilder.build());
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.ApkInstaller
 * JD-Core Version:    0.6.2
 */