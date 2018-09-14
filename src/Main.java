import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Main{
	static NodeInfo NIobj;
	
	public static void main(String[] args) throws IOException,InterruptedException {
		NIobj = ReadConfigFile.readConfigFile(Integer.parseInt(args[0]),args[1]);	
		NIobj.id= Integer.parseInt(args[0]);
		
		BroadCast b= new BroadCast();
		kNeighbor kn=new kNeighbor(NIobj.id,NIobj.ClientConnectionCount[NIobj.id],b);
		
		for(int i=0;i<NIobj.nodes.size();i++){
			NIobj.nodeInfo.put(NIobj.nodes.get(i).nodeId, NIobj.nodes.get(i));
		}
		
		TCPServer server = new TCPServer(NIobj);		
		TCPClient client = new TCPClient(NIobj, NIobj.id, kn);
		server.listenforinput(kn);
		
		while (NIobj.ClientConnectionCount[NIobj.id] != NIobj.channels.size());
		
		for(Socket socket : NIobj.channels){
			broadCast.addSocket(socket);
		}
		
		kn.start();
	}

}