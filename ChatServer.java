/**
 * ChatServer Program by Jonathan Wong [nModZero]
 * @author Jonathan Wong
 * 
 * Server-side code for console-based chatroom software.
 * Designed to correspond with the ChatClient class.
 * Features multithreading for handling multiple clients at once.
 * For demonstration purposes, a hard-coded cap of 10 client
 * connections has been enforced, along with a hard-coded port 9999.
 * 
 * The ChatServerThread is instantiated for each new client connection,
 * while a Chatter thread relays incoming messages from one client to
 * all connected clients.
 */

import java.io.*;
import java.net.*;

public class ChatServer {
	
	String address;
	static int port;
	static Thread t;
	static Chatter chatter;
	static boolean capped;
	static int connections;
	static final int limit = 10;
	
	public static void main (String[] args) {
	
		connections = 0;
		capped = false;
        int portNumber = 9999;
        boolean listening = true;
		System.out.println("Chat Server application initiated.");
		t = new Thread(new Chatter());
		t.start();
         
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
        	
        	System.out.println("IP and port data: " + serverSocket.getInetAddress() + " " + portNumber);
        	
            while (listening) {
            	
            	gateCheck();
            	
            	if (!capped) {
                new ChatServerThread(serverSocket.accept()).start();
            	increment();
            	}
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
	} // main
	
	/**
	 * Checks the number of connections to the ServerSocket, and disables main() from creating new ChatServerThreads when the cap is reached.
	 */
	private static synchronized void gateCheck() {
		if (connections == limit) capped = true;
		else capped = false;
	} // gateCheck
	
	/**
	 * Increments the connections counter. Called upon by ChatServer when a new connection is made.
	 */
	public static synchronized void increment() {
		connections++;
        System.out.println("New connection detected. Connections now at " + connections + ".");
	} // decrement
	
	/**
	 * Decrements the connections counter. Called upon by ChatServerThreads when a client disconnects.
	 */
	public static synchronized void decrement() {
		connections--;
		System.out.println("Closed connection detected. Connections now at " + connections + ".");
	} // decrement
	
} // ChatServer
