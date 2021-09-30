package asynchrony;

import java.util.Observable;
import java.util.Observer;

/**
 * The observer is updated asynchronously as the
 * final tail call. 
 * 
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: TailCallSubject.java 24393 2009-01-26 17:04:57Z oscar $
 */
public class TailCallSubject extends Observable {
	protected Observer observer = new Observer() {
		public void update(Observable o, Object arg) {
			System.out.print(arg + ":");
		}
	};
	protected double state;
	public void updateState(double d) {		// unsynchronized
		doUpdate(d); 						// partially synchronized
		sendNotification();					// unsynchronized
	}
	protected synchronized void doUpdate(double d) {	// synchronized
		state = d;
	}
	protected void sendNotification() {		// unsynchronized
		observer.update(this, state);
	}

	// DEMO CODE
	public static void main(String[] args) {
		TailCallSubject host = new TailCallSubject();
		clientThread(host).start();
		clientThread(host).start();
	}

	protected static final int COUNT = 20;
	protected static Thread clientThread(final TailCallSubject host) {
		return new Thread() {
			public void run() {
				for (int i=1; i<=COUNT; i++) {
					host.updateState(i);
					Thread.yield();
				}
			}
		};
	}

}
