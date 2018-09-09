import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

  public TCPClient(NodeInfo NIobj,int curNode) {
		for(int i=0;i<NIobj.numOfNodes;i++){
			if(NIobj.adjMtx[curNode][i] == 1){
				System.out.println("connecting nodes "+curNode+"'s client to server of "+i);
				String hostName = NIobj.nodeInfo.get(i).host;
				int port = NIobj.nodeInfo.get(i).port;
				InetAddress address = null;
				try {
					//System.out.println(hostName);
					address = InetAddress.getByName(hostName);
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
					System.exit(1);
				}
				Socket client = null;
				try {
					client = new Socket(address,port);
				} catch (IOException e) {
					System.out.println("Connection Broken");
					e.printStackTrace();
					System.exit(1);
				}
				//Send client request to all neighboring nodes
//				NIobj.channels.put(i, client);
				NIobj.neighbors.add(i);
				ObjectOutputStream oos = null;
				//DataOutputStream dos=null;
				StreamMsg m= new StreamMsg();
				m.msg="Hi, this is a msg";
				m.NodeId= curNode;
				m.neighbors=NIobj.neighbors;
				
				try {
					oos = new ObjectOutputStream(client.getOutputStream());
					oos.writeObject(m);
					oos.flush();
					
					System.out.println(oos);
				} catch (IOException e) {
					System.out.println("cant send this msg");
					e.printStackTrace();
				}
			//	NIobj.oStream.put(i, oos);	
			}
		}	 
	  
  }	
	
}
