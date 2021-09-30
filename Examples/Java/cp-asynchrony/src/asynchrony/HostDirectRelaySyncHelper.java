package asynchrony;

/**
 * Here the host is not immutable, so we must
 * synchronize the accessors.
 * 
 * Interestingly, the outputs may interleave,
 * though there is a single shared host, and
 * each client sets the helper just once.
 * 
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: HostDirectRelaySyncHelper.java 24393 2009-01-26 17:04:57Z oscar $
 *
 */
public class HostDirectRelaySyncHelper implements Host {
	protected Helper helper;

	public void service() {
		invokeHelper();
	}

	protected void invokeHelper() {
		helper().help();		// partially unsynchronized!
	}

	protected synchronized Helper helper() {
		return helper;
	}

	public synchronized void setHelper(String name) {
		helper = new NamedHelper(name);
	}

	// DEMO CODE
	public static void main(String[] args) {
		HostDirectRelaySyncHelper host = new HostDirectRelaySyncHelper();
		clientThread(host, ".").start();
		clientThread(host, "+").start();
	}

	private static Thread clientThread(final HostDirectRelaySyncHelper host, final String name) {
		return new Thread() {
			public void run() {
				host.setHelper(name);
				host.service();
			}
		};
	}
}
