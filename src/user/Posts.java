package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import posts.CommentInformation;
import posts.PostCommentPair;
import posts.PostInformation;

/****************************************************************************************************
* Project:  Pragmatic Development 
* Assignment:  Assignment 2
* Author(s):  Scott Legrove, Khalil Sherif, Masatoshi Windle
* Student Number: 100914325, 100901937, 100913032
* Date: 12/04/2015
* Description: JSP and Servlets working in an MVC fashion to create a forum software with user and
* admin ability
****************************************************************************************************/
@WebServlet("/Posts")
public class Posts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Posts() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		try{
			// Check if objects were requested for deletion
			if(request.getParameter("postId") != null)
				PostInformation.deletePost(Integer.parseInt(request.getParameter("postId").toString()));
			if(request.getParameter("commentId") != null)
				CommentInformation.deleteComment(Integer.parseInt(request.getParameter("commentId").toString()));
		}catch(Exception e){
			request.setAttribute("error", "Post/Comment was not deleted, please try again later");
		}
		
		
		request.getSession().removeAttribute("noPosts");
		try{		
			
			if(PostInformation.getPostCount() == 0)
				request.getSession().setAttribute("noPosts", 
						"No posts were found, would you like to add a post?<br>"
						+ "<a href='NewPost.jsp'>Add New Post</a>");
			
			// Create object pairing
			PostCommentPair PCPair = null;
			
			// Instantiate holding variables
			PostInformation[] posts = PostInformation.getAllPosts();
			CommentInformation[] comments;
			PostCommentPair[] PCPairs = new PostCommentPair[PostInformation.getPostCount()];
			
			// Fill the postCommentPair object
			int i = 0;
			for(PostInformation post : posts){
				comments = CommentInformation.getAllComments(post.getId());
				PCPair = new PostCommentPair(post, comments);
				PCPairs[i++] = PCPair;
			}
			request.getSession().setAttribute("posts", PCPairs);
		}catch(Exception e){
			// If there are no posts, noPosts attribute will be set to pass to the view
			request.getSession().setAttribute("noPosts", 
					"No posts were found, would you like to add a post?<br>"
					+ "<a href='NewPost.jsp'>Add New Post</a>");
		}
		
		response.sendRedirect("Posts.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
