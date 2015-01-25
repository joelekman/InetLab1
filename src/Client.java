import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

public class Client {
	private static final int CONNECTION_PORT = 8991;
	private static final String SERVER_ADRESS = "127.0.0.1";
	private static Socket socket;
	private static String username;
	private static Semaphore semaphore = new Semaphore(0);
	protected static GUI gui;
	
	public static void main(String[] args) throws IOException {
		try {
			socket = new Socket(SERVER_ADRESS, CONNECTION_PORT);
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " +SERVER_ADRESS);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't open connection to " + SERVER_ADRESS);
			System.exit(1);
		}
		
		System.out.println("Contacting to chat server ... ");

		new Thread(new ClientInThread(socket, semaphore)).start();
		new Thread(new ClientOutThread(socket, semaphore)).start();
		
		gui = new GUI();
		
		gui.incommingText("Connected to chat.");

		// TODO close the client
	}
	
	public static void setUsername(String uname){
		username = uname;
	}
	
	public static String getUsername(){
		return username;
	}
}   
