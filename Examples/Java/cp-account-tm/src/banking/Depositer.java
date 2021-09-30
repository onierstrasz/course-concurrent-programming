package banking;

/**
 * Helper class representing a thread that keeps depositing 
 * a given amount to the specified account for a given number 
 * of times
 *  
 * Lecture: Safety Patterns & Transactional Memory
 * 
 * October 2012
 * Mircea Lungu
 * 
 */

public class Depositer extends Thread {
	Account account;
	long delta;
	final long OPERATIONS_COUNT = 1000000;
	
	public Depositer (Account a, long delta) {
		account = a;
		this.delta = delta;
	}
	
	public void run () {
		for (int i = 0; i < OPERATIONS_COUNT; i++) {
			{
				//System.out.println("depositing "+ delta + " into " + account.balance());
				account.deposit(delta);
			}
		}
	}

}
