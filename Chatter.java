import java.net.*;
import java.util.HashMap;
import java.util.Vector;
import java.io.*;

public class Chatter extends Thread {
	
	private final int bufferSize = 10;
	private int m, msg_count, index;
	public int head, users;
	Socket[] sockets;
	
	
	Vector<String> messages;
	
	private boolean okToWrite, okToRead;
	private int numReaders, numWriters, numWaitingReaders, numWaitingWriters;
	
	String[] buffer;
	
	public Chatter () {
		System.out.println("[Server]: Database is now running.");
		users = 0;
		head = 0;
		m = bufferSize;
		msg_count = 0;
		buffer = new String[m];
	}
	
	public void run() {
		
	} // run
	
	public synchronized void addUser(Socket s) {

	} // addUser
	
	public synchronized void write(String msg) {
		messages.add(msg);
	} // addMsg
	
	public String read(int i) {
		String s = "";
		
		return s;
	} // readMsg
	
	public synchronized boolean hasNewMessages(int i) {
		boolean b = false;
		if (i != head) b = true;
		return b;
	} // hasNewMessages

} // ChatManager