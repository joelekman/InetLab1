import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *  This class handles the clients input to the chat room. One thread for each client.
 * 
 * @author Joel Ekman
 * @author Tom Johansson
 * 
 * @version 2015-01-26
 *
 */
public class ServerClientThread implements Runnable {
	// The socket assigned to a specific client.
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	// The clients username.
	private String username;
	// The chat room the client is presented in.
	private ServerOutThread chatRoom;
	// Boolean variables to exit while loops.
	boolean usernameSet, exit;

	/**
	 * Constructor
	 * Initializing socket, chatRoom, usernameSet, exit, in and out variables.
	 * 
	 * @param socket
	 * @param chatRoom
	 */
	public ServerClientThread(Socket socket, ServerOutThread chatRoom) {
		this.socket = socket;
		this.chatRoom = chatRoom;
		usernameSet = false;
		exit = false;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets username and reads the messages from the user.
	 * 
	 */
	public void run(){   
		// Tries to set username until usernameSet is true.
		while(!usernameSet){
			String user = null;
			try {
				// Read username selected by the user.
				user = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Check that the username isn't null and not already selected.
			if (user != null && !chatRoom.containsUsername(user.toLowerCase())){
				chatRoom.addUsername(user.toLowerCase(), socket);
				username = user;
				usernameSet = true;
				// Returns true to the client if the username is set.
				// This isn't visible for the user.
				out.println("true");
				out.println(user);
				// This message inform the other users in the chat room that a new user has joined the chat.
				chatRoom.sendMessage("Joined the chat!", socket, user);
				// Add the user's socket to the list of active sockets.
				chatRoom.addSocket(socket);
				// Print on the server that a new user has logged in.
				System.out.println("User singned in with username: "+username);
			} else {
				// Sends false to the client if the username is null or already taken.
				// This isn't visible for the user.
				out.println("false");
				// This is an error message to the user.
				out.println("Username "+user+" is taken, please select another.");
			}
		}

		// Reading message form the user until exit is set to true.
		while(!exit){
			String message = null;
			try {
				message = in.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// Sends message and return false if the user's socket is closed.
			exit = chatRoom.sendMessage(message, socket, username);
		}

		// Closes the recourses of the thread before exit the thread.
		try {
			out.close();
			in.close();
			socket.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}    
}