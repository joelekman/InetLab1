import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Semaphore;


public class ClientInThread implements Runnable{
	private BufferedReader in;
	private Semaphore semaphore = null;
	private static Boolean exit; 
	
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
		String usernameSet = null;
		while(true){
			try {
				usernameSet = in.readLine();
				if(usernameSet.equals("true")){
					Client.setUsername(in.readLine());
					break;
				} else {
					Client.gui.incommingText(in.readLine());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				usernameSet = "false";
				e1.printStackTrace();
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
        			Client.gui.incommingText("Server has failed! Chat program is going to shut down...");
        			Thread.sleep(3000);
        			exit = true;
        		}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block

			}
        }
		try {
			ClientOutThread.out.println(Client.getUsername() +" left the conversation."); // TODO, not working
			System.out.println("User is logged out");
			in.close();
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void exitChat() {
		exit = true;
	}
	
}
