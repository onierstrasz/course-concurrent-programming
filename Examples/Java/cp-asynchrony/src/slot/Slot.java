package slot;

/**
 * A generic one-slot buffer
 * 
 * Lecture: Safety and Synchronization
 * 
 * $Id: Slot.java 24334 2009-01-24 17:30:43Z oscar $
 *
 */
public class Slot<Value> implements Buffer<Value> {	
	private Value slotVal;			// initially null
	
	public synchronized void put(Value val) {
		while (slotVal != null) {
			try { wait(); }			// become NotRunnable
			catch (InterruptedException e) { }
		}
		slotVal = val;
		notifyAll();				// make waiting threads Runnable
		return;
	}
	
	public synchronized Value get() {
		Value rval;
		while (slotVal == null) {
			try { wait(); }
			catch (InterruptedException e) { }
		}
		rval = slotVal;
		slotVal = null;
		notifyAll();
		return rval;
	}
}
