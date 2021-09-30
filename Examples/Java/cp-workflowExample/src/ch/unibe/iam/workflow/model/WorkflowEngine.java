package ch.unibe.iam.workflow.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkflowEngine implements Runnable {
	
	private Executor executor;
	private ArrayList<Activity> activitiesToBeExecuted;
	private IdleActivity idleActivity;
	private boolean running;
	private ArrayList<Workflow> workflows;

	private WorkflowEngine(ExecutorService anExecutor) {
		executor = anExecutor;
		activitiesToBeExecuted = new ArrayList<Activity>();
		workflows = new ArrayList<Workflow>();
		idleActivity = new IdleActivity();
	}

	public static WorkflowEngine withFixedThreadPool(int numberOfThreads) {
		return new WorkflowEngine(Executors.newFixedThreadPool(numberOfThreads));
	}

	public static WorkflowEngine withSingleThreadPool() {
		return new WorkflowEngine(Executors.newSingleThreadExecutor());
	}

	public static WorkflowEngine withCachedThreadPool() {
		return new WorkflowEngine(Executors.newCachedThreadPool());
	}

	public synchronized void execute(Workflow aWorkflow) {
		workflows.add(aWorkflow);
		activitiesToBeExecuted.add(aWorkflow.firstActivity());
	
	}

	public synchronized int activitiesToBeExecutedSize() {
		return activitiesToBeExecuted.size();
	}
	
	public synchronized ArrayList<Object> activitiesToBeExecuted() {
		return new ArrayList<Object>(activitiesToBeExecuted);
	}
	
	public synchronized void runNextActivity() {
		Activity anActivity = getNextActivity();
		activitiesToBeExecuted.remove(anActivity);
		executor.execute(anActivity);
	}
	
	public synchronized Activity getNextActivity() {
		Activity nextActivity = null;
		if ( activitiesToBeExecuted.isEmpty() ) {
			nextActivity = idleActivity;
		} else {
			nextActivity = activitiesToBeExecuted.iterator().next();
		}
		return nextActivity;
	}

	public void run() {
		while(running) {
			runNextActivity();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void finish(Activity anActivity) {
		Workflow aWorkflow = null;
		Iterator<Workflow> iterator = workflows.iterator();
		while (iterator.hasNext()) {
			aWorkflow = iterator.next();
			if (aWorkflow.contains(anActivity)) {
				break;
			}
		}
		if (aWorkflow.isNotLast(anActivity)) {
			activitiesToBeExecuted.add(aWorkflow.nextActivityTo(anActivity));
		} else {
			workflows.remove(aWorkflow);
		}
	}
	
	public void startUp() {
		running = true;
		(new Thread(this)).start();
	}
	
	public void shutDown() {
		running = false;
	}

	public ArrayList<Workflow> activeWorkflows() {
		return new ArrayList<Workflow>(workflows);
	}

}
