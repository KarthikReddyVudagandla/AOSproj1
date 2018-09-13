import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class BroadCast implements Broadcaster{
	 ArrayList<Socket> channels;
	
	 BroadCast(ArrayList<Socket> NiObjChannels){
		 channels = NiObjChannels;
	 }
	 @Override
	 public void broadcast(StreamMsg m)
	   {
	   // Loop through Array list 'neighbors' (from Nodeinfo.java file)
	   // Loop through socket neighbour, send message 'm' to socket 's'
	       for (Socket s: channels) {
		 //  System.out.println("channel length is "+NIobj.channels.size());
	            sendMessage( s, m);
	        }
	   }
	 
	// sendMessage - Converts message 'm' to to OutputStream 
     public void sendMessage(Socket s, StreamMsg m)
     {
     // Send Message 'm' through socket 's'
    	 ObjectOutputStream oos=null;
    	 
               try {
                         oos = new ObjectOutputStream(s.getOutputStream());
                         oos.writeObject(m);
                         oos.flush();
                         
                         System.out.println(oos);
                    } 

               catch (IOException e)
                    {
                         System.out.println("cant send this msg");
                         e.printStackTrace();
                    }

     }

}
