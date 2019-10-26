package task3;



/**
 * Class Monitor To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
	/*
	 * ------------ Data members ------------
	 */

	// *** TASK-2 ***
	enum State {
		thinking, hungry, eating, talking;
	};
	
	// *** TASK-3 *** 
	enum PriorityState { 
		hungry, eating, thinking;
	}

	State[] state;
	PriorityState[] priorityState;
	int[] priority;
	int numOfPhil;
	boolean talking;


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers) {
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		state = new State[piNumberOfPhilosophers];
		priorityState = new PriorityState[piNumberOfPhilosophers];
		priority = new int[piNumberOfPhilosophers];
		numOfPhil = piNumberOfPhilosophers;

		for (int i = 0; i < piNumberOfPhilosophers; i++) {
			state[i] = State.thinking;
		}
		
		priority[0] = 3;
		priority[1] = 2;
		priority[2] = 0;
		priority[3] = 5;
	}

	/*
	 * ------------------------------- User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	
	// *** Task-2 *** Implementing pickUp Method
	public synchronized void pickUp(final int piTID) {
	
		int id = piTID - 1;

		state[id] = State.hungry;
		
		// *** Task-2 *** If any neighbors are eating then wait.
		while((state[(id + (numOfPhil - 1)) % numOfPhil] == State.eating)
				|| (state[(id + 1) % numOfPhil] == State.eating)) { 
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		// Checks priority before entering
		enter(id);
		
		// If none of the neighbors are eating then start eating
		state[id] = State.eating;
	}
	
	// ***Task-3 *** This method waits if the priorities doesn't match
	public synchronized void enter(int TID) {
		
		priorityState[TID] = PriorityState.hungry;
		
		while(isHigherPriority(TID) == false) { 
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		priorityState[TID] = PriorityState.eating;
	}
	
	// ***Task-3*** This method exits
	public synchronized void exit(int TID) { 
		priorityState[TID] = PriorityState.thinking;
		notifyAll();
	}
	
	// ***Task-3*** This method checks the priority
	public synchronized boolean isHigherPriority(int TID) { 
		boolean isHigherPriority = true;
		
		for(int i = 0; i < numOfPhil; i++) { 
			
			if(TID == i || priorityState[i] == PriorityState.thinking) { 
				// do nothing
			}
			
			else {
			if(priority[TID] > priority[i]) {
				isHigherPriority = false;
			}
			}
		}
		
		return isHigherPriority;
	}
	
	
	
	

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down and
	 * let others know they are available.
	 */
	
	// *** Task-2 *** Implementing putDown Method
	public synchronized void putDown(final int piTID) {
		
		int id = piTID - 1;
		
		exit(id);

		state[id] = State.thinking;
		
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy (while she is not
	 * eating).
	 */
	
	// *** Task-2 *** Implementing requestTalk Method
	public synchronized void requestTalk(int tid) {

		int id = tid - 1;
		
		// Wait if the philosopher is eating or others are talking
		while((state[id] == State.eating) || (isTalking(id) == true) || talking) { 
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		talking = true;
		state[id] = State.talking;

	}

	// Public boolean isTalking()
	// Checking if somebody is talking or not
	public synchronized boolean isTalking(int id) {
		boolean talking = false;
		int i = id;
		
		for(int j = 0; j < numOfPhil; j++) {
			
			if(i == j) { 
				// do nothing
			}
			
			// Check if others are talking or not
			else { 
				if (state[id] == State.talking) {
					talking = true;
				}
			}
		}

		// Return if they are talking or not
		return talking;
	}

	/**
	 * When one philosopher is done talking stuff, others can feel free to start
	 * talking.
	 */
	
	// *** Task-2 *** Implementing endTalk Method
	public synchronized void endTalk(int id) {
		int i = id-1;
		talking = false;
		state[i] = State.thinking;
		notifyAll();
	}
	
	
	
}

// EOF
