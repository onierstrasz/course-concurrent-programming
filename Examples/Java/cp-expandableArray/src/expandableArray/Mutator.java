package expandableArray;

/**
 * Applies an operation to an object
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: Mutator.java 24266 2009-01-23 19:49:25Z oscar $
 *
 */
public interface Mutator<Value> {
	public Value update(Value x);
}
