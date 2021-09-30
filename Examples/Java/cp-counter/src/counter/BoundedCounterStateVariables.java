package counter;

/**
 * Illustrates State Variables.
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: BoundedCounterStateVariables.java 24284 2009-01-24 13:29:24Z oscar $
 *
 */
public class BoundedCounterStateVariables extends BoundedCounterAbstract {
	protected enum State { BOTTOM, MIDDLE, TOP };
	protected State state = State.BOTTOM;	// state variable
	
	public synchronized long value() {
		return count;
	}
	
	public synchronized void inc() { 
		while (state == State.TOP) {		// consult logical state
			try { wait(); }
			catch(InterruptedException ex) {};
		}
		++count;					// modify actual state
		checkState();				// sync logical state
		checkInvariant();
	} 

	public synchronized void dec() {
		while (state == State.BOTTOM)	{	// consult logical state
			try { wait(); }
			catch(InterruptedException ex) {};
		}
		--count;					// modify actual state
		checkState();				// sync logical state
		checkInvariant();
	} 

	protected synchronized void checkState() {
		State oldState = state;
		if (count == MIN) { state = State.BOTTOM; }
		else if (count == MAX) { state = State.TOP; }
		else { state = State.MIDDLE; }

		if (leftOldState(oldState)) { notifyAll(); }
	}

	private boolean leftOldState(State oldState) {
		return state != oldState && (oldState == State.TOP || oldState == State.BOTTOM);
	}
}
