import java.io.IOException;

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

}
 