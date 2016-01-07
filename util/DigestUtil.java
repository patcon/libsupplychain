package org.whispersystems.libsupplychain.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class DigestUtil
{
  public static String calculateDigest(String paramString, byte[] paramArrayOfByte)
    throws IOException, NoSuchAlgorithmException
  {
    MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
    Formatter localFormatter = new Formatter();
    for (byte b : localMessageDigest.digest(paramArrayOfByte))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Byte.valueOf(b);
      localFormatter.format("%02x", arrayOfObject);
    }
    return localFormatter.toString();
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.util.DigestUtil
 * JD-Core Version:    0.6.2
 */