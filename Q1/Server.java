
/***
 * Daniel Khalil
 */

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
 
	DataInputStream din;
	DataOutputStream dos;
	Socket s;
	ServerSocket ss;
	
	
	
    public Server(int port)
    {
        try
        {
            System.out.println("Server Setup");
            ss = new ServerSocket(port);
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
    }
    
    public void run() {
    	try {
    		s = ss.accept();
            System.out.println("Connected To Client\n");
            din = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            dos = new DataOutputStream(s.getOutputStream());
            String line = "";
            while (!line.equals("Over"))
            {
                line = din.readUTF();
                String[] params = line.split(",");
                
                System.out.println("Received weight: " + params[0] + " height" + params[1]);
                double bmi_ = bmi( Double.parseDouble(params[0]),Double.parseDouble(params[1]) );
                System.out.println("Sending " + bmi_ + "\n\n");
                dos.writeUTF("Your BMI is: " + bmi_);
            }
            System.out.println("Closing connection");
 
            ss.close();
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
        Server server = new Server(666);
        server.run();
    }
}