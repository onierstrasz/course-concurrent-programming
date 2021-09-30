
/**
 * Demo class illustrating immutable objects.
 * Further operations would also look like this.
 * 
 * Lecture: Safety Patterns
 * 
 * $Id: Complex.java 24246 2009-01-23 12:58:09Z oscar $
 *
 */

public class Complex {
	final private int x, y;

	public Complex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Complex(int x) {
		this(x, 0);
	}

	public Object plus(Complex other) {
		return new Complex(this.x+other.x, this.y+other.y);
	}

	public Object times(Complex other) {
		return new Complex(this.x*other.x - this.y*other.y, this.x*other.y + other.x*this.y);
	}

	public boolean equals(Object o) {
		if (o instanceof Complex) {
			Complex other = (Complex) o;
			return (this.x == other.x) && (this.y == other.y);
		}
		return false;
	}
	
	public int hashCode() {
		return this.x ^ this.y;
	}
	
	public String toString() {
		return "Complex(" + x + "," + y + ")";
	}
}
