import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class handles the setup and new connections to the chat server.
 * 
 * @author Joel Ekman
 * @author Tom Johansson
 * 
 * @version 2015-01-26
 *
 */
public class Server {

	// Connection port
	private final static int CONNECTION_PORT = 8991;
	// True if server is listening for new connections
	private static boolean listening = true;
	// The server socket.
	private static ServerSocket serverSocket;

	/**
	 * Main setting up the chat server and the needed threads.
	 * 
	 * @param args (ignored)
	 */
	public static void main(String[] args) {
		// Trying to set up a new server socket on the specified port.
		try {
			serverSocket = new ServerSocket(CONNECTION_PORT); 
		} catch (IOException e) {
			System.err.println("Error: Could not listen on port: " + CONNECTION_PORT);
			System.exit(1); // Close the application if a connection can't be set up
		}

		System.out.println("Server is started and listening on port: " + CONNECTION_PORT);
		// Initializing a new chat room.
		ServerOutThread chatRoom = new ServerOutThread();
		// Start a new thread for the chat room.
		new Thread(chatRoom).start();

		// Listen for new connections from clients/users.
		while (listening){
			// Trying to set up a new socket for the client/user.
			try {
				Socket socket = serverSocket.accept();
				new Thread(new ServerClientThread(socket, chatRoom)).start();
				System.out.println("User connected on socket "+socket.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Trying to close the server socket. This is dead code and will not happen.
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}        
	}
}
