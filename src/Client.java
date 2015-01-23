import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private static final int CONNECTION_PORT = 8991;
	private static final String SERVER_ADRESS = "127.0.0.1";
	private static Socket socket;
	
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

		new Thread(new ClientInThread(socket)).start();
		new Thread(new ClientOutThread(socket)).start();
		System.out.println("Connected  to chat.");
		
		// TODO close the client
	}
}   
