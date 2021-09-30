package counter;

/**
 * Fixes Nested Monitors. Uses Condition Objects
 * to keep track of abstract state.
 * 
 * Lecture: Condition Objects
 * 
 * $Id: BoundedCounterCondition.java 24406 2009-01-26 19:53:30Z oscar $
 *
 */
public class BoundedCounterCondition extends BoundedCounterAbstract {
	protected Condition notMin_ = new SimpleConditionObject();
	protected Condition notMax_ = new SimpleConditionObject();

	public synchronized long value() { return count; }

	public void dec() {						// not synched!
		boolean wasMax = false;				// record notification condition
		synchronized(notMin_) {				// synch on condition object
			while (true) {					// new guard loop
				synchronized(this) {
					if (count > MIN) {		// check and act
						wasMax = (count == MAX);
						count--;
						break;
					}
				}
				notMin_.await();			// release host sync before wait
			}
		}
		if (wasMax)
			notMax_.signal();				// release all syncs!
		checkInvariant();
	}

	public void inc() {
		boolean wasMin = false;
		synchronized(notMax_) {
			while (true) {
				synchronized(this) {
					if (count < MAX) {
						wasMin = (count == MIN);
						count++;
						break;
					}
				}
				notMax_.await();
			}
		}
		if (wasMin) {
			notMin_.signal();
		}
		checkInvariant();
	}
}