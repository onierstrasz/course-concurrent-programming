package ch.unibe.iam.workflow.model;

public class IdleActivity extends Activity {

	public IdleActivity() {
		super("Idle");
	}

	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Idle activity was executed by thread " + Thread.currentThread().getName());
	}

}
