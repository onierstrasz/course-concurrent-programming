package banking;

/**
 * Helper class representing a thread that keeps transfering 
 * a given amount from an account to another
 *  
 * Lecture: Safety Patterns & Transactional Memory
 * 
 * October 2012
 * Mircea Lungu
 * 
 */

public class Transferer extends Thread {
	Account from, to;
	long delta;
	final long OPERATIONS_COUNT = 1000;
	
	public Transferer (Account from, Account to, long delta) {
		this.from = from;
		this.to = to;
		this.delta = delta;
	}
	
	public void run () {
		for (int i = 0; i < OPERATIONS_COUNT; i++) {
			{
				//System.out.println("transfer from" + from.balance() + " to " + to.balance());
				from.transfer(to, delta);
				Thread.yield();
			}
		}
	}

}
