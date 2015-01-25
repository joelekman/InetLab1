import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

public class Client {
	private static final int CONNECTION_PORT = 8991;
	private static final String SERVER_ADRESS = "127.0.0.1";
	private static Socket socket;
	private static String username;
	private static Semaphore semaphore = new Semaphore(0);
	protected static GUI gui;
	private static Thread clientInThread;
	private static Thread clientOutThread;
	
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
		gui = new GUI();
		gui.incommingText("Connected to chat.\n");

		clientInThread = new Thread(new ClientInThread(socket, semaphore));
		clientInThread.start();
		clientOutThread = new Thread(new ClientOutThread(socket, semaphore));
		clientOutThread.start();
	}
	
	public static void setUsername(String uname){
		username = uname;
	}
	
	public static String getUsername(){
		return username;
	}
	
	public static void closeClient(){
		ClientInThread.exitChat();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}   
