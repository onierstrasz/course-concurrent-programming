package counter;

/**
 * Illustrates State Tracking. 
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: BoundedCounterTrackingState.java 24284 2009-01-24 13:29:24Z oscar $
 *
 */
public class BoundedCounterTrackingState extends BoundedCounterAbstract {
	public synchronized long value() {
		return count;
	}

	public synchronized void inc() {
		while (count == MAX) {
			try { wait(); }
			catch(InterruptedException ex) {};
		}
		if (count++ == MIN) {
			notifyAll();	// just left bottom state
		}
		checkInvariant();
	}
	
	public synchronized void dec() {
		while (count == MIN) {
			try { wait(); }
			catch(InterruptedException ex) {};
		}
		if (count-- == MAX)	{
			notifyAll();	// just left top state
		}
		checkInvariant();
	}

}