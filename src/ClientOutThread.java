import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


public class ClientOutThread implements Runnable{
	PrintWriter out;
	Semaphore semaphore = null;

	public ClientOutThread(Socket socket, Semaphore semaphore){
		this.semaphore = semaphore;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		System.out.println("Enter username: ");
		Scanner scanner = new Scanner(System.in);
		boolean usernameSet = false;
		while(!usernameSet){
				String username = scanner.nextLine();
				out.println(username);
				try {
					semaphore.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(Client.getUsername() != null) {
					usernameSet = true;
					System.out.println("Welcome "+Client.getUsername()+"!");
				}
		}
		boolean exit = false;
		while(!exit){
			//System.out.print(Client.getUsername() + ": ");
			String message = scanner.nextLine();
			out.println(message);
		}
		scanner.close();
	}
}
