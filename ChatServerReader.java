import java.net.*;
import java.io.*;

public class ChatServerReader extends Thread {

	private int counter;
	private Socket socket;
	private BufferedReader in;
	private Chatter db;
	
	public ChatServerReader(Socket s, BufferedReader i) {
		super("ChatServerReader");
		socket = s;
		in = i;
		counter = 0;
		db = ChatServer.db;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // constructor
	
	public void run() {
		
		String fromClient = "";
		
		while (!socket.isClosed()) {
			try {
				while ((fromClient = in.readLine()) != null)
					System.out.println("CSR " + fromClient);
			} catch (IOException e) {
				System.out.println("Buffer input error. Exiting.");
				e.printStackTrace();
				System.exit(-1);
			} // inner loop
		} // outer loop
		
	} // run
	
} // ChatServerReader