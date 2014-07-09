package example;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import net.minidev.json.JSONObject;

@SuppressWarnings("serial")
public class Logout extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
	   HttpSession session = request.getSession();
	   JSONObject resp = new JSONObject();
	   session.invalidate();
	   resp.put("status", true);
	   response.getWriter().print(resp.toString());  
	   return;
	}
}
