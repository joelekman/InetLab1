import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 * Sends outgoing data to the server.
 * 
 * @author Joel Ekman
 * @author Tom Johansson
 * 
 * @version 2015-01-26
 *
 */
public class ClientOutThread implements Runnable{
	protected static PrintWriter out;
	Semaphore semaphore = null;

	/**
	 * Constructor to set up the output thread.
	 * 
	 * @param socket
	 * @param semaphore
	 */
	public ClientOutThread(Socket socket, Semaphore semaphore){
		this.semaphore = semaphore;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		Client.gui.incommingText("Enter username. \n");
		boolean usernameSet = false;
		while(!usernameSet){
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(Client.getUsername() != null) {
				usernameSet = true;
				Client.gui.incommingText("Welcome "+Client.getUsername()+"!\n");
			}
		}
	}
}
