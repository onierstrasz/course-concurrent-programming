package counter;

/**
 * Keep track of the number of permits available.
 * Implementation is similar to BoundedCounter.
 * 
 * Lecture: Condition Objects
 * 
 * $Id: CountCondition.java 24407 2009-01-26 20:22:31Z oscar $
 *
 */
public class Permit implements Condition {
	private int count;

	Permit(int init) {
		count = init;
	}

	public synchronized void await() {
		while (count == 0) {
			try { wait(); }
			catch(InterruptedException ex) { };
		}
		count --;
	}

	public synchronized void signal() {
		count ++;
		notifyAll();
	}
}