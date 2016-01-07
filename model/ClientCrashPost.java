package org.whispersystems.libsupplychain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.whispersystems.libsupplychain.ClientInfo;

public class ClientCrashPost
{

  @JsonProperty
  private String androidVersion;

  @JsonProperty
  private String country;

  @JsonProperty
  private Long crashTime;

  @JsonProperty
  private String crashType;

  @JsonProperty
  private String deviceType;

  @JsonProperty
  private Integer installedApkVersion;

  @JsonProperty
  private String language;

  @JsonProperty
  private String report;

  public ClientCrashPost()
  {
  }

  public ClientCrashPost(Integer paramInteger, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Long paramLong, String paramString6)
  {
    this.installedApkVersion = paramInteger;
    this.androidVersion = paramString1;
    this.deviceType = paramString2;
    this.language = paramString3;
    this.country = paramString4;
    this.crashType = paramString5;
    this.crashTime = paramLong;
    this.report = paramString6;
  }

  public ClientCrashPost(ClientInfo paramClientInfo, String paramString1, Long paramLong, String paramString2)
  {
    this(paramClientInfo.getAppVersion(), paramClientInfo.getAndroidVersion(), paramClientInfo.getDeviceType(), paramClientInfo.getLanguage(), paramClientInfo.getCountry(), paramString1, paramLong, paramString2);
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == null);
    ClientCrashPost localClientCrashPost;
    do
    {
      do
        return false;
      while (!(paramObject instanceof ClientCrashPost));
      localClientCrashPost = (ClientCrashPost)paramObject;
    }
    while ((!this.installedApkVersion.equals(localClientCrashPost.installedApkVersion)) || (!this.androidVersion.equals(localClientCrashPost.androidVersion)) || (!this.deviceType.equals(localClientCrashPost.deviceType)) || (!this.language.equals(localClientCrashPost.language)) || (!this.country.equals(localClientCrashPost.country)) || (!this.crashType.equals(localClientCrashPost.crashType)) || (!this.crashTime.equals(localClientCrashPost.crashTime)) || (!this.report.equals(localClientCrashPost.report)));
    return true;
  }

  public String getAndroidVersion()
  {
    return this.androidVersion;
  }

  public String getCountry()
  {
    return this.country;
  }

  public Long getCrashTime()
  {
    return this.crashTime;
  }

  public String getCrashType()
  {
    return this.crashType;
  }

  public String getDeviceType()
  {
    return this.deviceType;
  }

  public Integer getInstalledApkVersion()
  {
    return this.installedApkVersion;
  }

  public String getLanguage()
  {
    return this.language;
  }

  public String getReport()
  {
    return this.report;
  }

  public int hashCode()
  {
    return this.installedApkVersion.hashCode() ^ this.androidVersion.hashCode() ^ this.deviceType.hashCode() ^ this.language.hashCode() ^ this.country.hashCode() ^ this.crashType.hashCode() ^ this.crashTime.hashCode() ^ this.report.hashCode();
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.model.ClientCrashPost
 * JD-Core Version:    0.6.2
 */