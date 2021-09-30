package test.unibe.iam.workflow.model;

import java.util.ArrayList;

import ch.unibe.iam.workflow.model.Activity;
import ch.unibe.iam.workflow.model.Workflow;

import junit.framework.TestCase;

public class WorkflowTest extends TestCase {

	public void testCreation() {
		ArrayList<Activity> actions = new ArrayList<Activity>();
		Workflow aWorkflow = new Workflow(actions);
		
		assertTrue(aWorkflow.actions() == actions);
	}
	
	
}
