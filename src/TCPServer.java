import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	ServerSocket listener=null;
	Socket socket=null;
	int ServerPort;
	NodeInfo NIobj;
	
public TCPServer(NodeInfo NIobj) {
	this.NIobj= NIobj;
	
	ServerPort= NIobj.nodes.get(NIobj.id).port;
	
	try {
		listener= new ServerSocket(ServerPort);
	}catch(BindException e) {
		System.out.println("Node " + NIobj.id + " : " + e.getMessage() + ", Port : " + ServerPort);
		System.exit(1);	
	}catch (IOException e) {
		System.out.println(e.getMessage());
		System.exit(1);
	}
	try {
		Thread.sleep(50000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}	
public void listenforinput(){
	//Listen and accept for any client connections
	try {
		while (true) {
			try {
				socket = listener.accept();
			} catch (IOException e1) {
				System.out.println("Connection Broken");
				System.exit(1);
			}
			// For every client request start a new thread 
			//new ReceiveThread(socket,mapObject).start();
		}
	}
	finally {
		try {
			listener.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}	
	
}
