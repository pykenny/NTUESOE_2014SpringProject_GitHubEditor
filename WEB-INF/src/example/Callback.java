package example;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;

@SuppressWarnings("serial")
public class Callback extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
       HttpSession session = request.getSession();
	   String client_id = "b3068f92fe20717c750b";
	   String client_secret = "84659212843c93dbcdc8fd75e9a6bb057fc3f81a";
	   CloseableHttpClient httpclient = HttpClients.createDefault();
	   

	   String code = request.getParameter("code");
	   String[] parse_arr;
	   
	   try {
		   HttpPost httpPost = new HttpPost("https://github.com/login/oauth/access_token");
	       List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	       nvps.add(new BasicNameValuePair("client_id", client_id));
	       nvps.add(new BasicNameValuePair("client_secret", client_secret));
	       nvps.add(new BasicNameValuePair("code", code));
	       nvps.add(new BasicNameValuePair("accept", "json"));
	       httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	       CloseableHttpResponse response2 = httpclient.execute(httpPost);
	       try {
	           HttpEntity entity2 = response2.getEntity();
	           InputStream im= entity2.getContent();	               
	           String output = IOUtils.toString(im, "utf-8");
	           parse_arr = output.split("&");
	           EntityUtils.consume(entity2);
	       } finally {
	           response2.close();
	       }
	       /* Parse access_token */
	       String access_token = (parse_arr[0].split("="))[1];
	       session.setAttribute("access_token", access_token);
	       session.setAttribute("root_dir", "elliot79313/cklab_project102-1/");
	       
	       /* Update user information and database */
	       GHMyself selfinfo = GitHub.connectUsingOAuth(access_token).getMyself();
	       int uid = selfinfo.getId();
	       String uname = selfinfo.getLogin();
	       String email = selfinfo.getEmail();
	       
	       SqlOperation ops = new SqlOperation();
	       ops.NewUser(uid, uname, email);
	       
	   } finally {
		   httpclient.close();
	       response.sendRedirect("http://192.168.11.101:8080/HelloWorld/path.html");
	   }
	   	   
	   return;
   }
}