package user;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/****************************************************************************************************
* Project:  Pragmatic Development 
* Assignment:  Assignment 2
* Author(s):  Scott Legrove, Khalil Sherif, Masatoshi Windle
* Student Number: 100914325, 100901937, 100913032
* Date: 12/04/2015
* Description: JSP and Servlets working in an MVC fashion to create a forum software with user and
* admin ability
****************************************************************************************************/

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession sess;
    /**
     * Default constructor. 
     */
    public Login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		UserInformation ul;
		String error = "";
		sess = request.getSession();
		String email = request.getParameter("username");
		String pass = request.getParameter("password");
		
		// If user already logged in go to "posts"
		if(sess.getAttribute("user") != null){
			response.sendRedirect("Posts.jsp");
			return;
		}
			
		// Check for empty fields
		if(email == null || email.equals("") || pass == null || pass.equals("")){
			if(email == null || email.equals(""))
				error += "Email is missing<br>";
			if(pass == null || pass.equals(""))
				error += "Password is missing";
			
			sess.setAttribute("error", error);
			response.sendRedirect("login.jsp");
			return;
		}
		
		sess.removeAttribute("error");
			
		// Try to login user
		try {
			ul = UserInformation.FromLogin(email, pass);
			if(ul == null){
				sess.setAttribute("error", "Invalid login credentials");
				response.sendRedirect("login.jsp");
			}else{
				sess.setAttribute("user", ul);
				response.sendRedirect("Posts");
			}
		} catch (SQLException e) {
			sess.setAttribute("error", "Invalid login credentials");
			response.sendRedirect("login.jsp");
		}
	}

}
