package active;

/**
 * ActivePrime -- implements a prime as an active object
 * Can we be sure that primes will be linked in the correct order?
 * [Primes are passed along in a strictly ordered stream, and can
 * only be approved by the first prime whose square is bigger
 * than the value tested => must be linked in order ...]
 * 
 * Lecture: Architectural Styles
 * 
 * $Id: ActivePrime.java 24494 2009-01-29 16:00:28Z oscar $
 */
class ActivePrime extends Thread implements Source<Integer> {
	private static Source<Integer> lastPrime;		// where to link the next prime
	private int value;				// value of this prime
	private int square;				// square of this prime
	private Source<Integer> intSrc;	// source of ints to test

	private OneSlotBuffer<Integer> slot;		// to pass test value to next ActivePrime
	public ActivePrime(int value, Source<Integer> intSrc)
		throws ActivePrimeFailure
	{
		this.value = value;
		this.square = value*value;
		this.intSrc = intSrc;
		slot = new OneSlotBuffer<Integer>();
		lastPrime = this;			// NB: set class variable
		this.checkInvariant();		// Perform sanity check!
		System.out.println(value);
		// System.out.print(value + " ");
		System.out.flush();
		this.start();
	}

	public int value() {
		return this.value;
	}

	/**
	 * primes must be linked in increasing order! This method
	 * checks the invariant and (presumably never) throws an
	 * exception if the invariant is violated. 
	 */
	private void checkInvariant()
		throws ActivePrimeFailure
	{
		if (this.value > 2) {
			// If my value is not 2; then intSrc is also an ActivePrime
			int previous = ((ActivePrime) this.intSrc).value();
			if (previous > this.value) {
				throw new ActivePrimeFailure("Fatal error: prime "
					+ this.value + " linked after " + previous);
			}
		}
	}

	public void run() {
		int testValue = intSrc.get();	// may block
		while (testValue != 0) {
			if (testValue < this.square) {
				try {
					new ActivePrime(testValue, lastPrime);
				} catch (Exception e) {
					System.err.println(e.getMessage());
					testValue = 0; // stop the thread
				}
			} else if ((testValue % this.value) > 0) {
				// passed test, so prepare to give to next prime
				this.put(testValue);
			}
			testValue = intSrc.get();	// may block
		}
		put(0); // stop condition
	}

	private void put(Integer val) {
		slot.put(val);
	}

	public Integer get() {
		return slot.get();
	}
}
