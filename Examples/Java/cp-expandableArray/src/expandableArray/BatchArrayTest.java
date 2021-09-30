package expandableArray;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Demonstrates that the updateAll() method is truly atomic.
 * The two threads do not interleave, even though they take
 * some time to complete. One or the other succeeds in setting
 * all values to its unique id.
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: BatchArrayTest.java 24266 2009-01-23 19:49:25Z oscar $
 *
 */

public class BatchArrayTest {
	private BatchArray<Integer> ba;
	private Thread t1, t2;
	private static final int ARRAYSIZE = 20;
	
	public BatchArrayTest() {
		ba = new BatchArray<Integer>();
		for (int i = 1; i <= ARRAYSIZE; ++i) {
			ba.append(i); // all values different
		}
		t1 = mutatorThread(1,ba);
		t2 = mutatorThread(2,ba);
	}
	
	@Test
	public void testNoInterference() {
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			System.err.println("Could not join mutator threads!");
			e.printStackTrace();
		}
		for (int i=0; i<ba.size(); i++) {
			assertEquals(ba.at(1), ba.at(i)); // all values the same
		}
	}

	private Thread mutatorThread(final int id, final BatchArray<Integer> ba) {
		return new Thread() {
			public void run() {
				ba.updateAll(new Mutator<Integer> () {
					public Integer update(Integer x) {
						Thread.yield(); // yielding has no effect
						randomSleep(); 	// makes no difference
						return id; 		// set all values to my id
					}
				});
			}
		};
	}

	public void randomSleep() {
		try {
			Thread.sleep((int)(Math.random() * 100));
		} catch (InterruptedException e) { }
	}
}
