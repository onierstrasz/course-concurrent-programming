package counter;

/**
 * Common interface for BoundedCounter implementations
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: BoundedCounter.java 24280 2009-01-24 12:20:44Z oscar $
 *
 */
public interface BoundedCounter {
	public static final long MIN = 0;	// min value
	public static final long MAX = 5;	// max value
	public long value();	// inv't: MIN <= value() <= MAX
							// init: value() == MIN
	public void inc();		// pre: value() < MAX
	public void dec();		// pre: value() > MIN
	public int errors();	// count safety violations (for tests)
}