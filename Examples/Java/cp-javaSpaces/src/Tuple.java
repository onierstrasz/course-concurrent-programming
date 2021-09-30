import java.math.BigInteger;

/**
 * Tuple used to encode Fibonacci values
 * 
 * Lecture: Architectural Styles
 */
public class Tuple {
	public String functionName;
	public Integer argument;
	public BigInteger result;
	
	public Tuple(String functionName, Integer argument, BigInteger result) {
		this.functionName = functionName;
		this.argument = argument;
		this.result = result;
	}
	
	// NB: fly needs a default constructor
	public Tuple() {
		this(null,null,null);
	}

	public String toString() {
		return "Tuple(\"" + functionName + "\", " + argument + ", " + result + ")";
	}
}
