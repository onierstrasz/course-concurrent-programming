package active;

/**
 * One-Slot Buffer implements safe transfer of information
 * between Producer and Consumer.
 * This is is a generic component that can be used to coordinate
 * communication between two threads.  Note that only Objects
 * can be stored, not primitive types.
 * 
 * Lecture: Architectural Styles
 * 
 * $Id: OneSlotBuffer.java 24491 2009-01-29 15:37:07Z oscar $
 */
class OneSlotBuffer<Value> {
	private Value slotVal; // initially null

	public synchronized void put(Value val) {
		while (slotVal != null) {
			// System.out.println("OneSlotBuffer full -- waiting");
			try {
				wait();
			}  catch (InterruptedException e) {
				System.out.println("OneSlotBuffer interrupted!");
			}
		}
		slotVal = val;
		notifyAll();
		return;
	}

	public synchronized Value get() {
		Value rval;
		while (slotVal == null) {
			// System.out.println("OneSlotBuffer empty -- waiting");
			try {
				wait();
			}  catch (InterruptedException e) {
				System.out.println("OneSlotBuffer interrupted!");
			}
		}
		rval = slotVal;
		slotVal = null;
		notifyAll();
		return rval;
	}
}
