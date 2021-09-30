package slot;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

/**
 * Produce a series of integers and check that they all arrive in order.
 * 
 * Lecture: Safety and Synchronization
 * 
 * $Id: ProducerConsumerTest.java 24344 2009-01-24 21:30:58Z oscar $
 *
 */
public class ProducerConsumerTest {
	static private int COUNT = 50;
	private int total = 0;
	// private int errors = 0;

	@Test
	public void testSlot() { testOrderPreserved(new Slot<Integer>()); }

	// The bad slot causes either producers or consumers to starve
	// due to the race condition -- starvation is not testable
	// Nasty!
	// @Test
	public void testSlotBAD() { testOrderPreserved(new SlotBAD<Integer>()); }

	public void testOrderPreserved(Buffer<Integer> slot) {
		Vector<Thread> threads = new Vector<Thread>();
		threads.add(new Producer<Integer>("Producer", COUNT*2, slot){
			public Integer produce(int n) { return n; }
		});
		threads.add(new Consumer<Integer>("Consumer", COUNT, slot){
			protected void consume(int n, Integer val) { sum(val); }
		});
		threads.add(new Consumer<Integer>("Consumer", COUNT, slot){
			protected void consume(int n, Integer val) { sum(val); }
		});
		for (Thread thread: threads) {  thread.start(); }
		try { for (Thread thread: threads) {  thread.join(); }  }
		catch (InterruptedException e) { }
		// Should be sum of 1..2*COUNT
		assertEquals(COUNT*(2*COUNT+1), total);
	}

	private synchronized void sum(int n) {
		System.out.println(n);
		total += n;
	}

}
