package ch.unibe.iam.workflow.model;

public class ExceptionActivity extends Activity {

	public ExceptionActivity() {
		super("Idle");
	}

	public void run() {
		try {
			Thread.sleep(1000);
			throw new RuntimeException("a");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Idle activity was executed by thread " + Thread.currentThread().getName());
	}

}
