package counter;
import java.util.concurrent.Semaphore;

/**
 * Illustrates use of JUC semaphores.
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
public class BoundedCounterJUCSem extends BoundedCounterAbstract {
	protected Semaphore mutex;
	protected Semaphore full;
	protected Semaphore empty;
	
	BoundedCounterJUCSem() {
		mutex = new Semaphore(1);	// one permit for critical section
		full = new Semaphore(0);		// number of counters
		empty = new Semaphore((int)(MAX-MIN)); // number of empty slots
	}
	
	public long value() {
		try {
			mutex.acquire();		// grab the resource
		} catch (InterruptedException e) { }
		long val = count;
		mutex.release();			// release it
		return val;
	}
	
	public void inc() {
		try {
			empty.acquire();		// grab a slot
			mutex.acquire();
		} catch (InterruptedException e) { }
		count ++;
		mutex.release();
		full.release();			// release a counter
		checkInvariant();
	}
	
	public void dec() {
		try {
			full.acquire();		// grab a counter
			mutex.acquire();
		} catch (InterruptedException e) { }
		count --;
		mutex.release();
		empty.release();			// release a slot
		checkInvariant();
	}

}
