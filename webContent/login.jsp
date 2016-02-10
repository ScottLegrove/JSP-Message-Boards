<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>

	<link rel="stylesheet" type="text/css" href="css/main.css">
	<link rel="stylesheet" type="text/css" href="css/form.css">
</head>
<body>
	<nav>
		<h3>Message Board</h3>
		<ul>
			<li><a href="Home">Home</a></li>
			<li><a href="Register">Register</a></li>
		</ul>
	</nav><br>
	
	<main>
		<% 	if(session.getAttribute("error") != null){ %>
				<p class="error" align="center"><%= session.getAttribute("error") %></p>
		<%  }
		
			// Redirect user if logged in to the "posts" page
			if(session.getAttribute("user") != null){
				response.sendRedirect("Posts.jsp");
			}else // If user not logged in, reset session
				session.removeAttribute("error");
		%>
		<form action="Login" method="post">
			<table>
				<tr>
					<td><label for="un">Email: </label></td>
					<td><input type="text" name="username" id="un"></td>
				</tr>
				<tr>
					<td><label for="pass">Password: </label></td>
					<td><input type="password" name="password" id="pass"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Log-In" style=""></td>
				</tr>
			</table>
		</form>
	</main>
</body>
</html>