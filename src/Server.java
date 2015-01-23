import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	private final static int CONNECTION_PORT = 8991;
	static ArrayList<Socket> socketList = new ArrayList<Socket>();
	private static boolean listening = true;
	private static ServerSocket serverSocket;
	
	
	public static void main(String[] args) {	
		try {
			serverSocket = new ServerSocket(CONNECTION_PORT); 
		} catch (IOException e) {
			System.err.println("Error: Could not listen on port: " + CONNECTION_PORT);
			System.exit(1); // TODO
		}

		System.out.println("Server is started and listening on port: " + CONNECTION_PORT);
		while (listening){
			try {
				Socket socket = serverSocket.accept();
				socketList.add(socket);
				new Thread(new ServerThread(socket, socketList)).start();
				System.out.println("User connected on socket "+socket.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
	}
}
