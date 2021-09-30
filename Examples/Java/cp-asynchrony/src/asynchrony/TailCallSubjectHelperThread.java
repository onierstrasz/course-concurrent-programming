package asynchrony;

/**
 * Here we use a new thread for the tail call.
 * 
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: TailCallSubjectHelperThread.java 24393 2009-01-26 17:04:57Z oscar $
 */
public class TailCallSubjectHelperThread extends TailCallSubject {

	public synchronized void updateState(double d) { 
		state = d;
		new Thread() {
			public void run() {
				observer.update(TailCallSubjectHelperThread.this, state);
			}
		}.start();
	}

	// DEMO CODE
	public static void main(String[] args) {
		TailCallSubject host = new TailCallSubjectHelperThread();
		clientThread(host).start();
		clientThread(host).start();
	}
}