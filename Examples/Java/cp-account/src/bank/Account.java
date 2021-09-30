
package bank;

/**
 * Illustrates wait() and notify()
 * 
 * Lecture: Java and Concurrency
 * 
 * $Id: Account.java 24250 2009-01-23 15:17:00Z oscar $
 * 
 */
public class Account {
	protected int assets = 0;
	protected int errors = 0; // count safety violations
	
	public int getAssets() {
		return assets;
	}
	
	public synchronized void deposit(int amount) {
		assets += amount;
		notifyAll();
		checkInvariant();
	}
	
	public synchronized void withdraw(int amount) {
		while (amount > assets) {
			try {
				wait();
			} catch(InterruptedException e) { }
		}
		assets -= amount;
		checkInvariant();
	}
	
	protected void checkInvariant() {
		if (!(assets >= 0)) {
			errors++;
		}
	}
	
	public int errors() {
		return errors;
	}
}
