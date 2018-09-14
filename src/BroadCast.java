import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class BroadCast implements Broadcaster{
	ArrayList<Socket> channels;
	
	BroadCast(){
		channels = new ArrayList<Socket>();
	}
	

	public void addSocket(Socket s){
		channels.add(s);
	}
	
	@Override
	public void broadcast(StreamMsg m)
		{
			// Loop through socket neighbour, send message 'm' to socket 's'
			for (Socket s: channels) {
			// System.out.println("channel length is "+NIobj.channels.size());
	            sendMessage(s, m);
	        }
		}
	 
	// Send Message 'm' through socket 's'
    public void sendMessage(Socket s, StreamMsg m)
    {
		// sendMessage - Converts message 'm' to to OutputStream 
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
