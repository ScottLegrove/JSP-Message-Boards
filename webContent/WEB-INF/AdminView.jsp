<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Posts</title>
	
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<link rel="stylesheet" type="text/css" href="css/form.css">
</head>
<body>


	<nav>
		<h3>Message Board</h3>
		<ul>
			<li><a href="Home">Home</a></li>
			<li><a href="Posts">Posts</a></li>
			<li><a href="NewPost.jsp">New Post</a></li>
		</ul>
		
		<a href="Logout" class="logout">Logout</a>
	</nav><br>
	
	<main>
		<h1>Posts</h1>
		<!-- Errors were present in the request -->
		<h3 style="color:red;">${error }</h3>
		
		<!-- Prompt user if no posts -->
		${noPosts }
		
		<!-- Display posts if posts exist -->
		<c:forEach items="${ posts }" var="PCPair">
			<table>
				<!-- Post Title and Info -->
				<tr>
					<td colspan="2">
						<strong>${PCPair.pi.title }</strong> by 
						${PCPair.pi.author.firstName } ${PCPair.pi.author.lastName } - 
						${PCPair.pi.timePosted }
					</td>
				</tr>
				
				<!-- Post contents -->
				<tr>
					<td colspan="2">
						<p>
							${PCPair.pi.content }
						</p>
					</td>
				</tr>
				
				<!-- Comments -->
				<tr>
					<td colspan="2">
						<ul style="list-style-type: none;">
							<c:forEach items="${PCPair.ci }" var="Comment">
								<li>
									<!-- Comment Contents -->
									${Comment.author.firstName } commented, 
									"${Comment.content }" - ${Comment.timePosted }
									
									<!-- Delete link -->
									<a href="AdminController?commentId=${Comment.id }">Delete</a>
								</li>
							</c:forEach>
						</ul>
					</td>
				<tr>
				
				<!-- Post removal and Commenting -->
				<tr>
					<td>
					</td>
					<td>
						<a href="Comment.jsp?postId=${PCPair.pi.id }">Comment</a>
						&nbsp;
						| &nbsp;<a href="AdminController?postId=${PCPair.pi.id }">Remove Post</a>
					</td>
				</tr>
			</table><br>
			<hr>
		</c:forEach>
	</main>
</body>
</html>