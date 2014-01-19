import java.io.*;

public class User {

	protected String name;
	protected PrintWriter out;
	protected int ID;
	
	public User (String str, PrintWriter out) {
		name = str;
		this.out = out;
	} // constructor
	
	public void sendMsg(String s) {
		out.println(s);
	}
	
} // User