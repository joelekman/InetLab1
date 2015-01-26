import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 * Receives incoming data from the server.
 * 
 * @author Joel Ekman
 * @author Tom Johansson
 * 
 * @version 2015-01-26
 *
 */
public class ClientInThread implements Runnable{
	private BufferedReader in;
	private Semaphore semaphore = null;
	// Booleans to exit the while-loops.
	private static Boolean exit, userLoggedIn; 

	/**
	 * Constructor to set up the semaphore, the input stream and loop variables.
	 * 
	 * @param socket
	 * @param semaphore
	 */
	public ClientInThread(Socket socket, Semaphore semaphore){
		this.semaphore = semaphore;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// If input stream can't be established close the client.
			serverError();
		}
		exit = false;
		userLoggedIn = false;
	}

	/**
	 * Assigns username and reads incoming messages.
	 */
	@Override
	public void run() {
		String usernameSet = null;
		// Tries to log in the user
		while(!userLoggedIn){
			try {
				// Reads answer from the server true if username has been set.
				usernameSet = in.readLine();
				// NULL if server error.
				if(usernameSet == null){
					serverError();
				} else if(usernameSet.equals("true")){
					// Set username and exit loop.
					Client.setUsername(in.readLine());
					userLoggedIn = true;
				} else {
					// Print error message for the user.
					Client.gui.incommingText(in.readLine());
				}
			} catch (IOException e1) {
				// Not able to read from the server, server error.
				serverError();
			} finally {
				// Relese the semaphore to the ClientOutThread.
				semaphore.release();
			}
		}

		// Reads message from the server.
		while(!exit){
			try {
				String message = in.readLine();
				if(message != null){
					Client.gui.incommingText(message);
				} else {
					// If message is NULL, connection to the server has been broken.
					serverError();
				}
			} catch (IOException e) {
				// Server error.
				serverError();
			}
		}
		try {
			// Log out user.
			System.out.println("User is logged out");
			in.close();
			// Close the client.
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print error message to user, wait for 3 seconds and the close the client.
	 */
	private void serverError(){
		Client.gui.incommingText("Server has failed! Chat program is going to shut down...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		userLoggedIn = true;
		exit = true;
	}

	/**
	 * Exit chat.
	 */
	public static void exitChat() {
		exit = true;
	}
}
