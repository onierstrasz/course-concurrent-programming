package active;

/**
 * Interface for stream of values.
 * Implemented by TestForPrime and ActivePrime
 * 
 * Lecture: Architectural Styles
 * 
 * $Id: Source.java 24491 2009-01-29 15:37:07Z oscar $
 */
public interface Source<Value> {
	Value get();
}
