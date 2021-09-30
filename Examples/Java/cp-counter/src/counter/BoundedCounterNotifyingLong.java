package counter;

/**
 * Illustrates delegating notifications to
 * a dedicated helper object.
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: BoundedCounterNotifyingLong.java 24284 2009-01-24 13:29:24Z oscar $
 *
 */
public class BoundedCounterNotifyingLong implements BoundedCounter {
	private NotifyingLong count = new NotifyingLong(this, MIN);

	public synchronized long value() { 
		return count.value(); 
	}
	public synchronized void inc() { 
		while (count.value() >= MAX) {
			try { wait(); }
			catch(InterruptedException ex) {};
		}
		count.setValue(count.value()+1); // will issue notification
		checkInvariant();
	}
	public synchronized void dec() {
		while (count.value() <= MIN) {
			try { wait(); }
			catch(InterruptedException ex) {};
		}
		count.setValue(count.value()-1);
		checkInvariant();
	}

	// We cannot inherit these from BoundedCounterAbstract
	// since count is of a different type.
	private int errors = 0;
	protected void checkInvariant() {
		if (! (count.value() >= BoundedCounter.MIN
				&& count.value() <= BoundedCounter.MAX) ) {
			errors++;
		}
	}
	public int errors() {
		return errors;
	}
}