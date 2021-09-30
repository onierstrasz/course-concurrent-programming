package counter;

/**
 * This is almost identical to the BoundedCounter interface,
 * except for the exceptions raised. 
 * @author oscar
 *
 */

public interface BalkingCounter {
	public static final long MIN = 0;																			// min value
	public static final long MAX = 5;																			// max value
	public long value();	// inv't: MIN <= value() <= MAX
							// init: value() == MIN
	public void inc() throws BalkingException;	// pre: value() < MAX
	public void dec() throws BalkingException;	// pre: value() > MIN
	public int errors();
}