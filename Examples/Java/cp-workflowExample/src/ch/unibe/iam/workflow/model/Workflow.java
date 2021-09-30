package ch.unibe.iam.workflow.model;

import java.util.ArrayList;
import java.util.Collection;

public class Workflow {

	private ArrayList<Activity> activities;
	
	public Workflow(ArrayList<Activity> actionsCollection) {
		activities = actionsCollection;
	}

	public Collection<Activity> actions() {
		return activities;
	}

	public Activity firstActivity() {
		return activities.get(0);
	}

	public boolean contains(Activity anActivity) {
		return activities.contains(anActivity);
	}

	public Activity nextActivityTo(Activity anActivity) {
		int index = activities.indexOf(anActivity);		
		return activities.get(index+1);
	}

	public boolean isNotLast(Activity anActivity) {
		int index = activities.indexOf(anActivity);
		return index+1 != activities.size();
	}

}
