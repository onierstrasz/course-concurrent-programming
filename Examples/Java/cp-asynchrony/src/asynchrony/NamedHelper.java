package asynchrony;

/**
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: NamedHelper.java 24393 2009-01-26 17:04:57Z oscar $
 */
public class NamedHelper implements Helper {
	private static final int COUNT = 100;
	private final String name;
	
	NamedHelper(String name) {
		this.name = name;
	}

	public void help() {
		for (int i=0; i<COUNT; i++) {
			System.out.print(name);
			Thread.yield();
		}
	}
}
