package posts;

/****************************************************************************************************
* Project:  Pragmatic Development 
* Assignment:  Assignment 2
* Author(s):  Scott Legrove, Khalil Sherif, Masatoshi Windle
* Student Number: 100914325, 100901937, 100913032
* Date: 12/04/2015
* Description: JSP and Servlets working in an MVC fashion to create a forum software with user and
* admin ability
****************************************************************************************************/

public class PostCommentPair {
	PostInformation pi; 
	CommentInformation[] ci;
	public PostCommentPair(PostInformation pi, CommentInformation[] ci) {
		super();
		this.pi = pi;
		this.ci = ci;
	}
	public PostInformation getPi() {
		return pi;
	}
	public void setPi(PostInformation pi) {
		this.pi = pi;
	}
	public CommentInformation[] getCi() {
		return ci;
	}
	public void setCi(CommentInformation[] ci) {
		this.ci = ci;
	}
	
	public String printPairs(){
		return "";
	}
}
