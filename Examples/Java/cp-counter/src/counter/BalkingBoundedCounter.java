package counter;

/**
 * Demonstrates full synchronization with precondition checking.
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: BalkingBoundedCounter.java 24272 2009-01-23 21:17:54Z oscar $
 *
 */
public class BalkingBoundedCounter implements BalkingCounter {
	protected long count = BoundedCounter.MIN; 	// from MIN to MAX
	protected int errors = 0;
	public synchronized long value() { return count; }
	public synchronized void inc() throws BalkingException {
		if (count >= BoundedCounter.MAX) {
			throw new BalkingException("cannot increment");
		}
		++count;
		checkInvariant();
	}
	public synchronized void dec() throws BalkingException {
		if (count <= BoundedCounter.MIN) {
			throw new BalkingException("cannot decrement");
		}
		--count;
		checkInvariant();
	}
	protected void checkInvariant() {
		if (! (count >= BalkingCounter.MIN
				&& count <= BalkingCounter.MAX) ) {
			errors++;
		}
	}
	public int errors() {
		return errors;
	}
}
