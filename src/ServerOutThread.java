import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class ServerOutThread implements Runnable{

	private ArrayList<Socket> socketList = new ArrayList<Socket>();
	private HashMap<String, Socket> usernames = new HashMap<String, Socket>();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public synchronized Boolean sendMessage(String message, Socket sender, String username){
		for(Socket s: getSockets()){
			if(message == null){
				if(s == sender){
					System.out.println(username + " logged out.");
					removeUsername(username);
					removeSocket(s);
					for(Socket t: getSockets()){
						try {
							PrintWriter pw = new PrintWriter(t.getOutputStream(), true);
							pw.println(username + " logged out.");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				return true;
				}
			} else {
				try {
					PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
					pw.println(username + ": "+message);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return false;
	}  

	
	public synchronized ArrayList<Socket> getSockets(){
		return socketList;
	}
	
	public synchronized void addSocket(Socket socket){
		socketList.add(socket);
	}
	
	public synchronized void removeSocket(Socket socket){
		socketList.remove(socket);
	}
	
	public synchronized boolean containsUsername(String username){
		return usernames.containsKey(username);
	}
	
	public synchronized void addUsername(String username, Socket socket){
		usernames.put(username, socket);
	}
	
	public synchronized void removeUsername(String username){
		usernames.remove(username);
	}

}
