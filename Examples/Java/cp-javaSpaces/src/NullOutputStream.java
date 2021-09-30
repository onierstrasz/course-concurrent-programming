import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream extends OutputStream {
	public NullOutputStream() {
		super();
	}
	public void write(int b) throws IOException { }
}
