/**
 * User class by Jonathan Wong
 * @author Jonathan Wong
 * 
 * A simple structural class for storing user information.
 */

import java.io.*;

public class User {

	protected String name;
	protected PrintWriter out;

	public User(String str, PrintWriter out) {
		name = str;
		this.out = out;
	} // constructor

	/**
	 * Public method that allows an outer class to send a String message to the
	 * connected client through the socket.
	 * 
	 * @param s
	 */
	public void sendMsg(String s) {
		out.println(s);
	} // sendMsg

} // User