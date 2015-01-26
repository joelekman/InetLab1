import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 * 
 * @author Joel Ekman
 * @author Tom Johansson
 * 
 * @version 2015-01-26
 *
 */
public class ClientInThread implements Runnable{
	private BufferedReader in;
	private Semaphore semaphore = null;
	private static Boolean exit; 
	private static Boolean userLoggedIn;
	
	public ClientInThread(Socket socket, Semaphore semaphore){
		this.semaphore = semaphore;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		exit = false;
		userLoggedIn = false;
		
		String usernameSet = null;
		while(!userLoggedIn){
			try {
				usernameSet = in.readLine();
				if(usernameSet == null){
					serverError();
				} else if(usernameSet.equals("true")){
					Client.setUsername(in.readLine());
					userLoggedIn = true;
				} else {
					Client.gui.incommingText(in.readLine());
				}
			} catch (IOException e1) {
				serverError();
			} finally {
				semaphore.release();
			}
		}
		
		while(!exit){
        	try {
        		String message = in.readLine();
        		if(message != null){
        			Client.gui.incommingText(message);
        		} else {
        			serverError();
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
        }
		try {
			System.out.println("User is logged out");
			in.close();
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void serverError(){
		Client.gui.incommingText("Server has failed! Chat program is going to shut down...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userLoggedIn = true;
		exit = true;
	}
	
	public static void exitChat() {
		exit = true;
	}
	
}
