package counter;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

/**
 * We create several busy-waiting incrementor and decrementor
 * threads in an effort to provoke a race condition.
 * Note that the second test is non-deterministic, since
 * we do not actually force the race condition.
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: BalkingBoundedCounterTest.java 24277 2009-01-24 11:07:06Z oscar $
 *
 */
public class BalkingBoundedCounterTest {

	public static final int threadCount = 100; // Need many threads to provoke race condition

	@Test
	public void testBalkingBoundedCounter()
	{
		this.runTests(new BalkingBoundedCounter());
	}

	// We expect this test to fail due to the race condition
	// NB: this test is non-deterministic!
	@Test(expected=java.lang.AssertionError.class)
	public void testBalkingBoundedCounterBAD()
	{
		this.runTests(new BalkingBoundedCounterBAD());
	}

	public void runTests(final BalkingCounter bc) {
		Vector<Thread> threads = new Vector<Thread>();

		for (int i=1; i<=threadCount; i++) {
			threads.add(new BusyWaitingClient() {
				public void action() throws BalkingException { bc.inc(); }
			});
		}

		for (int i=1; i<=threadCount; i++) {
			threads.add(new BusyWaitingClient() {
				public void action() throws BalkingException { bc.dec(); }
			});
		}

		// Wait for all threads to terminate
		try { for (Thread thread: threads) { thread.join(); } }
		catch (InterruptedException err) { }
		assertEquals(0, bc.value());
		assertEquals(0, bc.errors()); // Here we check the race condition
	}

	public abstract class BusyWaitingClient extends Thread {
		BusyWaitingClient() { this.start(); }
		public void run() {
			boolean succeeded = false;
			// busy-wait (potentially starves!)
			while (!succeeded) {
				try {
					action();
					succeeded = true;
				} catch (BalkingException e) {
					Thread.yield();
					// randomSleep();
				}
			}
		}
		abstract void action() throws BalkingException;
	}
}
