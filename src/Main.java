import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Main{
	 static NodeInfo NIobj;
	public static void main(String[] args) throws IOException,InterruptedException  {
		// TODO Auto-generated method stub
      NIobj = ReadConfigFile.readConfigFile(Integer.parseInt(args[0]),args[1]);
     
     NIobj.id= Integer.parseInt(args[0]);
     int curNode= NIobj.id;
    
     for(int i=0;i<NIobj.nodes.size();i++){
			NIobj.nodeInfo.put(NIobj.nodes.get(i).nodeId, NIobj.nodes.get(i));
		}
     
     TCPServer server = new TCPServer(NIobj);
     
     new TCPClient(NIobj,curNode);
     
     
     StreamMsg m=new StreamMsg();
     m.type=MsgType.neighbor;
     m.phaseNeighbors= new ArrayList<Integer>();
     m.phaseNeighbors.add(NIobj.id);
     
   
    
     
     server.listenforinput();
     
     while (NIobj.ClientConnectionCount[NIobj.id]!=NIobj.channels.size());
     BroadCast b= new BroadCast(NIobj.channels);
     //b.broadcast(m);
    
     kNeighbor kn=new kNeighbor(NIobj.id,NIobj.ClientConnectionCount[NIobj.id],b);
     kn.start();
     
	}

     // broadCast methods still needs some work

  





}
 