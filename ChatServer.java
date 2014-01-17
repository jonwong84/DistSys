import java.io.*;
import java.net.*;

public class ChatServer {
	
	String address;
	static int port;
	static Chatter db;
	static boolean capped;
	static int connections;
	static final int limit = 10;
	
	public static void main (String[] args) {
	
		connections = 0;
		capped = false;
        int portNumber = 9999;
        boolean listening = true;
		System.out.println("Chat Server application initiated.");
		db = new Chatter();
		db.run();
         
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
            	
            	gateCheck();
            	
            	if (!capped) {
                new ChatServerThread(serverSocket.accept()).start();
            	connections++;
                System.out.println("New connection detected. Connections now at " + connections + ".");
            	}
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
	} // main
	
	private static synchronized void gateCheck() {
		if (connections == limit) capped = true;
		else capped = false;
	} // gateCheck
	
	public static synchronized void decrement() {
		connections--;
		System.out.println("Decremented to " + connections + ".");
	} // decrement
	
} // ChatServer
