package test.unibe.iam.workflow.model;

import ch.unibe.iam.workflow.model.Activity;
import ch.unibe.iam.workflow.model.ExecutionContext;

public class MockActivity extends Activity {

	private int delayTime;
	
	public MockActivity(String aName, int aDelayTime) {
		super(aName);
		delayTime = aDelayTime;
	}

	public void run() {
		try {
			Thread.sleep(delayTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ExecutionContext.workflowEngine().finish(this);
		System.out.println("Activity " +name() + " was executed by thread " + Thread.currentThread().getName());
	}

}
