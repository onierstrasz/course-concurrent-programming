package counter;

/**
 * The most basic form of conditional synchronization.
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: BoundedCounterBasic.java 24284 2009-01-24 13:29:24Z oscar $
 *
 */
public class BoundedCounterBasic extends BoundedCounterAbstract {
	public synchronized long value() {
		return count;
	}

	public synchronized void inc() {
		while (count >= MAX) {
			try { wait(); }
			catch(InterruptedException ex) { };
		}
		count ++;
		notifyAll();
		checkInvariant(); // record safety violations
	}

	public synchronized void dec() {
		while (count <= MIN) {
			try { wait(); }
			catch(InterruptedException ex) { };
		}
		count --;
		notifyAll();
		checkInvariant();
	}
}
