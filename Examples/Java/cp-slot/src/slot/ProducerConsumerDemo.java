package slot;

/**
 * Demo communication and synchronization via a shared one-slot buffer.
 * 
 * Lecture: Safety and Synchronization
 * 
 * $Id: ProducerConsumerDemo.java 24339 2009-01-24 20:28:01Z oscar $
 *
 */
public class ProducerConsumerDemo {
	static int COUNT = 5;

	public static void main(String args[]) {
		(new ProducerConsumerDemo()).demo();
	}

	public void demo() {
		Buffer<String> slot = new Slot<String>();

		new FruitProducer("Peter", COUNT, slot, "apple").start();
		new FruitProducer("Paula", COUNT, slot, "orange").start();
		new FruitProducer("Patricia", COUNT, slot, "banana").start();

		new FruitConsumer("Carla", COUNT, slot).start();
		new FruitConsumer("Cris", 2*COUNT, slot).start();
	}

	private class FruitProducer extends Producer<String> {
		protected String wares;
		FruitProducer(String name, int count, Buffer<String> slot, String wares) {
			super(name, count, slot);
			this.wares = wares;
		}
		protected String produce(int n) {
			String message;
			message = wares + "(" + n + ")";
			System.out.println(getName() + " put " + message);
			return message;
		}
	}

	private class FruitConsumer extends Consumer<String> {
		FruitConsumer(String name, int count, Buffer<String> slot) {
			super(name, count, slot);
		}
		protected void consume(int n, String message) {
			System.out.println(getName() + " got " + message);
		}
	}
}
