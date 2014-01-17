import java.net.*;
import java.io.*;

public class ChatProtocol {

	private static final int get_username = 100;
	private static final int checked_in = 200;
	private static final int disconnect = 300;
	
	private Chatter db;
	private int state;
	private int counter;
	
	public ChatProtocol(Chatter d) {
		db = d;
		state = get_username;
		counter = 0;
	} // constructor
	
	public String processInput(String msg) {
		
		String output = "";
		
		if (state == get_username) {
			output = "Connection successful!";
			state = checked_in;
		}
		
		else if (state == checked_in) {
			if (msg.equals("/exit")) {
				state = disconnect;
			}
			output = msg;
		}
		return output;
	} // relay
	
	
} // ChatProtocol