package counter;

/**
 * Abstract superclass for BoundedCounter implementations.
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: BoundedCounter.java 24271 2009-01-23 20:55:50Z oscar $
 *
 */
public abstract class BoundedCounterAbstract implements BoundedCounter {
	protected long count = MIN;
	private int errors = 0;

	protected void checkInvariant() {
		if (! (count >= BoundedCounter.MIN
				&& count <= BoundedCounter.MAX) ) {
			errors++;
		}
	}
	public int errors() {
		return errors;
	}
}
