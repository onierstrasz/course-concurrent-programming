package counter;

/**
 * Illustrates encapsulation of assignment.
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: BoundedCounterEncapsulatedAssigns.java 24284 2009-01-24 13:29:24Z oscar $
 *
 */
public class BoundedCounterEncapsulatedAssigns extends BoundedCounterAbstract {
	public synchronized long value() {
		return count;
	}

	public synchronized void inc() {
		awaitIncrementable();
		setCount(count + 1);
		checkInvariant();
	}

	public synchronized void dec() {
		awaitDecrementable();
		setCount(count - 1);
		checkInvariant();
	}

	protected synchronized void awaitIncrementable() {
		while (count >= MAX) {
			try { wait(); }
			catch(InterruptedException ex) { };
		}
	}

	protected synchronized void awaitDecrementable() {
		while (count <= MIN) {
			try { wait(); }
			catch(InterruptedException ex) { };
		}
	}

	protected synchronized void setCount(long newValue) {
		count = newValue;
		notifyAll();
	}
}
