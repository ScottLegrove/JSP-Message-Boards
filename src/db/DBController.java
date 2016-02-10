package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/****************************************************************************************************
* Project:  Pragmatic Development 
* Assignment:  Assignment 2
* Author(s):  Scott Legrove, Khalil Sherif, Masatoshi Windle
* Student Number: 100914325, 100901937, 100913032
* Date: 12/04/2015
* Description: JSP and Servlets working in an MVC fashion to create a forum software with user and
* admin ability
****************************************************************************************************/

public final class DBController {
	
	private static final String SQL_HOST = "localhost";
	private static final String SQL_USER = "root";
	private static final String SQL_PASSWORD = "admin";
	private static final String SQL_DATABASE = "comp3095_pragdevelopment";
	
	private static final String SQL_CONNECTIONSTRING = "jdbc:mysql://%s/%s?user=%s&password=%s";
	
	private static Object instance = null;
	
	public static Connection CreateSQLConnection(){
		
		try {
			if(instance == null)
				instance = Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		
		try
		{
			String connString = String.format(
					SQL_CONNECTIONSTRING,
					SQL_HOST,
					SQL_DATABASE,
					SQL_USER,
					SQL_PASSWORD);

			conn = DriverManager.getConnection(connString);
			
		}catch(SQLException ex){
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return conn;
	}
	
	public static ResultSet Query(String query) throws SQLException{
		Connection con = CreateSQLConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(query);
		return rs;
	}
	
	public static void CloseStatement(Statement statement) throws SQLException{
		Connection con = statement.getConnection();
		
		statement.close();
		con.close();
	}
	
	public static void CloseResultSet(ResultSet rs) throws SQLException{
		Statement statement = rs.getStatement();
		Connection con = statement.getConnection();
		
		rs.close();
		statement.close();
		con.close();
	}
	
	public static PreparedStatement GetPreparedStatement(String query) throws SQLException{
		Connection con = CreateSQLConnection();
		PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		return statement;
	}
	
	public static ResultSet ExecutePreparedStatement(PreparedStatement statement) throws SQLException{
		return statement.executeQuery();
	}
	
	public static int UpdatePreparedStatement(PreparedStatement statement) throws SQLException{
		return statement.executeUpdate();
	}
}

