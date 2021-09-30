package banking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Set of tests to illustrate the use of STM. In this particular case, we are
 * using the multiverse STM for Java.
 * 
 * Lecture: Safety Patterns & Transactional Memory
 * 
 * October 2012
 * Mircea Lungu
 * 
 */

public class Demo_Scenarios  {
	
	@Test
	public void testAccountDepositsWithTM() {
		Account common = new AccountTM (1000);
		common.print("at the beginning...");
		transactions_cancelling_each_other(common);
		common.print("at the end...");
		assertEquals(common.balance(), 1000);
	}
	
	/*
	 * This test will end with an exception when the Account
	 * is not synchronized since we will have race conditions
	 */
	@Test(expected=java.lang.AssertionError.class)
	public void testAccountDeposits() {
		Account common = new Account (1000);
		common.print("at the beginning...");
		transactions_cancelling_each_other(common);
		common.print("at the end...");
		assertEquals(common.balance(), 1000);
	}
	
	
	/*
	 * This test will deadlock when Account>>transfer is 
	 * synchronized
	 */
	@Test
	public void testMutualTransfers() {
		Account one = new Account (10000);
		Account two = new Account (10000);
		
		one.print("one: at the beginning...");
		two.print("two: at the beginning...");
		mutual_transfers(one, two);
		one.print("one: at the end...");
		two.print("two: at the end...");
	}

	@Test
	public void testMutualTransfersTM() {
		Account one = new AccountTM (10000);
		Account two = new AccountTM (10000);
		
		one.print("one: at the beginning...");
		two.print("two: at the beginning...");
		mutual_transfers(one, two);
		one.print("one: at the end...");
		two.print("two: at the end...");
	}

	/* 
	 * Very simple scenario. If  one of the transactioners adds 
	 * 1000 times 1 and the other removes 1000 times 1 then 
	 * eventually we'll end up with the account having the initial
	 * amount since all the deposits and widthdrawals will have cancelled
	 * each other
	 */
	public void transactions_cancelling_each_other(Account common) {
		
		Depositer t1 = new Depositer(common, 1);
		Depositer t2 = new Depositer(common, -1);
		t1.start();
		t2.start();
		
		try 
			{t1.join();
			t2.join();} 
		catch (InterruptedException e) 
			{e.printStackTrace();}
	}
	
	public void mutual_transfers (Account one, Account two) {
		Transferer t1 = new Transferer (two, one, 1);
		Transferer t2 = new Transferer (one, two, 1);
		t1.start();
		t2.start();

		try 
		{t1.join();
		t2.join();
		} 
	catch (InterruptedException e) 
		{e.printStackTrace();}
	}
}
