package task4;



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

	State[] state;
	int numOfPhil;
	boolean talking;


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers) {
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		state = new State[piNumberOfPhilosophers];
		numOfPhil = piNumberOfPhilosophers;

		for (int i = 0; i < piNumberOfPhilosophers; i++) {
			state[i] = State.thinking;
		}
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
		
		// If none of the neighbors are eating then start eating
		state[id] = State.eating;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down and
	 * let others know they are available.
	 */
	
	// *** Task-2 *** Implementing putDown Method
	public synchronized void putDown(final int piTID) {
		
		int id = piTID - 1;

		state[id] = State.thinking;
		
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy (while she is not
	 * eating).
	 */
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

	// public boolean isTalking()
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
	public synchronized void endTalk(int id) {
		int i = id-1;
		talking = false;
		state[i] = State.thinking;
		notifyAll();
	}
}

// EOF
