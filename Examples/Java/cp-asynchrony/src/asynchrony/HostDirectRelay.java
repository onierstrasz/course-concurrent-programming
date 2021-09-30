
package asynchrony;

/**
 * Here we have no pre(), during() or post().
 * We simply invoke a helper without synchronization.
 * 
 * The Host is immutable, so it does not need to be synchronized,
 * and is free to accept multiple, concurrent requests.
 * 
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: HostDirectRelay.java 24393 2009-01-26 17:04:57Z oscar $
 *
 */
public class HostDirectRelay implements Host {
	// NB: Helper is also immutable, so unsynchronized
	protected final Helper helper = new CountingHelper();

	public void service() { // unsynchronized!
		invokeHelper();		// stateless method
	}

	protected void invokeHelper() {
		helper.help();		// unsynchronized!
	}
	
	// DEMO CODE
	public static void main(String[] args) {
		Host host = new HostDirectRelay();
		clientThread(host).start();
		clientThread(host).start();
	}

	private static Thread clientThread(final Host host) {
		return new Thread() {
			public void run() { host.service(); }
		};
	}
}
