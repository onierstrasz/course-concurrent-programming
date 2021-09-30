package test.unibe.iam.workflow.model;

import java.util.ArrayList;

import ch.unibe.iam.workflow.model.Activity;
import ch.unibe.iam.workflow.model.ExceptionActivity;
import ch.unibe.iam.workflow.model.ExecutionContext;
import ch.unibe.iam.workflow.model.Workflow;
import ch.unibe.iam.workflow.model.WorkflowEngine;

public class WorkflowEngineCaseStudy {
	
	public static void singleThreadPool() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withSingleThreadPool();
		ExecutionContext.workflowEngine(aWorkflowEngine);
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new MockActivity("1",2000));
		activities.add(new MockActivity("2",3000));
		activities.add(new MockActivity("3",3000));
		Workflow aWorkflow = new Workflow(activities);
		aWorkflowEngine.startUp();
		aWorkflowEngine.execute(aWorkflow);
		while(!aWorkflowEngine.activeWorkflows().isEmpty()) {
			try {
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		aWorkflowEngine.shutDown();
	}
	
	public static void singleThreadPoolWithException() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withSingleThreadPool();
		ExecutionContext.workflowEngine(aWorkflowEngine);
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new MockActivity("1",2000));
		activities.add(new MockActivity("2",3000));
		activities.add(new ExceptionActivity());
		Workflow aWorkflow = new Workflow(activities);
		aWorkflowEngine.startUp();
		aWorkflowEngine.execute(aWorkflow);
		while(!aWorkflowEngine.activeWorkflows().isEmpty()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		aWorkflowEngine.shutDown();

	}
	
	public static void singleThreadPoolMultiplesWorkflows() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withSingleThreadPool();
		ExecutionContext.workflowEngine(aWorkflowEngine);
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new MockActivity("1",2000));
		activities.add(new MockActivity("2",3000));
		activities.add(new MockActivity("3",3000));
		Workflow workflow1 = new Workflow(activities);
		
		activities = new ArrayList<Activity>();
		activities.add(new MockActivity("4",2000));
		activities.add(new MockActivity("5",3000));
		activities.add(new MockActivity("6",3000));
		Workflow workflow2 = new Workflow(activities);
		
		
		activities = new ArrayList<Activity>();
		activities.add(new MockActivity("7",3000));
		activities.add(new MockActivity("8",1000));
		activities.add(new MockActivity("9",2000));
		activities.add(new MockActivity("10",1000));
		Workflow workflow3 = new Workflow(activities);
		
		aWorkflowEngine.startUp();
		aWorkflowEngine.execute(workflow1);
		aWorkflowEngine.execute(workflow2);
		aWorkflowEngine.execute(workflow3);
		while(!aWorkflowEngine.activeWorkflows().isEmpty()) {
			try {
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		aWorkflowEngine.shutDown();

	}
	
	public static void fixedThreadPoolMultiplesWorkflows() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withFixedThreadPool(3);
		ExecutionContext.workflowEngine(aWorkflowEngine);
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new MockActivity("1",2000));
		activities.add(new MockActivity("2",3000));
		activities.add(new MockActivity("3",3000));
		Workflow workflow1 = new Workflow(activities);
		
		activities = new ArrayList<Activity>();
		activities.add(new MockActivity("4",2000));
		activities.add(new MockActivity("5",3000));
		activities.add(new MockActivity("6",3000));
		Workflow workflow2 = new Workflow(activities);
		
		
		activities = new ArrayList<Activity>();
		activities.add(new MockActivity("7",3000));
		activities.add(new MockActivity("8",1000));
		activities.add(new MockActivity("9",2000));
		activities.add(new MockActivity("10",1000));
		Workflow workflow3 = new Workflow(activities);
		
		aWorkflowEngine.startUp();
		aWorkflowEngine.execute(workflow1);
		aWorkflowEngine.execute(workflow2);
		aWorkflowEngine.execute(workflow3);
		while(!aWorkflowEngine.activeWorkflows().isEmpty()) {
			try {
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		aWorkflowEngine.shutDown();

	}
	
	public static void cachedExecutorMultiplesWorkflows() {
		WorkflowEngine aWorkflowEngine = WorkflowEngine.withCachedThreadPool();
		ExecutionContext.workflowEngine(aWorkflowEngine);
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new MockActivity("1",2000));
		activities.add(new MockActivity("2",3000));
		activities.add(new MockActivity("3",3000));
		Workflow workflow1 = new Workflow(activities);
		
		activities = new ArrayList<Activity>();
		activities.add(new MockActivity("4",2000));
		activities.add(new MockActivity("5",3000));
		activities.add(new MockActivity("6",3000));
		Workflow workflow2 = new Workflow(activities);
		
		
		activities = new ArrayList<Activity>();
		activities.add(new MockActivity("7",3000));
		activities.add(new MockActivity("8",1000));
		activities.add(new MockActivity("9",2000));
		activities.add(new MockActivity("10",1000));
		Workflow workflow3 = new Workflow(activities);
		
		aWorkflowEngine.startUp();
		aWorkflowEngine.execute(workflow1);
		aWorkflowEngine.execute(workflow2);
		aWorkflowEngine.execute(workflow3);
		while(!aWorkflowEngine.activeWorkflows().isEmpty()) {
			try {
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		aWorkflowEngine.shutDown();

	}
	
	public static void main(String args[]) {
		//WorkflowEngineCaseStudy.singleThreadPool();
		// WorkflowEngineCaseStudy.singleThreadPoolWithException();
		//WorkflowEngineCaseStudy.singleThreadPoolMultiplesWorkflows();
		WorkflowEngineCaseStudy.fixedThreadPoolMultiplesWorkflows();
		//WorkflowEngineCaseStudy.cachedExecutorMultiplesWorkflows();
	}
	

	
}
