<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Comment</title>
</head>
<body>

	<% 
		// Redirect user if not logged in to "login" page
		if(session.getAttribute("user") == null){
			response.sendRedirect("home.jsp");
		}
	%>
	
	<h1>New Comment</h1>
		<hr/>
	
		<h2>Post</h2>
		
		<h4>Title: ${ sessionScope.postInfo.title } </h4>
		<h5><strong>Author: ${ sessionScope.postInfo.author.firstName }</strong></h5>
				<h5>${ sessionScope.postInfo.timePosted }</h5>
		<p>${ sessionScope.postInfo.content }</p>
			
				

	<form action="NewCommentServlet" Method="Post">
	<input type="hidden" name ="postId" value="21"/>
	  User: ${ user.firstName }
	  <br/>
	  Content:&nbsp;&nbsp; ${ errors }<br/>
	  <textarea rows="7" cols="45" name="content"></textarea>
	  	<br/>
	  <input type="submit" value="Submit">	
	</form>

</body>
</html>