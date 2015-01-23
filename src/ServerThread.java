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
    
    
    public ServerThread(Socket socket, ArrayList<Socket> socketList) {
        this.socket = socket;
        this.socketList = socketList;
    }
    
    public void run(){   
    	boolean exit = false;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            while(!exit){
	            String message = in.readLine();
	            for(Socket s: socketList){
	            	if (!s.equals(socket)){
	            		PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
	            		pw.println(message);
	            	}
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