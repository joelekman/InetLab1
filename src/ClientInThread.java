import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Semaphore;


public class ClientInThread implements Runnable{
	BufferedReader in;
	Semaphore semaphore = null;
	
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
		Boolean exit = false;
		String usernameSet = null;
		while(true){
			try {
				usernameSet = in.readLine();
				if(usernameSet.equals("true")){
					Client.setUsername(in.readLine());
					break;
				} else {
					System.out.println(in.readLine());
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
				System.out.print("\r"+in.readLine()+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
	}

}
