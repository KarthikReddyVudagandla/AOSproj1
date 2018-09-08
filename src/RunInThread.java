import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class RunInThread {
Socket socket;
NodeInfo NIobj;

public RunInThread(Socket socket,NodeInfo NIobj ) {
	this.socket=socket;
	this.NIobj=NIobj;	
}

public void run() {
	ObjectInputStream ois = null;
	try {
		ois = new ObjectInputStream(socket.getInputStream());
		System.out.println(ois);
		
	} catch (IOException e1) {
		e1.printStackTrace();
	}
  	  		
  	} 
	
	
}



