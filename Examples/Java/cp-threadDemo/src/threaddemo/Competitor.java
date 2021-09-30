package threaddemo;

/**
 * Simple demonstration of Java Threads
 * (Lecture 2: Java and Concurrency)
 */
class Competitor extends Thread {

	public Competitor(String str) {
		super(str); // Call Thread constructor
	}

	@Override
	public void run() {	// What the thread actually does
		for (int km = 0; km < 5; km++) {
			System.out.println(km + " " + getName());
			try {
				sleep(100);
			} catch (InterruptedException e) { } }
		System.out.println("DONE: " + getName());
	}

}