package org.whispersystems.libsupplychain.model;

import android.os.Bundle;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApkRelease
{
  private static final String KEY_DIGEST_SHA256 = "ApkRelease.KEY_DIGEST_SHA256";
  private static final String KEY_DOWNLOAD_LINK = "ApkRelease.KEY_DOWNLOAD_LINK";
  private static final String KEY_TIMESTAMP = "ApkRelease.KEY_TIMESTAMP";
  private static final String KEY_VERSION = "ApkRelease.KEY_VERSION";

  @JsonProperty
  private String digestSha256;

  @JsonProperty
  private String downloadLink;

  @JsonProperty
  private Long timestamp;

  @JsonProperty
  private Integer version;

  public ApkRelease()
  {
  }

  public ApkRelease(Long paramLong, Integer paramInteger, String paramString1, String paramString2)
  {
    this.timestamp = paramLong;
    this.version = paramInteger;
    this.digestSha256 = paramString1;
    this.downloadLink = paramString2;
  }

  public static ApkRelease build(Bundle paramBundle)
  {
    return new ApkRelease(Long.valueOf(paramBundle.getLong("ApkRelease.KEY_TIMESTAMP")), Integer.valueOf(paramBundle.getInt("ApkRelease.KEY_VERSION")), paramBundle.getString("ApkRelease.KEY_DIGEST_SHA256"), paramBundle.getString("ApkRelease.KEY_DOWNLOAD_LINK"));
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == null);
    ApkRelease localApkRelease;
    do
    {
      do
        return false;
      while (!(paramObject instanceof ApkRelease));
      localApkRelease = (ApkRelease)paramObject;
    }
    while ((!this.timestamp.equals(localApkRelease.timestamp)) || (!this.version.equals(localApkRelease.version)) || (!this.digestSha256.equals(localApkRelease.digestSha256)) || (!this.downloadLink.equals(localApkRelease.downloadLink)));
    return true;
  }

  public String getDigestSha256()
  {
    return this.digestSha256;
  }

  public String getDownloadLink()
  {
    return this.downloadLink;
  }

  public Long getTimestamp()
  {
    return this.timestamp;
  }

  public Integer getVersion()
  {
    return this.version;
  }

  public int hashCode()
  {
    return this.timestamp.hashCode() ^ this.version.hashCode() ^ this.digestSha256.hashCode() ^ this.downloadLink.hashCode();
  }

  public Bundle toBundle()
  {
    Bundle localBundle = new Bundle();
    localBundle.putLong("ApkRelease.KEY_TIMESTAMP", this.timestamp.longValue());
    localBundle.putInt("ApkRelease.KEY_VERSION", this.version.intValue());
    localBundle.putString("ApkRelease.KEY_DIGEST_SHA256", this.digestSha256);
    localBundle.putString("ApkRelease.KEY_DOWNLOAD_LINK", this.downloadLink);
    return localBundle;
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.model.ApkRelease
 * JD-Core Version:    0.6.2
 */