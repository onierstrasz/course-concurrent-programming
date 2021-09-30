
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * NB: This class only tests the functionality of LinkedCell,
 * not the synchronization behaviour. (There is no obvious
 * way to test potential race conditions on doubles ...)
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: LinkCellTest.java 24253 2009-01-23 15:27:44Z oscar $
 *
 */
public class LinkCellTest {
	private LinkedCell cell;
	final double delta = 1E-10;
	
	@Before
	public void setUp() {
		cell = new LinkedCell(1, new LinkedCell(2, new LinkedCell(3, null)));
	}

	@Test
	public void testValue() {
		assertEquals(1, cell.value(), delta);
	}

	@Test
	public void testSetValue() {
		cell.setValue(4);
		assertEquals(4, cell.value(), delta);
	}

	@Test
	public void testNext() {
		assertEquals(1, cell.value(), delta);
		LinkedCell next1 = cell.next();
		assertEquals(2, next1.value(), delta);
		LinkedCell next2 = next1.next();
		assertEquals(3, next2.value(), delta);
		LinkedCell next3 = next2.next();
		assertEquals(null, next3);
	}

	@Test
	public void testSum() {
		assertEquals(6, cell.sum(), delta);
	}

	@Test
	public void testIncludes() {
		assertTrue(cell.includes(1));
		assertTrue(cell.includes(2));
		assertTrue(cell.includes(3));
		assertFalse(cell.includes(4));
	}

}
