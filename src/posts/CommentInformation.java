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

public final class CommentInformation {
	private int id;
	private UserInformation author;
	private PostInformation post;
	private Timestamp timePosted;
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

	public String getContent() {
		return content;
	}
	
	public PostInformation getPost(){
		return post;
	}
	
	public CommentInformation(int ID) throws SQLException	{
		id = ID;
		
		String query = "SELECT * FROM Comments_T WHERE ID = ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, ID);
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
		
		if(rs.next())
		{
			post = new PostInformation(rs.getInt("AssociatedPost"));
			content = rs.getString("Content");
			timePosted = rs.getTimestamp("TimePosted");
			author = new UserInformation(rs.getInt("Author"));
		} else { throw new SQLException("Post does not exist!"); }
	}
	
	public static int getCommentCount(int postId) throws SQLException{
		String query = "SELECT COUNT(ID) FROM Comments_T WHERE AssociatedPost = ?";
		
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, postId);
		
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
		
		int count = 0;
		
		if(rs.next())
		{
			count = rs.getInt(1);
		}
		
		return count;
	}
	
	public static CommentInformation getCommentByIndex(int postId, int index) throws SQLException{
		String query = "SELECT ID FROM Comments_T WHERE AssociatedPost = ? LIMIT ?, 1";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, postId);
		ps.setInt(2, index);
		
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
			return new CommentInformation(id);
	}
	
	public static CommentInformation getCommentById(int id) throws SQLException{
		return new CommentInformation(id);
	}

	public static CommentInformation[] getComments(int postId, int startIndex, int count) throws SQLException{
		ArrayList<CommentInformation> comments = new ArrayList<CommentInformation>();
		
		String query = "SELECT ID FROM Comments_T WHERE AssociatedPost = ? LIMIT ?, ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, postId);
		ps.setInt(2, startIndex);
		ps.setInt(3, count);
		
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
				
		while(rs.next())
		{
			comments.add(new CommentInformation(rs.getInt("ID")));
		}
		
		DBController.CloseResultSet(rs);
		
		CommentInformation[] returnArray = new CommentInformation[comments.size()]; 
		comments.toArray(returnArray);
		return returnArray;
	}
	
	public static CommentInformation createComment(int postId, int authorId, String content) throws SQLException{
		int id;
		
		String query = "INSERT INTO Comments_T(AssociatedPost, Author, Content) VALUES (?, ?, ?)";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, postId);
		ps.setInt(2, authorId);
		ps.setString(3, content);
		
		
		DBController.UpdatePreparedStatement(ps);
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		
		id = rs.getInt(1);
		DBController.CloseStatement(ps);
		
		return new CommentInformation(id);
	}
	
	public static boolean deleteComment(int commentId) throws SQLException{
		int res;
		
		String query = "DELETE FROM Comments_T WHERE ID = ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, commentId);
		
		res = DBController.UpdatePreparedStatement(ps);
		DBController.CloseStatement(ps);
		
		return (res != 0);
	}
	
	public static CommentInformation[] getAllComments(int postId) throws SQLException{
		ArrayList<CommentInformation> comments = new ArrayList<CommentInformation>();
		
		String query = "SELECT ID FROM Comments_T WHERE AssociatedPost = ?";
		PreparedStatement ps = DBController.GetPreparedStatement(query);
		
		ps.setInt(1, postId);
		
		ResultSet rs = DBController.ExecutePreparedStatement(ps);
				
		while(rs.next())
		{
			comments.add(new CommentInformation(rs.getInt("ID")));
		}
		
		DBController.CloseResultSet(rs);
		
		CommentInformation[] returnArray = new CommentInformation[comments.size()]; 
		comments.toArray(returnArray);
		return returnArray;
	}
	
	public static String  newCommentValidation(String content){
		boolean isValid = true;
		String commentErrors = "";
		
		if((content.isEmpty()) || (content == null)){
			isValid = false;
			commentErrors = "Missing Content";
		}
		
		if(isValid)
			return null;
		else
			return commentErrors;
	}
}
