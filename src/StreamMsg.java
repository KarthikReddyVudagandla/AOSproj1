import java.io.Serializable;
import java.util.ArrayList;
enum MsgType{neighbor,okay,terminate};
public class StreamMsg implements Serializable {
	//int NodeId;	
	MsgType type;
	ArrayList<Integer> phaseNeighbors;

	public StreamMsg(){
		phaseNeighbors = new ArrayList<Integer>();
		type = MsgType.neighbor;
	}
}
