
package bank;

/**
 * Broken account with an unsynchronized withdraw method.
 * A race condition may occur if a context switch
 * occurs at the point where Thread.yield() is called.
 * 
 * Lecture: Safety and Synchronization
 * 
 * $Id: AccountBAD.java 24249 2009-01-23 15:05:26Z oscar $
 * 
 */
public class AccountBAD extends Account {
	public void withdraw(int amount) {
		while (amount > assets) {
			Thread.yield(); // busy wait
		}
		Thread.yield(); // try to provoke a race condition
		assets -= amount;
		checkInvariant(); // invariant may be violated
	}

}
