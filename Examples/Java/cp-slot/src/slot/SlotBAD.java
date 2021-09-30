package slot;

/**
 * An unsynchronized Slot.  Race condition can lead to starvation
 * See ProducerConsumerTest
 * 
 * Lecture: [Not used, but could use in Liveness and Guarded Methods]
 * 
 * $Id: Slot.java 24334 2009-01-24 17:30:43Z oscar $
 *
 */
class SlotBAD<Value> implements Buffer<Value> {	
	private Value slotVal;			// initially null
	
	public void put(Value val) {
		while (slotVal != null) {
			Thread.yield();
		}
		Thread.yield(); // race condition here
		slotVal = val;
		return;
	}
	
	public Value get() {
		Value rval;
		while (slotVal == null) {
			Thread.yield();
		}
		Thread.yield(); // race condition here
		rval = slotVal;
		slotVal = null;
		return rval;
	}
}
