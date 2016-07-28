package example;
 
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
 
public class UrlReaderServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String url = (String)Utils.getRequestParamForGet(request, "url");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        if (url != null) {
            try {
                response.getWriter().println(readUrl(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    String readUrl(String urlString) throws Exception
    {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = IOUtils.toString(in, encoding);
        //System.out.println(body);
        return body;
    }
}
