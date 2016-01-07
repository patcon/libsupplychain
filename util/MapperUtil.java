package org.whispersystems.libsupplychain.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtil
{
  private static final ObjectMapper mapper = new ObjectMapper();

  public static ObjectMapper getMapper()
  {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }
}

/* Location:           /tmp/org.anhonesteffort.flock-4b93f078-cae6-4997-a1c2-ef833b2ac2c1-dex2jar.jar
 * Qualified Name:     org.whispersystems.libsupplychain.util.MapperUtil
 * JD-Core Version:    0.6.2
 */