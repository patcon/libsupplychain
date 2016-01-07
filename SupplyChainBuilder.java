package org.whispersystems.libsupplychain;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLSocketFactory;
import org.whispersystems.jobqueue.JobManager;
import org.whispersystems.jobqueue.JobManager.Builder;
import org.whispersystems.jobqueue.requirements.NetworkRequirementProvider;
import org.whispersystems.jobqueue.requirements.RequirementProvider;
import org.whispersystems.libsupplychain.util.SSLSocketFactoryUtil;

public class SupplyChainBuilder
{
  private final ClientInfo clientInfo;
  private final Context context;
  private final ServerInfo serverInfo;

  private SupplyChainBuilder(Context paramContext, ServerInfo paramServerInfo, ClientInfo paramClientInfo)
  {
    this.context = paramContext;
    this.serverInfo = paramServerInfo;
    this.clientInfo = paramClientInfo;
  }

  public static SupplyChainBuilder newBuilder(Context paramContext, String paramString1, String paramString2)
    throws PackageManager.NameNotFoundException, KeyStoreException
  {
    return new SupplyChainBuilder(paramContext, ServerInfo.build(paramString2), ClientInfo.build(paramContext, paramString1));
  }

  public SupplyChain create()
    throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    JobManager.Builder localBuilder = JobManager.newBuilder(this.context).withName("SupplyChainJobManager").withConsumerThreads(2);
    RequirementProvider[] arrayOfRequirementProvider = new RequirementProvider[1];
    arrayOfRequirementProvider[0] = new NetworkRequirementProvider(this.context);
    JobManager localJobManager = localBuilder.withRequirementProviders(arrayOfRequirementProvider).build();
    SSLSocketFactory localSSLSocketFactory = SSLSocketFactoryUtil.buildWithTrust(this.serverInfo.getTrustStore());
    ConnectionFactory localConnectionFactory = new ConnectionFactory(this.serverInfo, this.clientInfo, localSSLSocketFactory);
    return new SupplyChain(this.context, localJobManager, this.clientInfo, localConnectionFactory);
  }

  public SupplyChainBuilder withAndroidVersion(String paramString)
  {
    this.clientInfo.setAndroidVersion(paramString);
    return this;
  }

  public SupplyChainBuilder withAppRestartDelay(Long paramLong)
  {
    this.clientInfo.setAppRestartDelay(paramLong);
    return this;
  }

  public SupplyChainBuilder withCountry(String paramString)
  {
    this.clientInfo.setCountry(paramString);
    return this;
  }

  public SupplyChainBuilder withDeviceType(String paramString)
  {
    this.clientInfo.setDeviceType(paramString);
    return this;
  }

  public SupplyChainBuilder withHostnameVerification(Boolean paramBoolean)
  {
    this.serverInfo.setHostnameVerification(paramBoolean);
    return this;
  }

  public SupplyChainBuilder withLanguage(String paramString)
  {
    this.clientInfo.setLanguage(paramString);
    return this;
  }

  public SupplyChainBuilder withPort(Integer paramInteger)
  {
    this.serverInfo.setPort(paramInteger);
    return this;
  }

  public SupplyChainBuilder withTrustStore(KeyStore paramKeyStore)
  {
    this.serverInfo.setTrustStore(paramKeyStore);
    return this;
  }

  public SupplyChainBuilder withUpdateNotificationColor(int paramInt)
  {
    this.clientInfo.setUpdateNotificationColor(Integer.valueOf(paramInt));
    return this;
  }

  public SupplyChainBuilder withUpdateNotificationDrawable(int paramInt)
  {
    this.clientInfo.setUpdateNotificationDrawable(Integer.valueOf(paramInt));
    return this;
  }

  public SupplyChainBuilder withUpdateNotificationText(String paramString)
  {
    this.clientInfo.setUpdateNotificationText(paramString);
    return this;
  }

  public SupplyChainBuilder withUpdateNotificationTitle(String paramString)
  {
    this.clientInfo.setUpdateNotificationTitle(paramString);
    return this;
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.SupplyChainBuilder
 * JD-Core Version:    0.6.2
 */