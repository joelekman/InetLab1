import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;


/**
 * Setting up the connection to the server and starting the client in and out thread.
 * 
 * @author Joel Ekman
 * @author Tom Johansson
 * 
 * @version 2015-01-26
 *
 */
public class Client {
	// Port to connect to the server
	private static final int CONNECTION_PORT = 8991;
	// IP-address of the server
	private static final String SERVER_ADDRESS = "130.229.155.254";
	// Socket to connect to the server
	private static Socket socket;
	// The users selected username
	private static String username;
	// Semaphore to wait for the server answer when setting up username
	private static Semaphore semaphore = new Semaphore(0);
	// The client GUI
	protected static GUI gui;
	// Thread for incoming communication from the server
	private static Thread clientInThread;
	// Thread for outgoing communication to the server
	private static Thread clientOutThread;

	/**
	 * Main
	 * Set up connection with the server, starting client threads and GUI.
	 * 
	 * @param args (ignore)
	 */
	public static void main(String[] args) {
		try {
			socket = new Socket(SERVER_ADDRESS, CONNECTION_PORT);
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " +SERVER_ADDRESS);
			// Close the client if the server address is wrong.
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't open connection to " + SERVER_ADDRESS);
			// Close the client if a connection can't be made.
			System.exit(1);
		}
		System.out.println("Contacting to chat server ... ");
		// Start the threads and GUI
		clientInThread = new Thread(new ClientInThread(socket, semaphore));
		clientInThread.start();
		clientOutThread = new Thread(new ClientOutThread(socket, semaphore));
		clientOutThread.start();
		gui = new GUI();
		gui.incommingText("Connected to chat.\n");

	}

	/**
	 * Set the username.
	 * 
	 * @param uname (username)
	 */
	public static void setUsername(String uname){
		username = uname;
	}

	/**
	 * Returns the username.
	 * 
	 * @return username
	 */
	public static String getUsername(){
		return username;
	}

	/**
	 * Exit chat and close the socket.
	 */
	public static void closeClient(){
		ClientInThread.exitChat();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}   
