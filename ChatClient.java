/**
 * ChatClient class by Jonathan Wong
 * @author Jonathan Wong
 * 
 * Client-side software for console-based chatroom software.
 * Used in conjunction with ChatServer class.
 * For demonstrations purposes, port 9999 and "localhost" are hard-coded into the program
 * when connecting to the server. Change as necessary.
 * 
 */

import java.io.*;
import java.net.*;

public class ChatClient {

	/**
	 * Main() establishes a connection with the server via Socket class, and begins by
	 * asking for a user name, which will be stored server-side. Once this is established,
	 * this class will continually handle input from the keyboard, while a separate
	 * ChatClientReader listens for incoming messages from the server. Currently, the command
	 * "/exit" will allow a user to close the socket, and end the program.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		Thread listener;
		boolean active = true;
		String hostName = "localhost";
		int port = 9999;
		String userInput = "";

		System.out.println("[Client] ChatClient v1.0 initiated.");
		System.out.println("[Client] Connecting to " + hostName + " " + port);
		try (BufferedReader keyboard = new BufferedReader(
				new InputStreamReader(System.in));
				Socket chatSocket = new Socket(hostName, port);
				PrintWriter out = new PrintWriter(chatSocket.getOutputStream(),
						true);) {

			System.out.println("[Client] Socket connection established.");
			System.out.print("[Client] Please provide a username: ");
			String userName = keyboard.readLine();
			out.println(userName);
			
			listener = new Thread(new ChatClientReader(chatSocket));
			listener.start();

			while (active) {

				userInput = keyboard.readLine();

				if (userInput != null) {
					if (userInput.equals("/exit"))
					{
						System.out.println("[Client] Logging off.");
						active = false;
					}
					out.println(userInput);
				}
			} // while loop
			chatSocket.shutdownInput();
			chatSocket.shutdownOutput();
			chatSocket.close();
		} catch (UnknownHostException e) {
			System.err.println("Error connecting to " + hostName);
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Error retrieving I/O for connection to "
					+ hostName);
			System.exit(-1);
		}
	} // main

} // ChatClient
