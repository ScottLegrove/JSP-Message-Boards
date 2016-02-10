<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Start a new post</title>
</head>
<body>
	<% 
		// Redirect user if not logged in to "login" page
		if(session.getAttribute("user") == null){
			response.sendRedirect("home.jsp");
		}
	%>
<h1>New Post</h1>
<hr/>
<form action="NewPostServlet" Method="Post">
  User: ${ user.firstName }
  	<br/>
  Title: <input type="text" name="title" value= ${ errors[0] }>
  	<br/>
  	<br/>
  Content: <br/>
  <textarea rows="7" cols="45" name="content">${ errors[1] }</textarea>
  	<br/>
  <input type="submit" value="Submit">	
</form>

</body>
</html>