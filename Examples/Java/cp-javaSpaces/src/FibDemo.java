import com.zink.fly.FlyPrime;
import com.zink.fly.kit.FlyFinder;
import java.io.PrintStream;
import java.math.BigInteger;

/**
 * Illustrates the use of Linda-style tuple spaces to realize a parallel computation.
 * Helper methods provide a more Linda-like interface to the fly framework.
 * 
 * Lecture: Architectural Styles
 */
public class FibDemo {
	FlyPrime tupleSpace;
	private final static int FibToCompute = 5;
	private final static long WaitTime = 60*60*1000L;	// max time for blocking requests
	private final static long LeaseTime = WaitTime;		// time to keep data in tuple space
	private final static long ZeroWaitTime = 0L;			// for non-blocking requests
	private static PrintStream debug;

	/**
	 * An Eval evaluates an expression in a separate thread.
	 * It should also define toString() to document what it is computing.
	 */
	public abstract class Eval {
		String name; // What do I evaluate?
		Eval(String s) { name = s; }
		abstract BigInteger expr();
		public String toString() { return name; }
	}

	public FibDemo() {
		connectToTupleSpace();
	}

	public static void main(String[] args) {
		int n = FibToCompute;
		BigInteger result;
		debug = System.out;
		// debug = new PrintStream(new NullOutputStream());  // silent
		FibDemo fibdemo = new FibDemo();
		fibdemo.emptyTupleSpace();
		debug.println("Computing fib(" + n + ")");
		result = fibdemo.fib(n);
		debug.print("DONE: ");
		System.out.println("fib(" + n + ") = " + result);
	}

	/**
	 * Compute nth Fibonacci number using a tuple space
	 */
	BigInteger fib(final int n) {
		Tuple tuple;

		tuple = rdp(new Tuple("Fib", n, null));		// non-blocking
		if (tuple != null) {
			return tuple.result;
		}

		if (n<2) {
			out(new Tuple("Fib", n, BigInteger.ONE));			// non-blocking
			return BigInteger.ONE;
		}
		eval("Fib", n, new Eval("fib(" + (n-1) + ")+fib(" + (n-2) + ")") {
			public BigInteger expr() { return fib(n-1).add(fib(n-2)); }
		} );
		tuple = rd(new Tuple("Fib", n, null));		// blocking
		return tuple.result;
	}	// Post-condition: rdp("Fib",n,null) != null

	/**
	 * Non-blocking, non-destructive read.
	 * Returns null if no tuple is found.
	 * Null values in the template match any value,
	 * so result is usually set to null to search for
	 * a matching tuple.
	 */
	private Tuple rdp(Tuple template) {
		Tuple tuple = tupleSpace.read(template, ZeroWaitTime);
		debug.println("rdp(" + template + ") = " + tuple);
		return tuple;
	}

	/**
	 * Blocking, non-destructive read.
	 */
	private Tuple rd(Tuple template) {
		debug.println("rd(" + template + ") [blocks]");
		Tuple tuple = tupleSpace.read(template, WaitTime);
		debug.println("rd(" + tuple + ") [returns]");
		return tuple;
	}

	/**
	 * Non-blocking, destructive read.
	 */
	private Tuple inp(Tuple template) {
		Tuple tuple = tupleSpace.take(template, ZeroWaitTime);
		// debug.println("inp(" + template + ") = " + tuple);
		return tuple;
	}

	/**
	 * Non-blocking write.
	 */
	private void out(Tuple template) {
		debug.println("out(" + template + ")");
		long leaseTime = tupleSpace.write(template, LeaseTime);
		if (leaseTime != LeaseTime) {
			System.err.println("Unexpected lease time for written tuple: "
					+ leaseTime + " != " + LeaseTime );
			// System.exit(1);
		}
	}

	/**
	 * Evaluate an expression representing a tuple in a separate thread.
	 * Output the tuple when it has been computed.
	 */
	private void eval(String fn, final Integer arg, final Eval eval) {
		debug.println("eval(\"" + fn + "\", " + arg + ", " + eval + ")");
		new Thread() {
			public void run() { out(new Tuple("Fib", arg, eval.expr())); }
		}.start();
	}

	/**
	 * Boilerplate code from the fly framework.
	 */
	private void connectToTupleSpace() {
		FlyFinder flyFinder = new FlyFinder();
		this.tupleSpace = flyFinder.find();
		if (null == tupleSpace) {
			System.err.println("Failed to find a Fly Server running on the local network");
			System.exit(1);
		}
	}

	/**
	 * Remove all old Fib Tuples from previous run.
	 */
	private void emptyTupleSpace() {
		Tuple template = new Tuple("Fib", null, null);
		while(inp(template) != null);
	}
}
