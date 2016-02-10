package posts;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

@WebServlet("/NewCommentServlet")
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewCommentServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") == null){
			response.sendRedirect("home.jsp");
		}else{
			
			Integer postInfoID = Integer.parseInt(request.getParameter("postId"));
			
			try{
				PostInformation postInfo = PostInformation.getPostById(postInfoID);
				session.setAttribute("postInfo", postInfo);
				
			}catch(Exception e){
				e.getMessage();
			}
			response.sendRedirect("Comment.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		HttpSession session = request.getSession();
		String commentContent = request.getParameter("content");
		
		PostInformation pi = (PostInformation) session.getAttribute("postInfo");
		UserInformation ui = (UserInformation) session.getAttribute("user");
		
		int uId = ui.getId();
		int pId = pi.getId();

		String errors = CommentInformation.newCommentValidation(commentContent);
		
		if(errors != null){
			RequestDispatcher rd = request.getRequestDispatcher("Comment.jsp");
			
			request.setAttribute("errors", errors);
			rd.forward(request, response);
		}else{
			try {
				CommentInformation.createComment(pId, uId, commentContent);		
			} catch (SQLException e) {
				e.getMessage();
			}
				response.sendRedirect("Posts");
		}
	}
}
