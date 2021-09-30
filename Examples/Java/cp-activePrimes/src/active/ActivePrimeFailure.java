package active;

/**
 * This exception is raised if the ActivePrime
 * class invariant is violated.
 * 
 * Lecture: Architectural Styles
 * 
 * $Id: ActivePrimeFailure.java 24330 2009-01-24 17:06:20Z oscar $
 */
@SuppressWarnings("serial")
class ActivePrimeFailure extends Exception {
	public ActivePrimeFailure() { super(); }
	public ActivePrimeFailure(String s) { super(s); }
}
