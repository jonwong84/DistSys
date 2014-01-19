/**
 * ChatServerThread class by Jonathan Wong
 * @author Jonathan Wong
 * 
 * Server-side thread responsible for handling a single instance of a client socket connection.
 * Handles incoming messages and new socket connections.
 */

import java.net.*;
import java.io.*;

public class ChatServerThread extends Thread {

	User user;
	private Socket socket = null;
	private boolean active;
	private Chatter chatter;
	
	public ChatServerThread (Socket socket) {
		super ("ChatServerThread");
		active = true;
		chatter = ChatServer.chatter;
		this.socket = socket;
	} // constructor
	
	/**
	 * Completes a new socket connection to a client, and stores its corresponding User information
	 * within the Chatter thread. It will continually listen for incoming messages, and add it to
	 * the Chatter's buffer.
	 */
	public void run() {
				
		try (
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				) {
			String inputLine, outputLine;
			
			outputLine = "[Server] Connection successful!";
			out.println(outputLine);
			
			inputLine = in.readLine();
			String userName = "[" + inputLine + "]";
			user = new User(userName,out);
			chatter.addUser(user);
			out.println("[Server] You are now logged in as " + userName + ".");
						
			while(active) {
				
				while ((inputLine = in.readLine()) != null) {
					
					outputLine = user.name + " " + inputLine;
				
				if (inputLine.equals("/exit")) {
					active = false;
					outputLine = "[Server] " + userName + " has left the server.";
				}
				
				chatter.addMsg(outputLine);
				} // while inputLine block
			}
			ChatServer.decrement();
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		}
		catch (IOException e) {
			System.out.println("Connection error detected. Details as follows:");
			e.printStackTrace();
		}
		
	} // run
	
} // ChatServerMinion
