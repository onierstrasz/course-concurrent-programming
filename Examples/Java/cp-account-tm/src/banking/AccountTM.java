package banking;


/**
 * Illustrates the use of STM. In this particular case, we are
 * using the multiverse STM for Java.
 * 
 * Lecture: Safety Patterns & Transactional Memory
 * 
 * October 2012
 * Mircea Lungu
 * 
 */

import org.multiverse.api.references.TxnLong;
import org.multiverse.stms.gamma.transactionalobjects.GammaTxnLong;
import static org.multiverse.api.StmUtils.*;


public class AccountTM extends Account {
	
	TxnLong balance;
	
	public AccountTM (long balance) {
		this.balance = new GammaTxnLong (balance);
	}
	
	public void deposit (final long amount) {
		atomic(new Runnable () { public void run () {
				balance.incrementAndGet(amount);
			}});
	}
	
	void withdraw (final long amount) {
		atomic(new Runnable () { public void run () {
			if (balance.get() < amount)
				throw new Error("Out of money");
			balance.decrement(amount);
		}});
	}
	
	void transfer (final AccountTM another, final long amount) {
		atomic(new Runnable () { public void run () {
			withdraw (amount);
			another.deposit(amount);
		}});	
	}
	
	long balance () {
		return (long) balance.atomicGet();
	}	

}
