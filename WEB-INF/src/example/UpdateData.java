package example;

import java.io.IOException;

import javax.servlet.http.*;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHContentUpdateResponse;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

@SuppressWarnings("serial")
public class UpdateData extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		
		HttpSession session = req.getSession();
		String path = req.getParameter("req_path");
		String content = req.getParameter("content");
		String comment = req.getParameter("comment");
		
		try{
			// update data
			GitHub github = GitHub.connectUsingOAuth((String)session.getAttribute("access_token"));
			GHRepository repo = github.getRepository((String)session.getAttribute("root_dir"));
			GHContent cont_gh = repo.getFileContent(path);
			GHContentUpdateResponse ghresp = cont_gh.update(content, comment);
			
			// get commit information
			GHCommit ghcmt = ghresp.getCommit();
			String ccode = ghcmt.getSHA1();
			
			GHMyself updator = github.getMyself();

			int uid = updator.getId();
			
			// update commit database
			SqlOperation ops = new SqlOperation();
			ops.InsertCommit(uid, cont_gh.getName(), ccode, cont_gh.getHtmlUrl());
			
			// after update, return success message
			resp.getWriter().print("{\"status\":\"success!\"}");
		} catch(IOException e){
			// handle error here????
			
		}
	}
}