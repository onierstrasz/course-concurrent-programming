package slot;

/**
 * An active object performs <count> actions in its own thread.
 * 
 * Lecture: Safety and Synchronization
 * 
 * $Id: ActiveObject.java 24339 2009-01-24 20:28:01Z oscar $
 *
 */
abstract class ActiveObject extends Thread {
	protected int count;
	ActiveObject(String name, int count) {
		super(name);
		this.count = count;
	}
	public void run() {
		int i;
		for (i=1;i<=count;i++) {
			this.action(i);
		}
	}
	protected abstract void action(int n);
}
