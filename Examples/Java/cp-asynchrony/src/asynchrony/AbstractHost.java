
package asynchrony;

/**
 * Typical skeleton for asynchronous invocations.
 * 
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: AbstractHost.java 24393 2009-01-26 17:04:57Z oscar $
 *
 */
abstract class AbstractHost implements Host {
	
	public void service() {
		pre();					// code to run before invocation
		invokeHelper();			// the invocation
		during();				// code to run in parallel
		post();					// code to run after completion
	}

	abstract protected void pre();
	abstract protected void invokeHelper();
	abstract protected void during();
	abstract protected void post();
}
