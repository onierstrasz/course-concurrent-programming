
/**
 * A broken implementation of the ownership transfer object.
 * Unsynchronized, so problems could occur at the Thread.yield() point.
 * 
 * Lecture: Safety patterns
 * 
 * $Id: ResourceVariableBAD.java 24262 2009-01-23 19:13:01Z oscar $
 *
 */
public class ResourceVariableBAD<Resource> extends ResourceVariable<Resource> {
	public ResourceVariableBAD(Resource resource) {
		super(resource);
	}
	// NB: unsynchronized
	public Resource exchange(Resource newResource) {
		Resource oldResource = resource;
		Thread.yield(); // try to provoke race condition
		resource = newResource; 
		return oldResource;
	}
}
