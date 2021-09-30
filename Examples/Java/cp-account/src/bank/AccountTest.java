
package bank;

import java.util.Vector;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Here we try to provoke a race condition by having
 * a large number of concurrent deposits and withdrawals.
 * If the bank account drops below 0, then the class invariant
 * is violated.
 * 
 * Lecture: Java and Concurrency
 * 
 * $Id: AccountTest.java 24341 2009-01-24 20:52:58Z oscar $
 *
 */
public class AccountTest {
	private final int deposits = 100;
	// by reducing the number of deposits,
	// the race condition becomes less likely ...

	// This class is properly synchronized, so it should behave correctly
	@Test public void testAccount() {
		testDepositWithdraw(new Account());
	}

	// This Account class is broken, so we expect it to misbehave
	@Test(expected=java.lang.AssertionError.class)
	public void testAccountBAD() {
		testDepositWithdraw(new AccountBAD());
	}

	public void testDepositWithdraw(Account account) {
		assertEquals(0,account.getAssets());
		Vector<Thread> withdrawals = new Vector<Thread>();
		for (int i=1;i<=deposits;i++) {
			withdrawals.add(withdraw(1, account));
		}
		assertEquals(0,account.getAssets());

		for (int i=1;i<=deposits;i++) {
			account.deposit(1);
			Thread.yield();
		}

		for (Thread w: withdrawals) {
			try { w.join(); }
			catch (InterruptedException e) { }
		}
		assertEquals(0,account.getAssets());
		assertEquals(0,account.errors); // check the race condition
	}

	private Thread withdraw(final int amount, final Account account) {
		Thread thread = new Thread() {
			public void run() {
				account.withdraw(amount);
			}
		};
		thread.start();
		return thread;
	}

}
