package counter;

/**
 * Here we remove synchronization, leading to a potential race condition.
 * If a second thread enters at the point where we invoke Thread.yield(),
 * the class invariant may be violated.
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: BalkingBoundedCounterBAD.java 24271 2009-01-23 20:55:50Z oscar $
 *
 */
public class BalkingBoundedCounterBAD extends BalkingBoundedCounter {
	// Missing synchronization
	public long value() { return count; }
	public void inc() throws BalkingException {
		if (count >= BoundedCounter.MAX) {
			throw new BalkingException("cannot increment");
		}
		Thread.yield(); // try to provoke a race condition
		++count;
		checkInvariant();
	}
	public void dec() throws BalkingException {
		if (count <= BoundedCounter.MIN) {
			throw new BalkingException("cannot decrement");
		}
		Thread.yield(); // try to provoke a race condition
		--count;
		checkInvariant();
	}
}
