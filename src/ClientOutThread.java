import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientOutThread implements Runnable{
	PrintWriter out;

	public ClientOutThread(Socket socket){
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
				if(Client.getUsername() != null) {
					usernameSet = true;
				}
		}
		boolean exit = false;
		while(!exit){
			String message = scanner.nextLine();
			System.out.println("this is what the scanner:"+message);//TODO remove
			out.println(message);
		}
		scanner.close();
	}
}
