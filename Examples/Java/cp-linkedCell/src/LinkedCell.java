
/**
 * Illustrates various forms of partial synchronization.
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: LinkedCell.java 24253 2009-01-23 15:27:44Z oscar $
 *
 */
public class LinkedCell {
	protected double value;				// NB: doubles are not atomic!
	protected final LinkedCell next; 	// fixed

	public LinkedCell (double val, LinkedCell next) {
		value = val;
		this.next = next;
	}

	public synchronized double value() {
		return value;
	}
	
	public synchronized void setValue(double v) {
		value = v;
	}

	public LinkedCell next() {				// not synced!
		return next; 						// next is immutable
	}
	
	public double sum() {					// add up all element values
		double v = value();					// get via synchronized accessor 
		if (next() != null) {
			v += next().sum();
		}
		return v;
	}

	public boolean includes(double x) {		// search for x
		synchronized(this) {				// sync to access value
			if (value == x) {
				return true;
			}
		} 
		if (next() == null) {
			return false;
		} else {
			return next().includes(x);
		}
	}
}
