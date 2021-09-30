package ch.unibe.iam.workflow.model;

public class Activity implements Runnable{

	private String name;
	
	public Activity(String aName) {
		name = aName;
	}

	public String name() {
		return name;
	}

	public void run() {

		
	}

}
