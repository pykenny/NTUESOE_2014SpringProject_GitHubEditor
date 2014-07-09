package example;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Login extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
	   String client_id = "b3068f92fe20717c750b";
	   String next = 	
		 "https://github.com/login/oauth/authorize?scope=user,public_repo&client_id="+client_id;
	
	   response.sendRedirect(next);
	   return;
	}
}
