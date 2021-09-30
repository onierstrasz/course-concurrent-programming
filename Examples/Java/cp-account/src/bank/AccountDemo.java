package bank;

/**
 * Simple demo of monitor behaviour.
 * The first thread is forced to wait until the second adds enough money.
 * 
 * Lecture: Java and Concurrency
 * 
 * $Id: AccountDemo.java 24248 2009-01-23 15:04:03Z oscar $
 *
 */
public class AccountDemo {

	public static void main(String[] args) {
		final Account account = new Account();
		new Thread() {
			public void run() {
				int amount = 100;
				System.out.println("Waiting to withdraw " + amount + " units ...");
				account.withdraw(amount);
				System.out.println("I withdrew " + amount + " units!");
			}
		}.start();
		try { Thread.sleep(1000); }
		catch (InterruptedException e){ }
		new Thread() {
			public void run() {
				int amount = 200;
				System.out.println("Depositing " + amount + " units ...");
				account.deposit(amount);
				System.out.println("I deposited " + amount + " units!");
			}
		}.start();
	}

}
