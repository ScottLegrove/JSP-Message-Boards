package user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/****************************************************************************************************
* Project:  Pragmatic Development 
* Assignment:  Assignment 2
* Author(s):  Scott Legrove, Khalil Sherif, Masatoshi Windle
* Student Number: 100914325, 100901937, 100913032
* Date: 12/04/2015
* Description: JSP and Servlets working in an MVC fashion to create a forum software with user and
* admin ability
****************************************************************************************************/

public class UserRegistrationValidation {
	
	private static final Pattern EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
	
	public UserRegistrationValidation(){}
	
	 public static boolean phoneRegexValidation(String phone){
	    	
	    	boolean validPhone;
	    	
	    	if(phone.matches("\\d{10}")){
	    		validPhone =  true;
	    	}else if(phone.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")){
	    		validPhone = true;
	    	}else if(phone.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")){
	    		validPhone = true;
	    	}else if(phone.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")){
	    		validPhone = true;
	    	}else 
	    		validPhone = false;
	    	
	    	return validPhone;
	    }

		public static boolean emailRegexValidation(String email) {
	        Matcher matcher = EMAIL_ADDRESS_REGEX .matcher(email);
	        return matcher.find();
		}
		

		public static HashMap<String, String> validateAndCreate(String firstName, String lastName,
				String email, String password, String phoneNumber){
			HashMap<String, String> errorList = new HashMap<String, String>();
			

			boolean emailInUse = false;
			boolean invalidForm = false;
			
			if((firstName == null) || (firstName.isEmpty())){
				errorList.put("firstName", "Missing first name");
				invalidForm = true;
			}else{
				errorList.put("validFirstName", firstName);
			}
			
			if((lastName == null) || (lastName.isEmpty())){
				errorList.put("lastName", "Missing last name");
				invalidForm = true;
			}else{
				errorList.put("validLastName", lastName);
			}
	
			
			if((email == null) || (email.isEmpty())  || (emailRegexValidation(email) == false)){
				errorList.put("email", "Missing or Invalid email");
				invalidForm = true;
			}else{
				errorList.put("validEmail", email);
			}
			
			if((password == null) || (password.isEmpty())){
				errorList.put("password", "Missing password");
				invalidForm = true;
			}
			
			if((phoneNumber == null) || (phoneNumber.isEmpty()) || (phoneRegexValidation(phoneNumber) == false)){
				errorList.put("phoneNumber", "Missing or Invalid phone number");
				invalidForm = true;
			}else{
				String validCleanPhone = phoneNumber.replaceAll(" ", "-");
				errorList.put("validPhoneNumber", validCleanPhone);
			}
			
			if(!invalidForm){
				String cleanPhone = phoneNumber.replaceAll(" ", "").replaceAll("-", "");
				
				try {
					UserInformation ui = UserInformation.Register(firstName, lastName, email, password, cleanPhone);
					if( ui == null){
						emailInUse = true;
						errorList.put("emailInUse", "Email is already in use");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
			
			
			
			if((invalidForm) || (emailInUse))
				return errorList;
			else
				return null;
		}
	      
}
