package example;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

public class Utils
{
    static Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * Parameter {@code ParamType} identifies the type of request-pojo
     */
    public static <ParamType> ParamType getRequestParamForPost(HttpServletRequest request, String paramName, Class<ParamType> clazz)
    {
        String [] paramValueArray = request.getParameterMap().get(paramName);
        Preconditions.checkArgument(paramValueArray!=null,
                "Could not find param=\"" + paramName + "\" in the request: " + request.getParameterMap());
        if (clazz == String.class)
            return (ParamType) paramValueArray[0];
        try
        {
            return Globals.mapper.readValue (paramValueArray[0], clazz);
        }
        catch (Exception e)
        {
            throw new RuntimeException ("Unable to parse param=\"" + paramValueArray[0] + "\"", e);
        }
    }

    /**
     * Helps read List<ParamType> type variables
     */
    public static <ParamType> List<ParamType> getRequestParamForPost(
            HttpServletRequest request, String paramName,
            TypeReference<List<ParamType>> typeReference)
    {
        String [] paramValueArray = request.getParameterMap().get(paramName);
        Preconditions.checkArgument(paramValueArray!=null, "Could not find param=\"" + paramName + "\" in the request");
        try
        {
            return Globals.mapper.readValue (paramValueArray[0], typeReference);
        }
        catch (Exception e)
        {
            throw new RuntimeException ("Unable to parse param=\"" + paramValueArray[0] + "\"", e);
        }
    }

    /**
     */
    public static Object getRequestParamForGet(HttpServletRequest request, String paramName)
    {
        String [] paramValueArray = request.getParameterMap().get(paramName);
        if (paramValueArray != null)
            return paramValueArray[0];
        return null;
    }

    public static Map<String, Object> createMap(Object []arr)
    {
        Preconditions.checkNotNull(arr, "Input array cannot be null");
        Preconditions.checkArgument(arr.length%2 == 0, "Input array must have an even number of elements");

        Map<String, Object> m = Maps.newHashMap();
        for (int i=0; i<arr.length; i=i+2)
        {
            Object key = arr[i];
            Object value = arr[i+1];
            Preconditions.checkArgument(key.getClass() == String.class,
                    "Element at position {} is not a string", i);
            m.put((String)key, value);
        }
        return m;
    }

    public static <T> void sendJsonResponse (HttpServletResponse response, T obj)
    {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        try
        {
            response.getWriter().println(Globals.mapper.writeValueAsString(obj));
        }
        catch (Exception e)
        {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public static void sendError(HttpServletResponse response, int responseCode, String msg)
    {
        logger.error (msg);
        response.setContentType("application/json");
        response.setStatus(responseCode);
        try
        {
            response.getWriter().println("{error:\"" + msg+ "\"}");
        }
        catch (Exception e)
        {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public static void sendError(HttpServletResponse response, Exception e)
    {
        logger.error(e.getLocalizedMessage(), e);
        sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
    }

    public static String hashPassword(String password)
    {
        HashCode hc = Hashing.md5().newHasher()
                .putString(password, Charsets.UTF_8)
                .hash();
        // MD5 produces a 32-digit hexadecimal number (https://en.wikipedia.org/wiki/MD5)
        // which becomes a 16-char string since 1 char = 8 bits = 2 hexadecimal numbers
        // To be safe, we allow 32-char strings because base-64 also increases the size by 33% approx
        String md5 = BaseEncoding.base64().encode(hc.asBytes());
        if (md5.length() > 31)
        {
            md5 = md5.substring(0, 31);
        }
        return md5;
    }

    public static int compareDates(Date d1, Date d2)
    {
        if (d1 == null || d2 == null)
            return 0;
        long diff = d1.getTime() - d2.getTime();
        return (diff < 0)? -1: ((diff == 0)? 0: 1);
    }
}
