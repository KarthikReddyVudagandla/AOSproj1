import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class BroadCast implements Broadcaster{
	ArrayList<Socket> channels;
	ArrayList<ObjectOutputStream> oos;
	BroadCast(){
		channels = new ArrayList<Socket>();
		oos = new ArrayList<ObjectOutputStream>();
	}
	

	public void addSocket(Socket s){
		channels.add(s);
		try{
			oos.add(new ObjectOutputStream(s.getOutputStream()));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void broadcast(StreamMsg m)
		{
			// Loop through socket neighbour, send message 'm' to socket 's'
			for (Integer i = 0; i < channels.size(); i++) {
			// System.out.println("channel length is "+NIobj.channels.size());
			//System.out.println("Sending message: " + m.phaseNeighbors);	
	            	sendMessage(i, m);
	       		 }
		}
	 
	// Send Message 'm' through socket 's'
    public void sendMessage(Integer i, StreamMsg m)
    {
		// sendMessage - Converts message 'm' to to OutputStream 
    	 	if(channels.get(i).isClosed()){
			System.out.println("Socket closed");
		}
		try {
			//oos = new ObjectOutputStream(s.getOutputStream());
			oos.get(i).writeObject(m);
			oos.get(i).flush();
			//System.out.println(oos);
		} 

		catch (IOException e)
		{
			//System.out.println("cant send this msg");
			//e.printStackTrace();
			System.out.println("Socket " + i  + " terminated before receiving " + m.type + " message.");
			try{
				channels.get(i).close();
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}
}
