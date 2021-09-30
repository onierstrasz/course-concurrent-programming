import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Simply tests Complex functionality.
 * There are no race conditions to test.
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: ComplexTest.java 24246 2009-01-23 12:58:09Z oscar $
 *
 */
public class ComplexTest {

	@Test public void sum() {
		final Complex a = new Complex(1,2);
		final Complex b = new Complex(3,4);
		final Complex c = new Complex(4,6);
		assertEquals(c, a.plus(b));
	}

	@Test public void rootMinusOne() {
		final Complex i = new Complex(0,1);
		assertEquals(new Complex(-1), i.times(i));
	}
	
	// Further tests would look like this ...
	
}
