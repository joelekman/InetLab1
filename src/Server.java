import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

	private final static int CONNECTION_PORT = 8991;
	static ArrayList<Socket> socketList = new ArrayList<Socket>();
	private static boolean listening = true;
	private static ServerSocket serverSocket;
	static HashMap<String, Socket> usernames = new HashMap<String, Socket>();
	
	
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
				addSocket(socket);
				new Thread(new ServerThread(socket)).start();
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
	
	public static synchronized ArrayList<Socket> getSockets(){
		return socketList;
	}
	
	public static synchronized void addSocket(Socket socket){
		socketList.add(socket);
	}
	
	public static synchronized void removeSocket(Socket socket){
		socketList.remove(socket);
	}
	
	public static synchronized boolean containsUsername(String username){
		return usernames.containsKey(username);
	}
	
	public static synchronized void addUsername(String username, Socket socket){
		usernames.put(username, socket);
	}
	
	public static synchronized void removeUsername(String username){
		usernames.remove(username);
	}
}
