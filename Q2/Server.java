/***
 * Daniel Khalil
 */

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread
{
	DataInputStream din;
	DataOutputStream dos;
	Socket s;
	int index;
 
	public Server(Socket s, int index) {
         try {
	         this.index = index;
	         din = new DataInputStream(new BufferedInputStream(s.getInputStream()));
	         dos = new DataOutputStream(s.getOutputStream());
	     } 
         catch (IOException e) {
	        	 e.printStackTrace();
	     }
	}
	
	@Override
    public void run()
    {
        try
        {
            String line = "";
            while (!line.equals("Over"))
            {
                line = din.readUTF();
                String[] params = line.split(",");
                
                System.out.println("Received weight: " + params[0] + " height " + params[1] + " from socket connection " + index);
                double bmi_ = bmi( Double.parseDouble(params[0]),Double.parseDouble(params[1]) );
                System.out.println("Sending " + bmi_ + " to socket connection " + index + "\n\n");
                dos.writeUTF("Your BMI is: " + bmi_);
            }
            System.out.println("Closing connection");
 
            s.close();
            din.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
    
    private double bmi(double weightInKilograms, double heightInMeters)
    {
    	return weightInKilograms / (heightInMeters * heightInMeters);
    }
 
    public static void main(String args[])
    {
    	
    	ArrayList<Server> serverThreads = new ArrayList<Server>();
    	try {
    		System.out.println("Setting up Server\nstart as many Client threads as you'd like");
    		ServerSocket server = new ServerSocket(666);
    		while(true) {
    			Socket sock = server.accept();
    			System.out.println("Spawning new thread\n");
    			serverThreads.add(new Server(sock, serverThreads.size()));
    			serverThreads.get(serverThreads.size()-1).start();
    		}
    	} catch(Exception e) {
    		
    	}
    }
}