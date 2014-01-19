import java.util.Vector;

public class Chatter extends Thread {
	
	Vector<User> userbase;	
	Vector<String> msgBuffer;
	
	public Chatter () {
		System.out.println("[Server] Chatter thread instantiated.");
		ChatServer.chatter = this;
		msgBuffer = new Vector<String>();
		userbase = new Vector<User>();
	}
	
	public void run() {
		
		System.out.println("[Server] Chatter thread running.");
		while (true)
			if (msgBuffer.size() > 0 && userbase.size() > 0) sendOutMessages();
	} // run
	
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
	
	public synchronized void addUser(User u) {
		userbase.add(u);
		msgBuffer.add("[Server] " + u.name + " has entered the server.");
	} // addUser
	
	public synchronized void addMsg(String msg) {
		msgBuffer.add(msg);
	} // addMsg
	
	public String read(int i) {
		String s = "";
		
		return s;
	} // readMsg

} // ChatManager