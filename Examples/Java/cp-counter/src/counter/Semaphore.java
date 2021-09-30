package counter;

/**
 * ...
 * 
 * Lecture: Condition Objects
 * 
 * $Id: Semaphore.java 24278 2009-01-24 11:08:42Z oscar $
 *
 */
public class Semaphore {
	private long value;

	public Semaphore (long initial) {
		value = initial;
	}

	synchronized public void up() {
		++value;
		notify();
	}

	synchronized public void down() {
		while (value== 0) {
			try { wait(); }
			catch(InterruptedException ex) { };
		}
		--value;
	}
}