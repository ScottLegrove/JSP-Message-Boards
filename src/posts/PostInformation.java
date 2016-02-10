package posts;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.String;
import db.DBController;
import user.UserInformation;
import java.util.ArrayList;

/****************************************************************************************************
* Project:  Pragmatic Development 
* Assignment:  Assignment 2
* Author(s):  Scott Legrove, Khalil Sherif, Masatoshi Windle
* Student Number: 100914325, 100901937, 100913032
* Date: 12/04/2015
* Description: JSP and Servlets working in an MVC fashion to create a forum software with user and
* admin ability
****************************************************************************************************/

public final class PostInformation {
	private int id;
	private UserInformation author;
	private Timestamp timePosted;
	private String title;
	private String content;
	
	public int getId() {
		return id;
	}

	public UserInformation getAuthor() {
		return author;
	}

	public Timestamp getTimePosted() {
		return timePosted;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	public PostInformation(int ID) throws SQLException	{
		id = ID;
		
		String query = "SELECT * FROM Posts_T WHERE ID = ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, ID);
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
		if(rs.next())
		{
			title = rs.getString("Title");
			content = rs.getString("Content");
			timePosted = rs.getTimestamp("TimePosted");
			author = new UserInformation(rs.getInt("Author"));
		} else { throw new SQLException("Post does not exist!"); }
	}
	
	public static int getPostCount() throws SQLException{
		String query = "SELECT COUNT(ID) FROM Posts_T";
		ResultSet rs = DBController.Query(query);
		rs.first();
		int count = rs.getInt(1);
		
		return count;
	}
	
	public static PostInformation getPostByIndex(int index) throws SQLException{
		String query = "SELECT ID FROM Posts_T LIMIT ?, 1";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		ps.setInt(1, index);
		
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
		
		Integer id = null;
		
		if(rs.next())
		{
			id = rs.getInt("ID");
		}
		
		DBController.CloseResultSet(rs);
		
		if(id == null)
			return null;
		else
			return new PostInformation(id);
	}
	
	public static PostInformation getPostById(int id) throws SQLException{
		return new PostInformation(id);
	}
	
	public static PostInformation[] getPosts(int startIndex, int count) throws SQLException{
		ArrayList<PostInformation> posts = new ArrayList<PostInformation>();
		
		String query = "SELECT ID FROM Posts_T LIMIT ?, ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, startIndex);
		ps.setInt(2, count);
		
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
				
		while(rs.next())
		{
			posts.add(new PostInformation(rs.getInt("ID")));
		}
		
		DBController.CloseResultSet(rs);
		
		PostInformation[] returnArray = new PostInformation[posts.size()]; 
		posts.toArray(returnArray);
		return returnArray;
	}
	
	public static PostInformation createPost(int authorId, String title, String content) throws SQLException{
		int id;
		
		String query = "INSERT INTO Posts_T(Author, Title, Content) VALUES (?, ?, ?)";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, authorId);
		ps.setString(2, title);
		ps.setString(3, content);
		
		DBController.UpdatePreparedStatement(ps);
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		id = rs.getInt(1);
		DBController.CloseStatement(ps);
		
		return new PostInformation(id);
	}
	
	public static boolean deletePost(int postId) throws SQLException{
		int res;
		
		deleteAllComments(postId);
		
		String query = "DELETE FROM Posts_T WHERE ID = ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, postId);
		
		res = DBController.UpdatePreparedStatement(ps);
		DBController.CloseStatement(ps);
		
		return (res != 0);
	}
	
	public static int deleteAllComments(int postId) throws SQLException{
		int res;
		
		String query = "DELETE FROM Comments_T WHERE AssociatedPost = ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, postId);
		
		res = DBController.UpdatePreparedStatement(ps);
		DBController.CloseStatement(ps);
		
		return res;
	}
	
	public static PostInformation[] getAllPosts() throws SQLException{
		ArrayList<PostInformation> posts = new ArrayList<PostInformation>();
		
		String query = "SELECT ID FROM Posts_T";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
				
		while(rs.next())
		{
			posts.add(new PostInformation(rs.getInt("ID")));
		}
		
		DBController.CloseResultSet(rs);
		
		PostInformation[] returnArray = new PostInformation[posts.size()]; 
		posts.toArray(returnArray);
		return returnArray;
	}
	
	public static ArrayList<String>  newPostValidation(String title, String content){
		boolean isValid = true;
		ArrayList<String> postErrors = new ArrayList<String>();
		
		if((title.isEmpty()) || (title == null)){
			isValid = false;
			postErrors.add("Missing");
		}else{
			postErrors.add(title);
		}
		
		if((content.isEmpty()) || (content == null)){
			isValid = false;
			postErrors.add("Missing Content");
		}else{
			postErrors.add(content);
		}
		
		if(isValid)
			return null;
		
		return postErrors;
	}
}
