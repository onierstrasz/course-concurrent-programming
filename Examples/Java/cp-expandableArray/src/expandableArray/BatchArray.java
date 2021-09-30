package expandableArray;

/**
 * Demonstrates atomic batch actions.
 * 
 * Removing the synchronization will provoke a race condition
 * and the test will fail.
 * 
 * Lecture: Safety Patterns
 *  
 * $Id: BatchArray.java 24266 2009-01-23 19:49:25Z oscar $
 *
 */
public class BatchArray<Value> extends ExpandableArray<Value> {
	public BatchArray(int initialSize) { super(initialSize); }
	public BatchArray() { super(); }
	public synchronized void updateAll(Mutator<Value> p) {
		for (int i = 0; i < size; ++i) {
			data[i] = p.update(data[i]);
		}
	}
}
