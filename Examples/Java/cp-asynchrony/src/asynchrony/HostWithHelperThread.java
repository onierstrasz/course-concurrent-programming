package asynchrony;

/**
 * Here the host creates a new thread for each request.
 * The helpers may interleave.
 * 
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: HostWithHelperThread.java 24393 2009-01-26 17:04:57Z oscar $
 *
 */
public class HostWithHelperThread implements Host {
	protected Helper helper = new CountingHelper();

	public void service() {
		invokeHelper();
	}

	protected void invokeHelper() {
		new Thread() {
			public void run() {
				helper.help();
			}
		}.start();
	}
	
	// DEMO CODE
	public static void main(String[] args) {
		hostThread().start();
		hostThread().start();
	}

	private static Thread hostThread() {
		return new Thread() {
			public void run() { new HostWithHelperThread().service(); }
		};
	}

}
