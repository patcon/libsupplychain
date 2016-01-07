package org.whispersystems.libsupplychain;

import android.net.Uri;
import android.net.Uri.Builder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class ConnectionFactory
{
  private final ClientInfo clientInfo;
  private final ServerInfo serverInfo;
  private final SSLSocketFactory socketFactory;

  public ConnectionFactory(ServerInfo paramServerInfo, ClientInfo paramClientInfo, SSLSocketFactory paramSSLSocketFactory)
  {
    this.serverInfo = paramServerInfo;
    this.clientInfo = paramClientInfo;
    this.socketFactory = paramSSLSocketFactory;
    if (!paramServerInfo.getHostnameVerification().booleanValue())
      HttpsURLConnection.setDefaultHostnameVerifier(new AwfulHostNameVerifier(null));
  }

  private Uri buildUriBase()
  {
    return Uri.parse("https://" + this.serverInfo.getHostname() + ":" + this.serverInfo.getPort());
  }

  public URL buildApkReleaseUrl()
    throws MalformedURLException
  {
    Uri.Builder localBuilder = buildUriBase().buildUpon();
    localBuilder.appendPath("packages").appendPath(this.clientInfo.getPackageName()).appendPath("apk").appendQueryParameter("installed_apk_version", this.clientInfo.getAppVersion().toString()).appendQueryParameter("android_version", this.clientInfo.getAndroidVersion()).appendQueryParameter("device_type", this.clientInfo.getDeviceType()).appendQueryParameter("language", this.clientInfo.getLanguage()).appendQueryParameter("country", this.clientInfo.getCountry());
    return new URL(localBuilder.build().toString());
  }

  public URL buildCrashPostUrl()
    throws MalformedURLException
  {
    Uri.Builder localBuilder = buildUriBase().buildUpon();
    localBuilder.appendPath("packages").appendPath(this.clientInfo.getPackageName()).appendPath("crash");
    return new URL(localBuilder.build().toString());
  }

  public HttpURLConnection getConnection(URL paramURL)
    throws IOException
  {
    HttpsURLConnection localHttpsURLConnection = (HttpsURLConnection)paramURL.openConnection();
    localHttpsURLConnection.setSSLSocketFactory(this.socketFactory);
    localHttpsURLConnection.addRequestProperty("X-SKIP-UUID", this.clientInfo.getUuid());
    return localHttpsURLConnection;
  }

  private class AwfulHostNameVerifier
    implements HostnameVerifier
  {
    private AwfulHostNameVerifier()
    {
    }

    public boolean verify(String paramString, SSLSession paramSSLSession)
    {
      return true;
    }
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.ConnectionFactory
 * JD-Core Version:    0.6.2
 */