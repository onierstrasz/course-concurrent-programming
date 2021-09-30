package counter;

/**
 * Illustrates use of semaphores
 * We model the bounded counter as a set of empty slots
 * to be filled with counters.  We have semaphores for
 * the number of empty slots, resp. counters.
 * The sum of the two us always MAX-MIN.
 * There is also a mutex semaphore for the critical section.
 * 
 * Lecture: Condition Objects
 * 
 * $Id: BoundedCounterSem.java 24407 2009-01-26 20:22:31Z oscar $
 *
 */
public class BoundedCounterSem extends BoundedCounterAbstract {
	protected Semaphore mutex;
	protected Semaphore full;
	protected Semaphore empty;
	
	BoundedCounterSem() {
		mutex = new Semaphore(1);	// critical section
		full = new Semaphore(0);		// number of counters
		empty = new Semaphore(MAX-MIN); // number of empty slots
	}
	
	public long value() {
		mutex.down();	// grab the resource
		long val = count;
		mutex.up();		// release it
		return val;
	}
	
	public void inc() {
		empty.down();	// grab a slot
		mutex.down();
		count ++;
		mutex.up();
		full.up();		// release a counter
		checkInvariant();
	}
	
	public void dec() {
		full.down();		// grab a counter
		mutex.down();
		count --;
		mutex.up();
		empty.up();		// release a slot
		checkInvariant();
	}
	
	/*
	 * These would cause a nested monitor problem!
	 */
	public void BADinc() {
		mutex.down();
		empty.down();	// grab a slot
		count ++;
		full.up();		// release a counter
		mutex.up();
		checkInvariant();
	}
	
	public void BADdec() {
		mutex.down();
		full.down();		// grab a counter
		count --;
		empty.up();		// release a slot
		mutex.up();
		checkInvariant();
	}

}
