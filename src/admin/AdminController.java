package admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import posts.CommentInformation;
import posts.PostCommentPair;
import posts.PostInformation;
import user.UserInformation;

/****************************************************************************************************
* Project:  Pragmatic Development 
* Assignment:  Assignment 2
* Author(s):  Scott Legrove, Khalil Sherif, Masatoshi Windle
* Student Number: 100914325, 100901937, 100913032
* Date: 12/04/2015
* Description: JSP and Servlets working in an MVC fashion to create a forum software with user and
* admin ability
****************************************************************************************************/

@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") == null){
			session.setAttribute("error", "Please login");
			response.sendRedirect("login.jsp");
		}else{
	
		
			UserInformation userInfo = (UserInformation) session.getAttribute("user");
			
			if(userInfo == null){
				session.setAttribute("error", "You must log in");
				response.sendRedirect("login.jsp");
			}
			
			synchronized(this){
				if(!userInfo.isAdmin()){
					RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/NotAnAdmin.jsp");
					rd.forward(request, response);
				}else{
					try{
						request.getSession().removeAttribute("noPosts");
						
						if(PostInformation.getPostCount() == 0)
							request.getSession().setAttribute("noPosts", 
									"No posts were found, would you like to add a post?<br>"
									+ "<a href='NewPost.jsp'>Add New Post</a>");
						
						// Check if objects were requested for deletion
						if(request.getParameter("postId") != null)
							PostInformation.deletePost(Integer.parseInt(request.getParameter("postId").toString()));
						if(request.getParameter("commentId") != null)
							CommentInformation.deleteComment(Integer.parseInt(request.getParameter("commentId").toString()));
					}catch(Exception e){
						request.setAttribute("error", "Post/Comment was not deleted, please try again later");
					}
					
					try{
						
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
						request.setAttribute("posts", PCPairs);
					}catch(Exception e){
						// If there are no posts, noPosts attribute will be set to pass to the view
						request.setAttribute("noPosts", 
								"No posts were found, would you like to add a post?<br>"
								+ "<a href='NewPost.jsp'>Add New Post</a>");
					}
					RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/AdminView.jsp");
					rd.forward(request, response);
				}
			}
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
