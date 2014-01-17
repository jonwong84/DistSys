import java.net.*;
import java.io.*;

public class ChatServerThread extends Thread {

	private Socket socket = null;
	private ChatProtocol p;
	private String userName;
	private boolean active;
	private Chatter db;
	
	public ChatServerThread (Socket socket) {
		super ("ChatServerThread");
		active = true;
		db = ChatServer.db;
		this.socket = socket;
	} // constructor
	
	public void run() {
				
		try (
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				) {
			String inputLine, outputLine;
			p = new ChatProtocol(db);
			
			outputLine = p.processInput("");
			out.println("[Server]: " + outputLine);
			inputLine = in.readLine();
			userName = "[" + inputLine + "]";
			out.println("[Server]: Name registered as " + userName + ".");
			
			new ChatServerReader(socket,in).run();
			//db.addUser(socket,out);
			
			while(active) {
				
				//inputLine = p.processInput(in.readLine());
				out.println(outputLine);
				if (outputLine.equals("/exit"))
					active = false;
			}
			socket.close();
			ChatServer.decrement();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	} // run
	
} // ChatServerMinion
