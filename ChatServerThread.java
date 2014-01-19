import java.net.*;
import java.io.*;

public class ChatServerThread extends Thread {

	User user;
	private Socket socket = null;
	//private ChatProtocol p;
	private String userName;
	private boolean active;
	private Chatter chatter;
	private Thread sender;
	
	public ChatServerThread (Socket socket) {
		super ("ChatServerThread");
		active = true;
		chatter = ChatServer.chatter;
		this.socket = socket;
	} // constructor
	
	public void run() {
				
		try (
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				) {
			String inputLine, outputLine;
			
			outputLine = "[Server] Connection successful!";
			out.println(outputLine);
			
			inputLine = in.readLine();
			userName = "[" + inputLine + "]";
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
				//out.println(outputLine);
				} // while inputLine block
			}
			ChatServer.decrement();
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	} // run
	
} // ChatServerMinion
