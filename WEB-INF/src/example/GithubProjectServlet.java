package example;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.*;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


@SuppressWarnings("serial")
public class GithubProjectServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try{
			String url = "https://api.github.com/repos/elliot79313/cklab_project102-1/contents/";
			if(req.getParameter("d")!=null){
				url = req.getParameter("d").toString();
			}
			
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpGet);
			
			try{ 
				HttpEntity entity = response.getEntity();
		        InputStream im= entity.getContent();
		        String output = IOUtils.toString(im, "utf-8");
		        EntityUtils.consume(entity);
		        resp.getWriter().println(output);
			} finally{
				response.close();
			}
			
		} finally {
			httpclient.close();
		}	
	}
}
