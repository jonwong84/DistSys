import java.io.*;
import java.net.*;

public class ChatClient {

	static boolean active;
	static int ID;
	static String userName;
	static Thread listener;

	public static void main(String[] args) throws IOException {

		active = true;
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
			userName = keyboard.readLine();
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
