package counter;

/**
 * Broken version without synchronization.
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: BoundedCounterV0.java 24272 2009-01-23 21:17:54Z oscar $
 *
 */
public class BoundedCounterNoSyncBAD extends BoundedCounterAbstract {
	public long value() {
		return count;
	}

	public void inc() {
		while (count >= MAX) {
			Thread.yield();
			// NB: wait() and notify() are invalid
			// outside synchronized code!
		}
		Thread.yield(); // race condition here
		count ++;
		checkInvariant();
	}

	public void dec() {
		while (count <= MIN) {
			Thread.yield();
		}
		Thread.yield();
		count --;
		checkInvariant();
	}
}
