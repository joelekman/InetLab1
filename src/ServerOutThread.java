import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ServerOutThread implements Runnable{

	private ArrayList<Socket> socketList = new ArrayList<Socket>();
	private HashMap<String, Socket> usernames = new HashMap<String, Socket>();
	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Override
	public void run() {

		
	}
	
	public synchronized Boolean sendMessage(String message, Socket sender, String username){
		for(Socket s: socketList){
			if(message == null){
				if(s == sender){
					System.out.println(username + " logged out.");
					usernames.remove(username);
					socketList.remove(s);
					for(Socket t: socketList){
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
					Date date = new Date();
					pw.println(dateFormat.format(date)+ " "+username + ": "+message);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return false;
	}  
	
	public synchronized void addSocket(Socket socket){
		socketList.add(socket);
	}
	
	public synchronized boolean containsUsername(String username){
		return usernames.containsKey(username);
	}
	
	public synchronized void addUsername(String username, Socket socket){
		usernames.put(username, socket);
	}
}
