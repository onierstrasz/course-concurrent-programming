package test.unibe.iam.workflow.model;

import ch.unibe.iam.workflow.model.Activity;
import junit.framework.TestCase;

public class ActivityTest extends TestCase {

	public void testCreation() {
		Activity anActivity = new Activity("1");
		assertTrue(anActivity.name() == "1");
	}
	
	
}
