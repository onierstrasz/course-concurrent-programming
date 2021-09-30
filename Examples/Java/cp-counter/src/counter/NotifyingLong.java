package counter;

/**
 * Helper object to issue notifications to observers
 * only when the state actually changes.
 * 
 * Lecture: Liveness and Guarded Methods
 * 
 * $Id: NotifyingLong.java 24284 2009-01-24 13:29:24Z oscar $
 *
 */
public class NotifyingLong {
	private long value;
	private Object observer;
	public NotifyingLong(Object o, long v) {
		observer = o; 
		value = v; 
	}
	public synchronized long value() { return value; }
	public void setValue(long v) {
		synchronized(this) {		// NB: partial synchronization
			value = v;
		}
		synchronized(observer) { 
			observer.notifyAll();	// NB: must be synchronized!
		}
	}
}