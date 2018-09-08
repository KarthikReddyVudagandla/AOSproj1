
import java.util.ArrayList;
import java.util.HashMap;

public class NodeInfo {

	int numOfNodes;
	int[][] adjMtx;	
	int id;
	
	//Mapping between process number as keys and <id,host,port> as value
	HashMap<Integer,Node> nodeInfo;
		
	//ArrayList which holds the total processes(nodes) 
	ArrayList<Node> nodes;
public NodeInfo() {
	nodes = new ArrayList<Node>();	
	nodeInfo = new HashMap<Integer,Node>();
	
}

}
