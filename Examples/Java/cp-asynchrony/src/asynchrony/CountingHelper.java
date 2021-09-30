package asynchrony;

/**
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: CountingHelper.java 24393 2009-01-26 17:04:57Z oscar $
 */
public class CountingHelper implements Helper {
	private static final int COUNT = 20;
	
	public void help() {
		for (int i=0; i<COUNT; i++) {
			System.out.print(i + ":");
			Thread.yield();
		}
	}
}
