package org.whispersystems.libsupplychain.util;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class SSLSocketFactoryUtil
{
  public static SSLSocketFactory buildWithTrust(KeyStore paramKeyStore)
    throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    TrustManagerFactory localTrustManagerFactory = TrustManagerFactory.getInstance("X509");
    localTrustManagerFactory.init(paramKeyStore);
    SSLContext localSSLContext = SSLContext.getInstance("TLS");
    localSSLContext.init(null, localTrustManagerFactory.getTrustManagers(), null);
    return localSSLContext.getSocketFactory();
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.util.SSLSocketFactoryUtil
 * JD-Core Version:    0.6.2
 */