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
	private String username;

	public ServerThread(Socket socket) {
		this.socket = socket;
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
			if (user != null && !Server.containsUsername(user.toLowerCase())){
				Server.addUsername(user.toLowerCase(), socket); //TODO semaphore
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
			for(Socket s: Server.getSockets()){
				if(message == null){
					if(s == socket){// TODO körs inte? 
						System.out.println(username + " logged out.");
						exit = true;
						Server.removeUsername(username);
						Server.removeSocket(s);

						for(Socket t: Server.getSockets()){
							try {
								PrintWriter pw = new PrintWriter(t.getOutputStream(), true);
								pw.println(username + " logged out.");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
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