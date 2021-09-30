package counter;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.*;

/**
 * We create several busy-waiting incrementor and decrementor
 * threads in an effort to provoke a race condition.
 * 
 * This class differs from BalkingBoundedCounterTest only in
 * that clients do not need to busy-wait.
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: BoundedCounterTest.java 24449 2009-01-28 16:28:42Z oscar $
 *
 */
public class BoundedCounterTest {
	public static final int threadCount = 100; // Need many threads to provoke race condition

	// Lecture: Liveness and Guarded Methods
	@Test public void testBasic() { this.runTests(new BoundedCounterBasic()); }
	@Test(expected=java.lang.AssertionError.class)
	public void testNoSyncBAD() { this.runTests(new BoundedCounterNoSyncBAD()); }
	@Test public void testEncapsulatedAssigns() { this.runTests(new BoundedCounterEncapsulatedAssigns()); }
	@Test public void testTrackingState() { this.runTests(new BoundedCounterTrackingState()); }
	@Test public void testStateVariables() { this.runTests(new BoundedCounterStateVariables()); }
	@Test public void testNotifyingLong() { this.runTests(new BoundedCounterNotifyingLong()); }
	
	// Lecture: Condition Objects
	// Nested monitor -- Doesn't terminate
	// @Test public void testNestedMonitorBAD() { this.runTests(new BoundedCounterNestedMonitorBAD()); }
	@Test public void testVCV() { this.runTests(new BoundedCounterCondition()); }
	@Test public void testVSem() { this.runTests(new BoundedCounterSem()); }
	@Test public void testJUCSem() { this.runTests(new BoundedCounterJUCSem()); }
	
	// Lecture: Fairness and Optimism
	@Test public void testVOPT() { this.runTests(new BoundedCounterOptimistic()); }

	public void runTests(final BoundedCounter bc) {
		Vector<Thread> threads = new Vector<Thread>();
		for (int i=1; i<=threadCount; i++) {
			threads.add(new Thread() { public void run() { bc.inc(); } });
		}
		for (int i=1; i<=threadCount; i++) {
			threads.add(new Thread() { public void run() { bc.dec(); } });
		}
		for (Thread thread: threads) { thread.start(); }
		// Wait for all threads to terminate
		try { for (Thread thread: threads) { thread.join(); } }
		catch (InterruptedException err) { }
		assertEquals(0, bc.value());
		assertEquals(0, bc.errors()); // Here we check the race condition
	}

}
