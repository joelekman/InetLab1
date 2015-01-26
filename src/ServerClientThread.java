import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

public class ServerClientThread implements Runnable {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String username;
	private ServerOutThread chatRoom;

	public ServerClientThread(Socket socket, ServerOutThread chatRoom) {
		this.socket = socket;
		this.chatRoom = chatRoom;
	}

	public void run(){   
		boolean usernameSet = false;
		boolean exit = false;

		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while(!usernameSet){
			String user = null;
			try {
				user = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (user != null && !chatRoom.containsUsername(user.toLowerCase())){
				chatRoom.addUsername(user.toLowerCase(), socket); //TODO semaphore
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
			String message = null;
			try {
				message = in.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			exit = chatRoom.sendMessage(message, socket, username);
		}

	try {
		out.close();
		in.close();
		socket.close();
	}catch (IOException e){
		e.printStackTrace();
	}
}    
}