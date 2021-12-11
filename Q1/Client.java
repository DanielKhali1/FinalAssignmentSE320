/***
 * Daniel Khalil
 */

import java.net.*;
import java.util.Scanner;
import java.io.*;
 
public class Client
{
 
	Socket s;
	DataInputStream din;
	DataOutputStream dos;
	
    public Client(String address, int port)
    {
        try
        {
            s = new Socket(address, port);
            din  = new DataInputStream(s.getInputStream());
            dos    = new DataOutputStream(s.getOutputStream());
            System.out.println("Connected to Server");
        }
        catch(UnknownHostException u)
        {
        	System.out.println("Host Connection Failed...");
        	return;
        }
        catch(IOException i)
        {
        	System.out.println("Host Connection Failed...");
        	return;        
        }
    }
    
    public void run() {
    	
        Scanner bmiIn = new Scanner(System.in);
        String line = "";
        
        
        while (!line.equals("Over"))
        {
            try
            {
            	System.out.println("Enter Weight(kilo) and Height(meters) format: [weight,height]:");
            	dos.writeUTF(bmiIn.next());
            	System.out.println(din.readUTF()+ "\n\n");
            }
            catch(IOException i)
            {
            	System.out.println("IO Exception... Ending");
                break;
            }
        }
        closeStreams();
    }
 
    private void closeStreams() {
        try
        {
            din.close();
            dos.close();
            s.close();
        }
        catch(IOException i)
        {
            System.out.println("Could not close streams");
        }		
	}

	public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 666);
        client.run();
    }
}