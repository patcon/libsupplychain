package org.whispersystems.libsupplychain.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import java.util.Locale;

public class ClientInfoUtil
{
  public static String getAndroidVersion()
  {
    return Build.VERSION.RELEASE;
  }

  public static Integer getAppVersion(Context paramContext, String paramString)
    throws PackageManager.NameNotFoundException
  {
    return Integer.valueOf(paramContext.getPackageManager().getPackageInfo(paramString, 0).versionCode);
  }

  public static String getCountry(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().locale.getCountry();
  }

  public static String getDeviceType()
  {
    return Build.DEVICE + "|" + Build.MODEL;
  }

  public static String getLanguage(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().locale.getLanguage();
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.util.ClientInfoUtil
 * JD-Core Version:    0.6.2
 */