package counter;

/**
 * A simple class to encapsulate guard conditions.
 * 
 * Lecture: Condition Objects
 * 
 * $Id: SimpleConditionObject.java 24407 2009-01-26 20:22:31Z oscar $
 *
 */
public class SimpleConditionObject implements Condition { 
	public synchronized void await() {
		try { wait(); }
		catch (InterruptedException ex) {}
	}
	public synchronized void signal() { 
		notifyAll(); 
	}
}