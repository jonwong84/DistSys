/**
 * Chatter class by Jonathan Wong
 * @author Jonathan Wong
 * 
 * Responsible for delivering all received messages to their intended recipients.
 */

import java.util.Vector;

public class Chatter extends Thread {

	Vector<User> userbase;
	Vector<String> msgBuffer;

	public Chatter() {
		System.out.println("[Server] Chatter thread instantiated.");
		ChatServer.chatter = this;
		msgBuffer = new Vector<String>();
		userbase = new Vector<User>();
	} // constructor

	/**
	 * A while-true loop sends out all stored messages to their recipients,
	 * given that there are messages present, and a nonempty list of connected
	 * clients.
	 */
	public void run() {

		System.out.println("[Server] Chatter thread running.");
		while (true)
			if (msgBuffer.size() > 0 && userbase.size() > 0)
				sendOutMessages();
	} // run

	/**
	 * For each message in the buffer, Chatter thread outputs the String to each
	 * registered recipient. Messages are removed once it's sent out.
	 */
	private synchronized void sendOutMessages() {

		User u = null;
		while (msgBuffer.size() > 0) {
			for (int i = 0; i < userbase.size(); i++) {
				u = userbase.elementAt(i);
				u.sendMsg(msgBuffer.elementAt(0));
			}
			msgBuffer.removeElementAt(0);
		}
	} // sendOutMessages

	/**
	 * Adds a new user to a vector.
	 * @param u
	 */
	public synchronized void addUser(User u) {
		userbase.add(u);
		msgBuffer.add("[Server] " + u.name + " has entered the server.");
	} // addUser

	/**
	 * Adds a new String message to the buffer.
	 * @param msg
	 */
	public synchronized void addMsg(String msg) {
		msgBuffer.add(msg);
	} // addMsg

} // ChatManager