package counter;

/**
 * Illustrates Nested Monitors
 * 
 * Lecture: Condition Objects
 * 
 * $Id: BoundedCounterNestedMonitorBAD.java 24406 2009-01-26 19:53:30Z oscar $
 *
 */
public class BoundedCounterNestedMonitorBAD extends BoundedCounterAbstract {
	protected Condition notMin_ = new SimpleConditionObject();
	protected Condition notMax_ = new SimpleConditionObject();

	public synchronized long value() { return count; }

	public synchronized void dec() { 
		while (count == MIN)
			notMin_.await();					// wait till count not MIN
		if (count-- == MAX)
			notMax_.signal();
		checkInvariant();
	}
	public synchronized void inc() {			// can't get in!
		while (count == MAX)
			notMax_.await();
		if (count++ == MIN)
			notMin_.signal();					// we never get here!
		checkInvariant();
	}

}