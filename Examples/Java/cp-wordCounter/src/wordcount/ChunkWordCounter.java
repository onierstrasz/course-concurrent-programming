package wordcount;

import java.util.StringTokenizer;

/**
 * Count the number of words in a chunk of text and report it to the WordCounter.
 */
public class ChunkWordCounter implements Runnable {

	private final StringTokenizer tokenizer;
	private final WordCounter counter;

	public ChunkWordCounter(String buffer, WordCounter counter) {
		tokenizer = new StringTokenizer(buffer, WordCounter.DELIMS);
		this.counter = counter;
	}

	/**
	 * Main task : tokenizes the given buffer and counts words.
	 */
	@Override
	public void run() {
		int total = 0;
		while (tokenizer.hasMoreTokens()) {
			tokenizer.nextToken();
			total++;
		}
		counter.incrementTotalWordCount(total);
	}

}
