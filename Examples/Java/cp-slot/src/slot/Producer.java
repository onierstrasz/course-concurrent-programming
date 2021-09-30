package slot;

/**
 * Producers puts values into a slot shared with a Consumer
 * 
 * Lecture: Safety and Synchronization
 * 
 * $Id: Producer.java 24337 2009-01-24 18:57:56Z oscar $
 *
 */
abstract class Producer<Value> extends ActiveObject {
	protected Buffer<Value> slot;
	Producer(String name, int count, Buffer<Value> slot) {
		super(name, count);
		this.slot = slot;
	}
	protected void action(int n) {
		slot.put(produce(n));
	}
	protected abstract Value produce(int n);
}
