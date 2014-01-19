/**
 * ChatClientReader class by Jonathan Wong
 * @author Jonathan Wong
 * 
 * Serves as a listener thread for ChatClient class.
 * Continually listens to the socket for incoming messages,
 * and prints them to the console.
 */

import java.io.*;
import java.net.*;

public class ChatClientReader extends Thread {

	private Socket socket = null;
	BufferedReader in;

	public ChatClientReader(Socket s) {
		super("ChatClientReader");
		socket = s;

		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // constructor

	public void run() {

		String fromServer = "";

		while (!socket.isClosed()) {
			try {
				while ((fromServer = in.readLine()) != null)
					System.out.println(fromServer);
			} catch (IOException e) {
				System.out.println("Connection closed. Ending program.");
				return;
			}
		} 

	} // run

} // ClientMsgReader