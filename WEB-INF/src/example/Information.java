package example;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;

import net.minidev.json.JSONObject;

@SuppressWarnings("serial")
public class Information extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
	   response.setContentType("text/plain");
	   HttpSession session = request.getSession();
	   String access_token = (String)session.getAttribute("access_token");
	   
	   JSONObject u_info = new JSONObject();
	   
	   if(access_token == null){
		   u_info.put("access", false);
	   } else{
		   u_info.put("access", true);
		   GitHub github = GitHub.connectUsingOAuth(access_token);
		   GHMyself myself = github.getMyself();
		   String user = myself.getLogin();
		   String email = myself.getEmail();
		   u_info.put("user", user);
		   u_info.put("email", email);
		   u_info.put("id", myself.getId());
		   u_info.put("name", myself.getName());
	   }
	   response.getWriter().print(u_info.toString());
	}
}