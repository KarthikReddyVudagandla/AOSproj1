import java.io.Serializable;
import java.util.ArrayList;
enum MsgType{neighbor,okay,terminate};
public class StreamMsg implements Serializable {

	//String msg=" Hi, this a msg";
	//int NodeId;	
	MsgType type;
	ArrayList<Integer> phaseNeighbors;
	
	//public ArrayList phaseNeighbors;
}

