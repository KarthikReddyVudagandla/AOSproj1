class Message{
	//int phaseNo;
	ArrayList<int> phaseNeighbors;
};

//This algorithm works in phases
//First send all immediate neighbors the new nodes added in the last phase, then receive the same
//Second send okay to mark end of phase and start new phase only when okay received from all immediate neighbors
class kNeighbor{
	Lock l;
	ArrayList<ArrayList<int>> kHopNeighbors;
	int phase;//current phase
	HashSet<int> nieghborsDiscovered;//hashset to ensure no node is added twice
	int immediateNeighbors;
	int currentPhaseReceived;//variable to keep track of how many immediate neighbors contacted this node
	int okayReceived;//varialbe to keep track of how many immediate neighbors have sent okay
	bool change;//variable to check if any change occurred in current phase
	bool terminate;//varaible to track end of algorithm
	
	kNeighbor(int neighborCount){
		kHopNeighbors = new ArrayList<ArrayList<int>>>();
		phase = 0;
		neighborsDiscovered = new HashSet<int>();
		immediateNeighbors = neighborCount;
		currentPhaseReceived = 0;
		okayReceived = 0;
		change = false;
	}
	
	void start(){
		Message m = new Message();
		m.phaseNeighbors = new ArrayList();
		m.phaseNeighbors.add(nodeid);
		send(m);
	}
	
	//Upon receiving a new message
	void receive(Message m){
		l.lock();
		for(int phaseNeighbor : m.phaseNeighbors){
			//Check if neighbor is already discovered
			if(!neighborsDiscovered.contains(phaseNeighbor)){
				change = true;
				if(kHopNeighbors.size() < phase){
					kHopNeighbors.add(new ArrayList<int>());
					kHopNeighbors[phase].add(phaseNeighbor);
					nieghborsDiscovered.add(phaseNeighbor);
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
	
	void send(Message m){
		for()
	}
	
	void sendOkay(){
		
	}
	
	void receiveOkay(){
		l.lock();
		okayReceived++;
		if(okayReceived == immediateNeighbors){
			Message m = new Message();
			//m.phaseNo = phase;
			m.phaseNeighbors = kHopNeighbors[phase-1];
			send(m);
			okayReceived = 0;
		}
		l.unlock();
	}
};