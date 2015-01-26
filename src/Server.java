import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

	private final static int CONNECTION_PORT = 8991;
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
		ServerOutThread chatRoom = new ServerOutThread();
		new Thread(chatRoom).start();
		
		while (listening){
			try {
				Socket socket = serverSocket.accept();
				chatRoom.addSocket(socket);
				new Thread(new ServerClientThread(socket, chatRoom)).start();
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
