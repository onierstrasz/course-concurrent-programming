package ch.unibe.iam.workflow.model;

public class ExecutionContext {

	private static WorkflowEngine workflowEngine;
	
	public static void workflowEngine(WorkflowEngine aWorkflowEngine) {
		workflowEngine = aWorkflowEngine;
	}
	
	public static WorkflowEngine workflowEngine() {
		return workflowEngine;
	}
	
}
