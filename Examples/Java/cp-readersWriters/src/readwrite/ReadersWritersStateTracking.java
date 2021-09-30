package readwrite;

/**
 * A Readers/Writers implementation using
 * state tracking variables.
 * 
 * Lecture: Fairness and Optimism
 * 
 * $Id: ReadersWriters.java 24450 2009-01-28 16:37:38Z oscar $
 *
 */
public abstract class ReadersWritersStateTracking {
	protected int activeReaders = 0;			// zero or more
	protected int activeWriters = 0;			// always zero or one
	protected int waitingReaders = 0;
	protected int waitingWriters = 0;
	protected abstract void doRead();			// defined by subclass
	protected abstract void doWrite(); 
	public void read() {						// unsynchronized
		beforeRead();							// obtain access
		doRead();
		afterRead();							// release access
	}
	public void write() {
		beforeWrite();
		doWrite();
		afterWrite();
	}
	protected synchronized void beforeRead() {
		++waitingReaders;						// available to subclasses
		while (!allowReader()) {
			try { wait(); }
			catch (InterruptedException ex) {}
		}
		--waitingReaders;
		++activeReaders;
	}
	protected boolean allowReader() {			// default policy
		return waitingWriters == 0 && activeWriters == 0;
	}
	protected synchronized void afterRead() { 
		--activeReaders;
		notifyAll();
	}
	protected synchronized void beforeWrite() {
		++waitingWriters;
		while (!allowWriter()) {
			try { wait(); }
			catch (InterruptedException ex) {}
		}
		--waitingWriters;
		++activeWriters;
	}
	protected boolean allowWriter() {			// default policy
		return activeReaders == 0 && activeWriters == 0;
	}
	protected synchronized void afterWrite() { 
		--activeWriters;
		notifyAll();
	}
}
