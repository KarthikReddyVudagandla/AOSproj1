
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class NodeInfo {

	int numOfNodes;
	int[][] adjMtx;	
	int id;
	ArrayList<Integer> neighbors;
	
	//Mapping between process number as keys and <id,host,port> as value
	HashMap<Integer,Node> nodeInfo;
	
	// Create all the channels in the beginning and keep it open till the end
		// Mapping between each process as a server and its client connections
		HashMap<Integer,Socket> channels;
		
	//ArrayList which holds the total processes(nodes) 
	ArrayList<Node> nodes;
public NodeInfo() {
	nodes = new ArrayList<Node>();	
	nodeInfo = new HashMap<Integer,Node>();
	neighbors = new ArrayList<>();
}

}
