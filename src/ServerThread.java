import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<Socket> socketList;
    private String username;
    
    public ServerThread(Socket socket, ArrayList<Socket> socketList) {
        this.socket = socket;
        this.socketList = socketList;
    }
    
    public void run(){   
    	boolean usernameSet = false;
    	boolean exit = false;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            while(!usernameSet){
            	String user = in.readLine();
            	if (!Server.usernames.containsKey(user.toLowerCase())){
            		Server.usernames.put(user.toLowerCase(), socket); //TODO semaphore
            		username = user;
            		usernameSet = true;
            		out.println("true");
            		out.println(user);
            		System.out.println("User singned in with username: "+username);
            	} else {
            		out.println("false");
            		out.println("Username "+user+" is taken, please select another.");
            	}
            }
            
            while(!exit){
	            String message = in.readLine();
	            for(Socket s: socketList){
//	            	if (!s.equals(socket)){ //TODO Remove
//	            	if(s.){
//	            		System.out.println("lkamfdea");
//	            		if(s == socket){
//	            			System.out.println(username + "logged out.");
//	            			exit = true;
//	            		}
//	            		Server.usernames.remove(username);
//	            		socketList.remove(s);
//	            	} else {
	            	if(message != null){
	            		PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
//	            		try {
	            			pw.println(username + ": "+message);
	            	} else {
//	            		} catch (IOException e) {
	            		if(s == socket){
	            			System.out.println(username + " logged out.");
	            			exit = true;
	            			Server.usernames.remove(username);
	            		}
		            		socketList.remove(s);
	            	}
//	            		}
//	            	}
	            }
            }  
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }    
}