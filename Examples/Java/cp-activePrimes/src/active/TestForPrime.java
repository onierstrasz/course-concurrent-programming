package active;

/**
 * Generate a sequence of integers to pass
 * through the Prime Sieve.
 * NB: can optimize to generate only odds ...
 * 
 * Lecture: Architectural Styles
 * 
 * $Id: TestForPrime.java 24491 2009-01-29 15:37:07Z oscar $
 */
class TestForPrime implements Source<Integer> {
	private int nextValue;
	private int maxValue;

	public TestForPrime(int max) {
		this.nextValue = 3;
		this.maxValue = max;
	}

	public Integer get() {
		// System.out.println("TestForPrime " + nextValue);
		if (nextValue < maxValue) {
			return nextValue++;
		} else {
			return 0;
		}
	}
}
