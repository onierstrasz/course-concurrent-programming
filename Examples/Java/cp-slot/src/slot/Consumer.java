package slot;

/**
 * A Consumer gets values from a slot shared with a Producer
 * 
 * Lecture: Safety and Synchronization
 * 
 * $Id: Consumer.java 24339 2009-01-24 20:28:01Z oscar $
 *
 */
abstract class Consumer<Value> extends ActiveObject {
	protected Buffer<Value> slot;
	Consumer(String name, int count, Buffer<Value> slot) {
		super(name, count);
		this.slot = slot;
	}
	protected void action(int n) {
		consume(n, slot.get());
	}
	protected abstract void consume(int n, Value val);
}
