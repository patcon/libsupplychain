package org.whispersystems.libsupplychain.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogcatUtil
{
  public static String grabLogcat()
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -d").getInputStream()));
    StringBuilder localStringBuilder = new StringBuilder();
    String str1 = System.getProperty("line.separator");
    while (true)
    {
      String str2 = localBufferedReader.readLine();
      if (str2 == null)
        break;
      localStringBuilder.append(str2);
      localStringBuilder.append(str1);
    }
    return localStringBuilder.toString();
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.util.LogcatUtil
 * JD-Core Version:    0.6.2
 */