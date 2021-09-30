package banking;

/**
 * Naive implementation of the Account in Java
 * Can deadlock with two simultaneous and inverse transfers
 * 
 * Lecture: Safety Patterns & Transactional Memory
 * 
 * October 2012
 * Mircea Lungu
 * 
 */

public class Account {
	
	float balance;
	
	public Account () {}
	public Account (long balance) {
		this.balance = balance;
	}
	
	public void deposit (long amount) {
		balance += amount;
	}
	
	synchronized void withdraw (long amount) {
		if (balance < amount)
			throw new Error("Out of money");
		balance -= amount;
	}
	
	// If not synchronized, race condition
	// If synchronized, deadlock
	void transfer (Account another, long amount) {
		//another.print("before withdrawing from another...");
		another.withdraw (amount);
		//another.print("after withdrawing from another...");
		Thread.yield();

		//print("before depositing in this...");
		deposit(amount);
		//print("after depositing in this...");

	}
	
	void print(String message) {
		System.out.println(message + balance());
	}
	
	long balance () {
		return (long)balance;
	}
	

}
