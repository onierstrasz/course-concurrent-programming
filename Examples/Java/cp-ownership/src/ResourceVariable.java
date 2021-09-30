
/**
 * A simple class for safely transferring ownership
 * of an unsynchronized, shared resource.
 * To swap your resource with the one held by rv, evaluate:
 * 
 * var = rv.exchange(var)
 * 
 * Lecture: Safety patterns
 * 
 * $Id: ResourceVariable.java 24262 2009-01-23 19:13:01Z oscar $
 *
 */
public class ResourceVariable<Resource> {
	protected Resource resource;
	public ResourceVariable(Resource resource) {
		this.resource = resource;
	}
	public synchronized Resource exchange(Resource newResource) {
		Resource oldResource = resource; 
		resource = newResource; 
		return oldResource;
	}
}
