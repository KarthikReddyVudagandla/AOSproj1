import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws IOException,InterruptedException  {
		// TODO Auto-generated method stub
     NodeInfo NIobj = ReadConfigFile.readConfigFile(Integer.parseInt(args[0]),args[1]);
     
     NIobj.id= Integer.parseInt(args[0]);
     int curNode= NIobj.id;
    
     for(int i=0;i<NIobj.nodes.size();i++){
			NIobj.nodeInfo.put(NIobj.nodes.get(i).nodeId, NIobj.nodes.get(i));
		}
     
     TCPServer server = new TCPServer(NIobj);
     
     new TCPClient(NIobj,curNode);
     
     server.listenforinput();
     
     
	}

     // broadCast methods still needs some work

//   public broadCast(message m)
//   {
//   // Loop through Array list 'neighbors' (from Nodeinfo.java file)
//   // Loop through socket neighbour, send message 'm' to socket 's'
//   for (; ; ) {
//        sendMessage(socket s, message m);
//   }
//   }


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
 