package org.whispersystems.libsupplychain;

import java.security.KeyStore;
import java.security.KeyStoreException;

public class ServerInfo
{
  private String hostname;
  private Boolean hostnameVerification;
  private Integer port;
  private KeyStore trustStore;

  public static ServerInfo build(String paramString)
    throws KeyStoreException
  {
    ServerInfo localServerInfo = new ServerInfo();
    localServerInfo.trustStore = KeyStore.getInstance("AndroidCAStore");
    localServerInfo.hostname = paramString;
    localServerInfo.port = Integer.valueOf(443);
    localServerInfo.hostnameVerification = Boolean.valueOf(true);
    return localServerInfo;
  }

  public String getHostname()
  {
    return this.hostname;
  }

  public Boolean getHostnameVerification()
  {
    return this.hostnameVerification;
  }

  public Integer getPort()
  {
    return this.port;
  }

  public KeyStore getTrustStore()
  {
    return this.trustStore;
  }

  public void setHostnameVerification(Boolean paramBoolean)
  {
    this.hostnameVerification = paramBoolean;
  }

  protected void setPort(Integer paramInteger)
  {
    this.port = paramInteger;
  }

  protected void setTrustStore(KeyStore paramKeyStore)
  {
    this.trustStore = paramKeyStore;
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.ServerInfo
 * JD-Core Version:    0.6.2
 */