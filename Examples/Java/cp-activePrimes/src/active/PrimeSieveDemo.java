package active;

/**
 * Prime Sieve using active objects -- each prime in the
 * sieve has its own thread.
 * 
 * Lecture: Architectural Styles
 * 
 * $Id: PrimeSieveDemo.java 24494 2009-01-29 16:00:28Z oscar $
 */
public class PrimeSieveDemo {
	public static void main(String args[]) {
		if (args.length > 0) {
			try {
				genPrimes(Integer.parseInt(args[0]));
			} catch (NumberFormatException e) {
				System.out.println("Error: " + args[0] + " not a number");
			}
		} else {
			int n = 10000;
			genPrimes(n);
		}
	}

	public static void genPrimes(int n) {
		try {
			new ActivePrime(2, new TestForPrime(n));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}	
	}
}
