package org.whispersystems.libsupplychain;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import java.util.UUID;
import org.whispersystems.libsupplychain.util.ClientInfoUtil;

public class ClientInfo
{
  private static final String KEY_CLIENT_UUID = "KEY_CLIENT_UUID";
  private String androidVersion;
  private Long appRestartDelay;
  private Integer appVersion;
  private String clientUuid;
  private String country;
  private String deviceType;
  private String language;
  private String packageName;
  private Integer updateNotificationColor;
  private Integer updateNotificationDrawable;
  private String updateNotificationText;
  private String updateNotificationTitle;

  public static ClientInfo build(Context paramContext, String paramString)
    throws PackageManager.NameNotFoundException
  {
    ClientInfo localClientInfo = new ClientInfo();
    localClientInfo.packageName = paramString;
    localClientInfo.clientUuid = getUuid(paramContext);
    localClientInfo.appVersion = ClientInfoUtil.getAppVersion(paramContext, paramString);
    localClientInfo.androidVersion = ClientInfoUtil.getAndroidVersion();
    localClientInfo.deviceType = ClientInfoUtil.getDeviceType();
    localClientInfo.language = ClientInfoUtil.getLanguage(paramContext);
    localClientInfo.country = ClientInfoUtil.getCountry(paramContext);
    localClientInfo.updateNotificationDrawable = Integer.valueOf(R.drawable.supplychain__ic_launcher);
    localClientInfo.updateNotificationColor = Integer.valueOf(0);
    localClientInfo.updateNotificationTitle = paramContext.getString(R.string.libsupplychain__update_notification_title);
    localClientInfo.updateNotificationText = paramContext.getString(R.string.libsupplychain__update_notification_text);
    localClientInfo.appRestartDelay = Long.valueOf(-1L);
    return localClientInfo;
  }

  protected static String getUuid(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    String str = localSharedPreferences.getString("KEY_CLIENT_UUID", null);
    if (str == null)
    {
      str = UUID.randomUUID().toString();
      localSharedPreferences.edit().putString("KEY_CLIENT_UUID", str).apply();
    }
    return str;
  }

  public String getAndroidVersion()
  {
    return this.androidVersion;
  }

  public Long getAppRestartDelay()
  {
    return this.appRestartDelay;
  }

  public Integer getAppVersion()
  {
    return this.appVersion;
  }

  public String getCountry()
  {
    return this.country;
  }

  public String getDeviceType()
  {
    return this.deviceType;
  }

  public String getLanguage()
  {
    return this.language;
  }

  public String getPackageName()
  {
    return this.packageName;
  }

  public Integer getUpdateNotificationColor()
  {
    return this.updateNotificationColor;
  }

  public Integer getUpdateNotificationDrawable()
  {
    return this.updateNotificationDrawable;
  }

  public String getUpdateNotificationText()
  {
    return this.updateNotificationText;
  }

  public String getUpdateNotificationTitle()
  {
    return this.updateNotificationTitle;
  }

  public String getUuid()
  {
    return this.clientUuid;
  }

  protected void setAndroidVersion(String paramString)
  {
    this.androidVersion = paramString;
  }

  public void setAppRestartDelay(Long paramLong)
  {
    this.appRestartDelay = paramLong;
  }

  protected void setCountry(String paramString)
  {
    this.country = paramString;
  }

  protected void setDeviceType(String paramString)
  {
    this.deviceType = paramString;
  }

  protected void setLanguage(String paramString)
  {
    this.language = paramString;
  }

  public void setUpdateNotificationColor(Integer paramInteger)
  {
    this.updateNotificationColor = paramInteger;
  }

  public void setUpdateNotificationDrawable(Integer paramInteger)
  {
    this.updateNotificationDrawable = paramInteger;
  }

  public void setUpdateNotificationText(String paramString)
  {
    this.updateNotificationText = paramString;
  }

  public void setUpdateNotificationTitle(String paramString)
  {
    this.updateNotificationTitle = paramString;
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.ClientInfo
 * JD-Core Version:    0.6.2
 */