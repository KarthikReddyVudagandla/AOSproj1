import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;

//class Message{
//	//Integer phaseNo;
//	ArrayList<Integer> phaseNeighbors;
//};

//This algorithm works in phases
//First send all immediate neighbors the new nodes added in the last phase, then receive the same
//Second send okay to mark end of phase and start new phase only when okay received from all immediate neighbors
class kNeighbor{
	Lock l;
	Integer nodeid;
	ArrayList<ArrayList<Integer>> kHopNeighbors;
	Integer phase;//current phase
	HashSet<Integer> neighborsDiscovered;//hashset to ensure no node is added twice
	Integer immediateNeighbors;
	Integer currentPhaseReceived;//variable to keep track of how many immediate neighbors contacted this node
	Integer okayReceived;//varialbe to keep track of how many immediate neighbors have sent okay
	boolean change;//variable to check if any change occurred in current phase
	boolean terminate;//varaible to track end of algorithm
	
	kNeighbor(Integer nodeid, Integer neighborCount){
		kHopNeighbors = new ArrayList<ArrayList<Integer>>();
		phase = 0;
		this.nodeid = nodeid;
		neighborsDiscovered = new HashSet<Integer>();
		immediateNeighbors = neighborCount;
		currentPhaseReceived = 0;
		okayReceived = 0;
		change = false;
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
		for(Integer phaseNeighbor : m.phaseNeighbors){
			//Check if neighbor is already discovered
			if(!neighborsDiscovered.contains(phaseNeighbor)){
				change = true;
				if(kHopNeighbors.size() < phase){
					kHopNeighbors.add(new ArrayList<Integer>());
					kHopNeighbors.get(phase).add(phaseNeighbor);
					neighborsDiscovered.add(phaseNeighbor);
				}			
			}
		}
		
		currentPhaseReceived++;
		if(currentPhaseReceived == immediateNeighbors){
			if(!change){
				//Terminate
			}
			sendOkay();
			phase++;
			currentPhaseReceived = 0;//reset
			change = false;
		}
		l.unlock();
	}
	
	void send(StreamMsg m){
//		for() {}
	}
	
	void sendOkay(){
		
	}
	
	void receiveOkay(){
		l.lock();
		okayReceived++;
		if(okayReceived == immediateNeighbors){
			StreamMsg m = new StreamMsg();
			//m.phaseNo = phase;
			m.phaseNeighbors = kHopNeighbors.get(phase-1);
			send(m);
			okayReceived = 0;
		}
		l.unlock();
	}
};