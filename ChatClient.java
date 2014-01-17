import java.io.*;
import java.net.*;

public class ChatClient {

	static boolean active;
	static int ID;
	
	public static void main(String[] args) throws IOException {

		active = true;
		String hostName = "localhost";
		int port = 9999;
		String msg = "";
		boolean handshake = true;
		String userInput;
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("[Client]: ChatClient v1.0 initiated.");
		System.out.println("[Client]: Connecting to " + hostName + " " + port);
		try (
				Socket chatSocket = new Socket(hostName, port);
				PrintWriter out = new PrintWriter(chatSocket.getOutputStream(), true);
				) {

			System.out.print("[Client]: Please input your username: ");
			userInput = keyboard.readLine();
			System.out.println("Keyboard = " + userInput);
			out.println(userInput);
			new ChatClientReader(chatSocket).run();

			while (active) {
				
				System.out.println(">");
				userInput = keyboard.readLine();
				if (userInput != null)
					out.println(userInput);
				if (userInput.equals("/exit")); {
					System.out.println("Logging off.");
					break;
				}
			} // while loop
			
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
