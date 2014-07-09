package example;

import java.io.IOException;
import javax.servlet.http.*;

import net.minidev.json.JSONObject;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

@SuppressWarnings("serial")
public class LoadContent extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		
		HttpSession session = req.getSession();
		String path = req.getParameter("file");
		
		GitHub github = GitHub.connectUsingOAuth((String)session.getAttribute("access_token"));
		GHRepository repo = github.getRepository((String)session.getAttribute("root_dir"));
		GHContent cont_gh = repo.getFileContent(path);
		String content = cont_gh.getContent();
		JSONObject object = new JSONObject();
		object.put("filepath", path);
		object.put("content", content);
		resp.getWriter().print(object.toString());
	}
}
