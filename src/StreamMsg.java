import java.io.Serializable;
import java.util.ArrayList;

public class StreamMsg implements Serializable {

	String msg=" Hi, this a msg";
	int NodeId;	
	ArrayList<Integer> neighbors;
}

