package counter;

/**
 * Interface for condition objects that encapsulate
 * guard conditions.
 * 
 * Lecture: Condition Objects
 * 
 * $Id: Condition.java 24407 2009-01-26 20:22:31Z oscar $
 *
 */
public interface Condition { 
	public void await();	// wait for some condition
	public void signal();	// signal that some condition holds
}
