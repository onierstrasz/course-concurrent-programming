
package slot;

/**
 * Interface for various kinds of synchronized one-slot buffers
 * 
 * Lecture: Safety and Synchronization
 * 
 * $Id: Buffer.java 24403 2009-01-26 19:35:37Z oscar $
 *
 */
public interface Buffer<Value> {
	public void put(Value val);
	public Value get();
}
