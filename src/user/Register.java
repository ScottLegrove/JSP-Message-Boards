package user;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
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
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	private HttpSession session = null;
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("user");
		response.sendRedirect("register.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		session = request.getSession();
		session.removeAttribute("user");
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		
		
		//String cleanPhone = phoneNumber.replaceAll(" ", "").replaceAll("-", "");
		
		synchronized(this){
			try{
				HashMap<String, String> errors = UserRegistrationValidation.validateAndCreate(firstName, lastName, email, password, phoneNumber);
			
					if(errors != null){
						RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
						request.setAttribute("errors", errors);
						rd.forward(request, response);
					

					}else{
						session.setAttribute("error", "Successfully Registered");
						response.sendRedirect("login.jsp");
					}
				
			}catch(Exception e){
				e.getMessage();
			}
		}
	}
}
