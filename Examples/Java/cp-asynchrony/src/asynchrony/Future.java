package asynchrony;

/**
 * Lecture: Liveness and Asynchrony
 * 
 * $Id: Future.java 24404 2009-01-26 19:36:11Z oscar $
 *
 */
public abstract class Future<Result,Argument> {
	private Result result;		// initially null

	public Future(final Argument arg) {
		new Thread() {
			public void run() { setResult(computeResult(arg)); }
		}.start();
	}

	abstract protected Result computeResult(Argument arg);

	public synchronized void setResult(Result val) {
		result = val;
		notifyAll();				// make waiting threads Runnable
		return;
	}
	
	public synchronized Result result() {
		while (result == null) {
			try { wait(); }
			catch (InterruptedException e) { }
		}
		return result;
	}
	
	// DEMO CODE
	public static void main(String args[]) {
		// With some luck, the faster ones may get computed first ...
		// NB: results depend highly on Thread scheduling ...
		demoThread(35).start();
		demoThread(25).start();
		demoThread(15).start();
		demoThread(5).start();
	}

	protected static Thread demoThread (final int n) {
		return new Thread() {
			public void run() {
				System.out.println("CALLING fibonacci("+n+")");
				Future<Integer,Integer> f = new Future<Integer,Integer>(n) {
					protected synchronized Integer computeResult(Integer n) {
						return fibonacci(n);
					}
					// slow, naive algorithm to force long compute times ;-)
					public int fibonacci(int n) {
						if (n<2) { return 1; }
						else { return fibonacci(n-1) + fibonacci(n-2); }
					}
				};
				System.out.println("GOT future(fibonacci("+n+"))");
				int val = f.result();
				System.out.println("GOT fibonacci("+n+") = " + val);
			}
		};
	}
}
