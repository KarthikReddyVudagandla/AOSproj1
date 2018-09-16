import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;

//This algorithm works in phases
//First send all immediate neighbors the new nodes added in the last phase, then receive the same
//Second send okay to mark end of phase and start new phase only when okay received from all immediate neighbors
//Terminate messages should only be sent in okay phase
public class kNeighbor implements MsgListener {
	ReentrantLock l;
	Integer nodeid;
	ArrayList<ArrayList<Integer>> kHopNeighbors;
	Integer phase;//current phase
	HashSet<Integer> neighborsDiscovered;//hashset to ensure no node is added twice
	Integer immediateNeighbors;
	volatile Integer currentPhaseReceived;//variable to keep track of how many immediate neighbors contacted this node
	volatile Integer okayReceived;//varialbe to keep track how many immediate neighbors have sent okay
	Integer terminateReceived;//variable to track how many immediate neighbors have terminated
	boolean change;//variable to check if any change occurred in current phase
	volatile boolean toTerminate;
	boolean terminated;//varaible to track end of algorithm
	Broadcaster b;
	
	public kNeighbor(Integer nodeid, Integer neighborCount, Broadcaster broadcaster){
		l = new ReentrantLock();		
		kHopNeighbors = new ArrayList<ArrayList<Integer>>();
		phase = 0;
		this.nodeid = nodeid;
		neighborsDiscovered = new HashSet<Integer>();
		neighborsDiscovered.add(nodeid);
		immediateNeighbors = neighborCount;
		currentPhaseReceived = 0;
		okayReceived = 0;
		terminateReceived = 0;
		change = false;
		b = broadcaster;
		toTerminate = false;//varialbe to track whether algorithm needs to terminate in next okay phase
		terminated = false;
	}
	
	public void start(){
		StreamMsg m = new StreamMsg();
		m.phaseNeighbors = new ArrayList<Integer>();
		m.phaseNeighbors.add(nodeid);
		m.type = MsgType.neighbor;
		this.send(m);
	}
	
	//Upon receiving a new message
	@Override
	public boolean receive(StreamMsg m){
		l.lock();
		if(!terminated)
		{
			if(m.type == MsgType.terminate){
				terminateReceived++;
				toTerminate = true;
			}
			//phase n.1
			if(m.type == MsgType.neighbor){				
				for(Integer phaseNeighbor : m.phaseNeighbors){
					//Check if neighbor is already discovered
					if(!neighborsDiscovered.contains(phaseNeighbor)){
						change = true;
						if(kHopNeighbors.size() < phase + 1){
							kHopNeighbors.add(new ArrayList<Integer>());
						}			
						kHopNeighbors.get(phase).add(phaseNeighbor);
						neighborsDiscovered.add(phaseNeighbor);
					}
				}
		
				currentPhaseReceived++;
			}
					
			//phase n.2
			else if(m.type == MsgType.okay || m.type == MsgType.terminate){
				okayReceived++;
				if(okayReceived == immediateNeighbors){
					StreamMsg m1 = new StreamMsg();
					//m.phaseNo = phase;
					if(!terminated){
						m1.type = MsgType.neighbor;
						m1.phaseNeighbors = kHopNeighbors.get(phase-1);
						System.out.println("Sending neighbors: " + m1.phaseNeighbors);
						send(m1);
					}
					okayReceived = 0;
				}			
			}

			if(currentPhaseReceived + terminateReceived == immediateNeighbors){
				if(!change){
					//Terminate
					toTerminate = true;
				}
				else{
					System.out.println("kHopNeighbors phase " + phase + "; neighbours " + kHopNeighbors.get(phase));
				}
				if(toTerminate && !change){
					sendTerminate();//Terminate message also acts like okay message. It's like okay and terminate
				}
				else{
					sendOkay();					
				}
				phase++;
				currentPhaseReceived = 0;//reset
				change = false;
			}
		}
		l.unlock();
		if(terminated) return false;
		else return true;
	}
	
	void send(StreamMsg m){
		b.broadcast(m);
	}
	
	void sendOkay(){
		System.out.println("Sending Okay");
		StreamMsg okayMessage = new StreamMsg();
		okayMessage.type = MsgType.okay;
		send(okayMessage);
	}
	
	void sendTerminate(){
		terminated = true;
		System.out.println("Sending Terminate");
		StreamMsg terminateMessage = new StreamMsg();
		terminateMessage.type = MsgType.terminate;
		send(terminateMessage);
	}

	@Override
	public boolean isTerminated(){
		return terminated;
	}
};
