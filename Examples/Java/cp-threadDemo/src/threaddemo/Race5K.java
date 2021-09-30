package threaddemo;

/**
 * Running the race multiple times will yield different results.
 */
public class Race5K {
	public static void main (String[] args) {
        // Instantiate and start threads
		new Competitor("Tortoise").start();
		new Competitor("Hare").start();
	}
}
