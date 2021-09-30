
package slot;

/**
 * Interface for various kinds of synchronized one-slot buffers
 * 
 * Lecture: Safety and Synchronization
 * 
 * $Id: Buffer.java 24263 2009-01-23 19:23:27Z oscar $
 *
 */
public interface Buffer<Value> {
	public void put(Value val);
	public Value get();
}
