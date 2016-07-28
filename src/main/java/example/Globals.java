package example;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Globals
{
    public static final long        MS_IN_HOUR        = 1000*60*60;
    public static final long        MS_IN_DAY         = MS_IN_HOUR*24;
    public static final long        MS_IN_WEEK        = MS_IN_DAY*7;
    public static final int         SEC_IN_HOUR       = 60*60;

    public static ObjectMapper      mapper            = new ObjectMapper();
    static
    {
        mapper.setSerializationInclusion(Include.NON_NULL);
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
