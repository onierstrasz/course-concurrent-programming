package readwrite;

/**
 * Demonstrates our Readers/Writers policy.
 * The start and end of read and write operations
 * are indicated by parentheses and brackets.
 * The demo shows that readers may overlap
 * but writers never do.
 * 
 * Lecture: Fairness and Optimism
 * 
 * $Id: ReadWriteDemo.java 24451 2009-01-28 16:48:33Z oscar $
 *
 */
class ReadWriteDemo extends ReadersWritersStateTracking {

	static public void main(String[] args) {
		new ReadWriteDemo().doit();
	}

	public void doit() {
		new Reader(this).start();
		new Reader(this).start();
		new Writer(this).start();
		new Writer(this).start();
	}

	abstract class Client extends Thread {
		protected final ReadersWritersStateTracking host;
		protected final int COUNT = 10;
		Client(ReadersWritersStateTracking host) { this.host = host; }
		public void run() {
			for (int i=0; i<=COUNT; i++) { doit(); }
		}
		abstract protected void doit();
	}

	class Reader extends Client {
		Reader(ReadersWritersStateTracking host) { super(host); }
		protected void doit() { host.read(); }
	}

	class Writer extends Client {
		Writer(ReadersWritersStateTracking host) { super(host); }
		protected void doit() { host.write(); }
	}	

	protected void doRead() {
		System.out.print("(");
		Thread.yield();
		System.out.print(")");
	}

	protected void doWrite() {
		System.out.print("[");
		Thread.yield();
		System.out.print("]");
	}

}
