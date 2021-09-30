package asynchrony;

import java.awt.EventQueue;

/**
 * Here we queue the helper requests in an EventQueue.
 * The helpers are always run sequentially.
 * 
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: HostEventQueue.java 24393 2009-01-26 17:04:57Z oscar $
 *
 */
public class HostEventQueue implements Host {
	protected final Helper helper = new CountingHelper();

	public void service() {
		invokeHelper();
	}

	protected void invokeHelper() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				helper.help();
			}
		});
	}

	// DEMO CODE
	public static void main(String[] args) {
		Host host = new HostEventQueue();
		clientThread(host).start();
		clientThread(host).start();
	}

	private static Thread clientThread(final Host host) {
		return new Thread() {
			public void run() { host.service(); }
		};
	}
}
