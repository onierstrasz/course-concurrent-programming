package counter;

/**
 * Illustrates Optimistic updates.
 * 
 * Lecture: Fairness and Optimism
 * 
 * $Id: BoundedCounterOptimistic.java 24449 2009-01-28 16:28:42Z oscar $
 *
 */
public class BoundedCounterOptimistic extends BoundedCounterAbstract {

	protected synchronized boolean commit(Long oldc, Long newc) {
		boolean success = (count == oldc);
		if (success) {
			count = newc;
		} else {
			System.err.println("COMMIT FAILED -- RETRYING");
		}
		return success;
	}

	public synchronized long value() {
		return count;
	}
	public void inc() { 
		for (;;) {						// thinly disguised busy-wait!
			long prev = this.value();
			long val = prev;
			if (val < MAX && commit(prev, val+1)) {
				break;
			}
			Thread.yield();				// is there another thread?!
		}
	}
	public void dec() { 
		for (;;) {
			long prev = this.value();
			long val = prev;
			if (val > MIN && commit(prev, val-1)) {
				break;
			}
			Thread.yield();
		}
	}
}
