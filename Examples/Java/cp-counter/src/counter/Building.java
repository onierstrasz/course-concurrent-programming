package counter;

/**
 * Permit demo.
 * 
 * Lecture: Condition Objects
 * 
 * $Id: Building.java 25439 2009-03-13 12:35:03Z oscar $
 *
 */
public class Building {

	Permit permit;

	Building(int n) {
		permit = new Permit(n);
	}

	void enter(String person) { // NB: unsynchronized
		permit.await();
		System.out.println(person + " has entered the building");
	}

	void leave(String person) {
		System.out.println(person + " has left the building");
		permit.signal();
	}

	// DEMO CODE
	public static void main(String[] args) {
		Building building = new Building(3);
		enterAndLeave(building, "bob");
		enterAndLeave(building, "carol");
		enterAndLeave(building, "ted");
		enterAndLeave(building, "alice");
		enterAndLeave(building, "elvis");
	}

	private static void enterAndLeave(final Building building, final String person) {
		new Thread() {
			public void run() {
				building.enter(person);
				pause();
				building.leave(person);
			}
		}.start();
	}

	static void pause() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

}
