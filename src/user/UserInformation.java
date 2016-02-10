package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.lang.String;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import db.DBController;

/****************************************************************************************************
* Project:  Pragmatic Development 
* Assignment:  Assignment 2
* Author(s):  Scott Legrove, Khalil Sherif, Masatoshi Windle
* Student Number: 100914325, 100901937, 100913032
* Date: 12/04/2015
* Description: JSP and Servlets working in an MVC fashion to create a forum software with user and
* admin ability
****************************************************************************************************/

public class UserInformation {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNumber;
	private int admin;
	
	public UserInformation(int ID) throws SQLException	{
		id = ID;
		
		String query = "SELECT * FROM Users_T WHERE ID = ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, ID);
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
		if(rs.next())
		{
			firstName = rs.getString("FirstName");
			lastName = rs.getString("LastName");
			email = rs.getString("Email");
			password = rs.getString("Password");
			phoneNumber = rs.getString("PhoneNumber");
			admin = rs.getInt("IsAdmin");
		} else { throw new SQLException("User does not exist!"); }
		
		DBController.CloseResultSet(rs);
	}
	
	public static UserInformation FromLogin(String email, String password) throws SQLException{
		UserInformation returnValue = null;
		
		String query = "SELECT ID FROM Users_T WHERE Email = ? AND Password = ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			password = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ps.setString(1, email);
		ps.setString(2, password);
		
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
		if(rs.next())
			returnValue = new UserInformation(rs.getInt("ID"));
		
		DBController.CloseResultSet(rs);
		
		return returnValue;
	}
	
	public static boolean UserExists(String email) throws SQLException{
		boolean returnValue = true;
		
		String query = "SELECT ID FROM Users_T WHERE Email = ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setString(1, email);
		
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
		if(!rs.next())
			returnValue = false;
		
		DBController.CloseResultSet(rs);
		
		return returnValue;
	}
	
	//TODO: Implement SaveChanges
	public boolean SaveChanges(String fname, String lname, String email, String password, String phoneNumber) throws SQLException{
		return true;
	}
	
	public static UserInformation Register(String fname, String lname, String email, String password, String phoneNumber) throws SQLException{
		return Register( fname, lname, email, password, phoneNumber, false);
	}
	
	public static UserInformation Register(String fname, String lname, String email, String password, String phoneNumber, boolean admin) throws SQLException{
		if(UserExists(email))
			return null;
		
		String query = "INSERT INTO Users_T (FirstName, LastName, Email, Password, PhoneNumber) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		MessageDigest md;
		String newPassword = password;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			newPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		
		ps.setString(1, fname);
		ps.setString(2, lname);
		ps.setString(3, email);
		ps.setString(4, newPassword);
		ps.setString(5, phoneNumber);
		
		DBController.UpdatePreparedStatement(ps);
		DBController.CloseStatement(ps);
		
		return UserInformation.FromLogin(email, password);
	}
	
	public int getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public boolean isAdmin(){
		return admin != 0;
	}
}
