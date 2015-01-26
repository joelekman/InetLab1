import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * This class is representing a chat room and handles all the outgoing messages and active users.
 * 
 * @author Joel Ekman
 * @author Tom Johansson
 * 
 * @version 2015-01-26
 *
 */
public class ServerOutThread implements Runnable{

	// List of all the client sockets connected to the chat room
	private ArrayList<Socket> socketList = new ArrayList<Socket>();
	// A hash map with usernames mapped to the user's socket.
	private HashMap<String, Socket> usernames = new HashMap<String, Socket>();
	// Format of the time printed in the chat.
	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Most override because this class implements runnable.
	 */
	@Override
	public void run() {
	}

	/**
	 * This method is synchronized. Sends a message from user (username) to all users in the chat room.
	 * 
	 * @param message the message from the user (username)
	 * @param sender the socket of the sender
	 * @param username the username of the sender
	 * @return exit false if the message is sent otherwise true.
	 */
	public synchronized Boolean sendMessage(String message, Socket sender, String username){
		// Iterates all the sockets of the connected users.
		for(Socket s: socketList){
			// If message is null and s is the sender log out the user.
			if(message == null){
				if(s == sender){
					System.out.println(username + " logged out.");
					usernames.remove(username);
					socketList.remove(s);
					// Send user logged out to all remaining users.
					for(Socket t: socketList){
						try {
							PrintWriter pw = new PrintWriter(t.getOutputStream(), true);
							pw.println(username + " logged out.");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					// If sender is logged out exit.
					return true;
				}
			} else {
				// If the message contains a message send it to the user with socket s.
				try {
					PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
					Date date = new Date();
					pw.println(dateFormat.format(date)+ " "+username + ": "+message);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return false;
	}  

	/**
	 * This method is synchronized. Add a socket to the socket list.
	 * 
	 * @param socket to add.
	 */
	public synchronized void addSocket(Socket socket){
		socketList.add(socket);
	}

	/**
	 * This method is synchronized. Check if a username is in use.
	 *  
	 * @param username
	 * @return true if the username is being used.
	 */
	public synchronized boolean containsUsername(String username){
		return usernames.containsKey(username);
	}

	/**
	 * Add username and socket to the username hashmap.
	 * 
	 * @param username
	 * @param socket
	 */
	public synchronized void addUsername(String username, Socket socket){
		usernames.put(username, socket);
	}
}
