import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;

public interface Broadcaster{
	void broadcast(StreamMsg m);
}

//This algorithm works in phases
//First send all immediate neighbors the new nodes added in the last phase, then receive the same
//Second send okay to mark end of phase and start new phase only when okay received from all immediate neighbors
public class kNeighbor{
	Lock l;
	Integer nodeid;
	ArrayList<ArrayList<Integer>> kHopNeighbors;
	Integer phase;//current phase
	HashSet<Integer> neighborsDiscovered;//hashset to ensure no node is added twice
	Integer immediateNeighbors;
	Integer currentPhaseReceived;//variable to keep track of how many immediate neighbors contacted this node
	Integer okayReceived;//varialbe to keep track of how many immediate neighbors have sent okay
	boolean change;//variable to check if any change occurred in current phase
	boolean toTerminate;//varaible to track end of algorithm
	Broadcaster b;
	
	kNeighbor(Integer nodeid, Integer neighborCount, Broadcaster broadcaster){
		kHopNeighbors = new ArrayList<ArrayList<Integer>>();
		phase = 0;
		this.nodeid = nodeid;
		neighborsDiscovered = new HashSet<Integer>();
		immediateNeighbors = neighborCount;
		currentPhaseReceived = 0;
		okayReceived = 0;
		change = false;
		b = broadcaster;
		toTerminate = false;
	}
	
	void start(){
		StreamMsg m = new StreamMsg();
		m.phaseNeighbors = new ArrayList();
		m.phaseNeighbors.add(nodeid);
		this.send(m);
	}
	
	//Upon receiving a new message
	void receive(StreamMsg m){
		l.lock();
		if(m.type == neighbor){				
			for(Integer phaseNeighbor : m.phaseNeighbors){
				//Check if neighbor is already discovered
				if(!neighborsDiscovered.contains(phaseNeighbor)){
					change = true;
					if(kHopNeighbors.size() < phase){
						kHopNeighbors.add(new ArrayList<Integer>());
					}			
					kHopNeighbors.get(phase).add(phaseNeighbor);
					neighborsDiscovered.add(phaseNeighbor);
				}
			}
			
			currentPhaseReceived++;
			if(currentPhaseReceived == immediateNeighbors){
				if(!change){
					//Terminate
					terminate();
				}
				StreamMsg m = new StreamMsg();
				m.type = okay;
				send(m);
				phase++;
				currentPhaseReceived = 0;//reset
				change = false;
			}
		}
		else if(m.type == okay){
			okayReceived++;
			if(okayReceived == immediateNeighbors){
				StreamMsg m = new StreamMsg();
				//m.phaseNo = phase;
				m.type = neighbor;
				m.phaseNeighbors = kHopNeighbors.get(phase-1);
				send(m);
				okayReceived = 0;
			}			
		}
		l.unlock();
	}
	
	void send(StreamMsg m){
		b.broadcast(m);
	}
	
	void terminate(){
		toTerminate = true;
	}
};