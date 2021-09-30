package counter;

@SuppressWarnings("serial")
public class BalkingException extends Exception {
	BalkingException() {
		super();
	}
	BalkingException(String msg) {
		super(msg);
	}
}
