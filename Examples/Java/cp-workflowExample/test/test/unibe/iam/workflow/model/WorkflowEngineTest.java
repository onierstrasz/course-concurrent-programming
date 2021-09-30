package test.unibe.iam.workflow.model;

import java.lang.reflect.Field;
import java.util.ArrayList;


import ch.unibe.iam.workflow.model.Activity;
import ch.unibe.iam.workflow.model.Workflow;
import ch.unibe.iam.workflow.model.WorkflowEngine;

import junit.framework.TestCase;

public class WorkflowEngineTest extends TestCase {

	public void testWithFixedThreadPool() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withFixedThreadPool(2);
		assertFalse(aWorkflowEngine == null);
		assertTrue(aWorkflowEngine.activitiesToBeExecutedSize() == 0);
	}
	
	public void testWithSingleThreadPool() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withSingleThreadPool();
		assertFalse(aWorkflowEngine == null);
		assertTrue(aWorkflowEngine.activitiesToBeExecutedSize() == 0);
	}
	
	public void testWithCachedThreadPool() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withCachedThreadPool();
		assertFalse(aWorkflowEngine == null);
		assertTrue(aWorkflowEngine.activitiesToBeExecutedSize() == 0);
	}
	
	public void testExecute() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withSingleThreadPool();
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new Activity("1"));
		activities.add(new Activity("2"));
		Workflow aWorkflow = new Workflow(activities);
		aWorkflowEngine.execute(aWorkflow);
		assertTrue(aWorkflowEngine.activitiesToBeExecutedSize() == 1);
	}
	
	public void testGetNextActivity() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withSingleThreadPool();
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new Activity("1"));
		activities.add(new Activity("2"));
		
		try {
			Field aField = aWorkflowEngine.getClass().getDeclaredField("activitiesToBeExecuted");
			aField.setAccessible(true);
			aField.set(aWorkflowEngine,activities);
		} catch (IllegalArgumentException e) {
			fail();
		} catch (IllegalAccessException e) {
			fail();
		} catch (SecurityException e) {
			fail();
		} catch (NoSuchFieldException e) {
			fail();
		}
		
		assertTrue(aWorkflowEngine.activitiesToBeExecutedSize() == 2);
		assertTrue(aWorkflowEngine.getNextActivity().name() == "1");
		assertTrue(aWorkflowEngine.activitiesToBeExecutedSize() == 2);
	}
	
}
