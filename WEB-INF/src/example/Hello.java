package example;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

@SuppressWarnings("serial")
public class Hello extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		
		String msg = request.getParameter("msg");
		String img = request.getParameter("img");
		
		int port = request.getServerPort();
		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		
		String example = "{\"key1\": 123}";
		
		JSONObject obj = (JSONObject)JSONValue.parse(example);		
		
		obj.put("key2", 456);
		
		JSONArray arr = new JSONArray();
		String[] test_str = {"1", "2", "3"};
		arr.add(test_str);
		obj.put("key3", arr);
		obj.put("key4", test_str);
		
		out.println(obj.toString());
		
		//out.println("[MSG]"+msg);
		//out.println("[IMG]"+img);
	}

}
