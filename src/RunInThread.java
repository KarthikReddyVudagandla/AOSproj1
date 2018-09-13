import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

public class RunInThread extends Thread {
Socket socket;
NodeInfo NIobj;
kNeighbor kN;

public RunInThread(Socket socket,NodeInfo NIobj, kNeighbor kN) {
	this.socket=socket;
	this.NIobj=NIobj;	
	this.kN = kN;
}

public void run() {
	ObjectInputStream ois = null;
	//DataInputStream dis=null;
	try {
		ois = new ObjectInputStream(socket.getInputStream());
		System.out.println("Msg recvd");		
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	try {
		
		StreamMsg msg;
		msg=(StreamMsg) ois.readObject();
		kN.receive(msg);
//		if(msg.type == MsgType.okay) {
//			System.out.println("okay msg recvd");
//		}
//		else if(msg.type == MsgType.neighbor)
//		{
//			
//			System.out.println("neighbours msg recvd from "+socket.getRemoteSocketAddress().toString() +" "+ msg.phaseNeighbors);
//			
//		}
//		System.out.println(NIobj.id+"says: "+msg.NodeId +" said "+msg.msg +" and");
//		System.out.println(msg.NodeId+ "'s neighbours are "+msg.neighbors);
		
	}catch(StreamCorruptedException e) {
		e.printStackTrace();
		System.exit(2);
	}
	catch (IOException e) {
		e.printStackTrace();
		System.exit(2);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		System.exit(2);
	} 
	
  	  		
  	} 
	
	
}



