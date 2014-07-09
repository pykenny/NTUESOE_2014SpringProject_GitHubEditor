package example;
import java.sql.*;
import java.util.Calendar;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;


public class SqlOperation {
	private String myDriver = "com.mysql.jdbc.Driver";
	private String myUrl = "jdbc:mysql://localhost:3306/user?user=root&password=admin";
	private Connection conn;
	
	public SqlOperation(){
		
		try{
			Class.forName(myDriver);		
			conn = DriverManager.getConnection(myUrl);
		}	
	    catch (Exception e)
	    {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    }
	}
	
	public void InsertUser(int gitid, String name, String email){
		String query = " INSERT INTO users (user_gitid, user_gitlogin, user_email)"
				+ " VALUES (?, ?, ?)";
		try{
			PreparedStatement pstate = conn.prepareStatement(query);
			pstate.setInt(1, gitid);
			pstate.setString(2, name);
			pstate.setString(3, email);
			
			pstate.execute();
			conn.close();
		}
	    catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
	}
	
	public void InsertCommit(int uid, String fname, String ccode, String link){
		String query = " INSERT INTO log (uid, filename, commitcode, created_time, filelink)"
				+ " VALUES (?, ?, ?, ?, ?)";
		Calendar calendar = Calendar.getInstance();
		//java.sql.Date t = new java.sql.Date(calendar.getTime().getTime());
		try{
			PreparedStatement pstate = conn.prepareStatement(query);
			pstate.setInt(1, uid);
			pstate.setString(2, fname);
			pstate.setString(3, ccode);
			pstate.setLong(4, calendar.getTimeInMillis());
			pstate.setString(5, link);
			
			pstate.execute();
			conn.close();
		}
	    catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
	}
	
	public JSONArray SelectCommit(int query_uid){
		JSONArray arr = new JSONArray();
	
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM log WHERE uid = " + query_uid);
			Date date;
			long miltime;
			java.text.SimpleDateFormat ft = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String timestr;
			
			while(rs.next()){
				JSONObject content = new JSONObject();
				content.put("filename", rs.getString("filename"));
				content.put("commitcode", rs.getString("commitcode"));
				
				miltime = rs.getLong("created_time");
				date = new Date(miltime);
				timestr = ft.format(date);		
				content.put("created_time", timestr);
				
				content.put("filelink", rs.getString("filelink"));
				arr.add(content);
			}
			conn.close();
		} catch (Exception e) {
        System.err.println("Got an exception! ");
        System.err.println(e.getMessage());
		}
		
		return arr;
	}

	public void NewUser(int uid, String login, String email){
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE user_gitid = " + uid);
			// new user
			if(rs.next() == false){
				String query = "INSERT INTO users (user_gitid, user_gitlogin, user_email)"
						+ " values (?, ?, ?)";
				PreparedStatement pstate = conn.prepareStatement(query);
				pstate.setInt(1, uid);
				pstate.setString(2, login);
				pstate.setString(3, email);
				
				pstate.execute();
				conn.close();
			}
		} catch (Exception e) {
			System.err.println("Got an exception! : ");
			System.err.println(e.getMessage());
		}
	}
}
