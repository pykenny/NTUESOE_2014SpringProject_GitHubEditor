<%@page import="example.SqlOperation"%>
<%@page import="org.kohsuke.github.GHMyself"%>
<%@page import="org.kohsuke.github.GitHub"%>
<%@page import="net.minidev.json.JSONObject"%>
<%@page import="net.minidev.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

HttpSession session2 = request.getSession();
String access_token = (String)session2.getAttribute("access_token");


JSONObject u_info = new JSONObject();
JSONArray commitrecord = new JSONArray();

if(access_token == null){
	   u_info.put("access", false);
} else{
	   u_info.put("access", true);
	   GitHub github = GitHub.connectUsingOAuth(access_token);
	   GHMyself myself = github.getMyself();
	   u_info.put("user", myself.getLogin());
	   u_info.put("email", myself.getEmail());
	   u_info.put("id", myself.getId());
	   u_info.put("name", myself.getName());
	   
	   SqlOperation sqlo = new SqlOperation();
	   // get user edit history here...
	   commitrecord = sqlo.SelectCommit(myself.getId());
}
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Github Edit System</title>

    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <link href="./css/dashboard.css" rel="stylesheet">
    
    <script type="text/javascript">
    	var user = <%= u_info.toString() %>;
    	var record = <%= commitrecord.toString() %>;
    </script>
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">GitHub Edit System</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
			<li><a href="http://localhost:8080/HelloWorld/path.html">Back to DIR</a></li>
            <li><a href="#">Help</a></li>
            <li id="log_out"><a href="#">Logout</a></li>
          </ul>
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row">

        <div class="col-sm-9 col-md-10 main">
          <h1 class="page-header">Edit Record</h1>
          <div class="table-responsive">
            <table id="recordtable" class="table table-striped">
              <thead>
                <tr>
                  <th>FileName</th>
                  <th>CommitCode</th>
                  <th>EditTime</th>
                  <th>FileLink</th>
                </tr>
              </thead>
              <tbody>

              </tbody>
            </table>
          </div>
        </div>
        
        <div class="col-sm-3 col-md-2 sidebar">
		  <img data-src="holder.js/200x200/auto/sky" class="img-circle" alt="200x200" src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMDAiIGhlaWdodD0iMjAwIj48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iIzBEOEZEQiIvPjx0ZXh0IHRleHQtYW5jaG9yPSJtaWRkbGUiIHg9IjEwMCIgeT0iMTAwIiBzdHlsZT0iZmlsbDojZmZmO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1zaXplOjEzcHg7Zm9udC1mYW1pbHk6QXJpYWwsSGVsdmV0aWNhLHNhbnMtc2VyaWY7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MjAweDIwMDwvdGV4dD48L3N2Zz4=">
          <ul class="nav nav-sidebar">
            <li><a href="#">NAME:<span id="user_login"></span></a></li>
            <li><a href="#">ID:<span id="user_id"></span></a></li>
            <li><a href="#">email:<span id="user_email"></span></a></li>
          </ul>
        </div>
        
        
      </div>
    </div>
	
	<div class="navbar navbar-default navbar-fixed-bottom" role="navigation">
      <div class="container-fluid">
        <div class="navbar-collapse collapse">
		  <ul class="nav navbar-nav navbar-right">
            <li>This is FootNote!</li>
          </ul>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="../../dist/js/bootstrap.min.js"></script>
    <script src="../../assets/js/docs.min.js"></script>
    <script type="text/javascript">
    $(function(){
    	$("#log_out").click(function(){
				$.get("./logout", null, null, "json")
			 .success(function(resp){
				if(resp.status = true){
					alert("Log out success!");
					window.location.href = "http://localhost:8080/HelloWorld/git_index.html";
			 	}
			});
		});
    	
    	$("#user_id").text(user["id"]);
    	$("#user_login").text(user["user"]);
    	$("#user_email").text(user["email"]);
		// insert record
		var tableRf;
		var rows;
		var row;
		var fname, ccode, ctime, link;
		var cell;
		for(i = 0; i < record.length; i++){
			dt = record[i];
			fname = dt.filename;
			ccode = dt.commitcode;
			ctime = dt.created_time;
			link = dt.filelink;
			
			tableRf = document.getElementById('recordtable');
			rows = tableRf.getElementsByTagName('tbody')[0].getElementsByTagName('tr').length;
			row = tableRf.insertRow(rows+1);
			
			cell = row.insertCell(0);
			cell.innerHTML = fname;
			cell = row.insertCell(1);
			cell.innerHTML = ccode;
			cell = row.insertCell(2);
			cell.innerHTML = ctime;
			cell = row.insertCell(3);
			cell.innerHTML = "<a href=\"" + link + "\">" + link + "</a>";
		}
    });
    </script>
  </body>
</html>
