<%@ page import ="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>

	<link rel="stylesheet" type="text/css" href="css/main.css">
	<link rel="stylesheet" type="text/css" href="css/form.css">
</head>
<body>
<%--  <%= session.getAttribute("errorFirstName") %>  --%>
<%-- <%= errorList.get("firstName") %> --%>
	<nav>
		<h3>Message Board</h3>
		<ul>
			<li><a href="Home">Home</a></li>
			<li><a href="Login">Login</a></li>
			<li><a href="Posts.jsp">Posts</a></li>
		</ul>
	</nav>
	<br>
	<main>
		
		<br>
	<form action="Register" method="post">
						
			<table>
				<tr>
					<td><label for="un">First Name: </label></td>
					<td><input type="text" name="firstName" id="un" value=${ errors.validFirstName }></td>
					<td>${ errors.firstName }<td>
				</tr>
				<tr>
					<td><label for="un">Last Name: </label></td>
					<td><input type="text" name="lastName" id="un" value=${ errors.validLastName }></td>
					<td>${ errors.lastName }<td>
				</tr>
				<tr>
					<td><label for="un">Email: </label></td>
					<td><input type="text" name="email" id="un" value=${ errors.validEmail }></td>
					<td>${ errors.email } ${ errors.emailInUse }<td>
				</tr>
				<tr>
					<td><label for="un">Password: </label></td>
					<td><input type="password" name="password" id="un"></td>
					<td>${ errors.password }<td>
				</tr>
				<tr>
					<td><label for="un">Phone Number: </label></td>
					<td><input type="text" name="phoneNumber" id="un" value=${ errors.validPhoneNumber }></td>
					<td>${ errors.phoneNumber }<td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Register" style=""></td>
				</tr>

			</table>
		</form>
	</main>
</body>
</html>