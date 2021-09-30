package expandableArray;

import java.util.NoSuchElementException;

/**
 * Demonstrates full synchronization
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: ExpandableArray.java 24266 2009-01-23 19:49:25Z oscar $
 *
 */
public class ExpandableArray<Value> {
	protected Value[] data; 					// the elements
	protected int size;							// the number of slots used
	static final int DEFAULT_SIZE = 10;
	public ExpandableArray(int initialSize) {
		data = newArray(initialSize);			// reserve some space
		size = 0;
	}
	
	@SuppressWarnings("unchecked")
	private Value[] newArray(int initialSize) {
		return (Value[]) new Object[initialSize];
	}
	
	public ExpandableArray() { this(DEFAULT_SIZE); }
	
	public synchronized int size() { return size; }
	public synchronized Value at(int i) throws NoSuchElementException {
		if (i < 0 || i >= size ) {
			throw new NoSuchElementException();
		} else {
			return data[i];
		}
	}
	public synchronized void append(Value x) { 	// add at end
		if (size >= data.length) { 					// need a bigger array
			Object[] olddata = data;				// so increase ~50%
			data = newArray(3 * (size + 1) / 2);
			System.arraycopy(olddata, 0, data, 0, olddata.length);
		}
		data[size++] = x;
	}
	public synchronized void removeLast() throws NoSuchElementException {
		if (size == 0) {
			throw new NoSuchElementException();
		} else {
			data[--size] = null;
		}
	}
}

