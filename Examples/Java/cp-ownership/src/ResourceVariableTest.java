
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * We create two threads, each with their own unsynchronized
 * integer variable.  Each thread repeatedly swaps its variable
 * with a third one held by the ResourceVariable ownership
 * transfer object.  The threads simply increment and decrement
 * the int var by their seed value.  If there is no interference,
 * then the value should be zero after each loop iteration.
 * We count the errors.
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: ResourceVariableTest.java 24262 2009-01-23 19:13:01Z oscar $
 *
 */
public class ResourceVariableTest {
	private IntVar var = new IntVar();
	private int errors = 0;
	private final int SEED1 = 5;
	private final int SEED2 = 17;
	private final int LOOP = 50; // adjust to provoke the race condition

	// NB: unsynchronized
	private class IntVar {
		private int value = 0; 
		void put(int value) { this.value = value; }
		int get() { return value; }
	}

	@Test
	public void testResourceVariable() {
		testSafety(new ResourceVariable<IntVar>(var));
	}

	// We expect an error due to the race condition
	// NB: might fail -- adjust LOOP value to provoke race
	@Test(expected=java.lang.AssertionError.class)
	public void testResourceVariableBAD() {
		testSafety(new ResourceVariableBAD<IntVar>(var));
	}
	
	private void testSafety(ResourceVariable<IntVar> rv) {
		Tinker t1 = new Tinker(SEED1, rv);
		Tinker t2 = new Tinker(SEED2, rv);
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException err) { }
		assertEquals(0, errors);
	}

	private class Tinker extends Thread {
		private int seed;
		private ResourceVariable<IntVar> rv;
		private IntVar var;
		Tinker(int seed, ResourceVariable<IntVar> rv) {
			this.seed = seed;
			this.rv = rv;
			var = new IntVar();
			this.start();
		}
		public void run() {
			for (int i=1; i<=LOOP; i++) {
				var = rv.exchange(var);		// swap vars
				var.put(var.get() + seed);	// increment
				Thread.yield();				// provoke a race condition
				var.put(var.get() - seed);	// decrement
				if (var.get() != 0) {		// check for interference
					incError();
				}
				var = rv.exchange(var);		// swap vars back
			}
		}
	}

	public synchronized void incError() {
		errors++;
	}
}
