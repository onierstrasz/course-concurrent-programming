package wordcount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Use a thread pool to count words in a file by splitting it into chunks and
 * handing each chunk to a ChunkWordCounter object.
 * 
 * Loosely adapted from
 * http://www.cs.umd.edu/class/fall2010/cmsc433/examples/wordcount
 * /WordCountParallelBad.java
 * 
 * @author Jorge Ressia and Oscar Nierstrasz
 * 
 */

public class WordCounter {
	private static final int CHUNK = 50000;
	private static final String TESTFILE = "book.txt";
	public final static String DELIMS = " :;,.{}()\t\n";

	//private int totalCount = 0;
	private AtomicInteger totalCount;
	private int numThreads;
	private int chunksize;
	private ExecutorService pool;
	private BufferedReader reader;
	private String leftover = "";

	WordCounter(String inputFile, int numThreads, int chunksize)
			throws java.io.IOException {
		this.numThreads = numThreads;
		this.chunksize = chunksize;
		pool = Executors.newFixedThreadPool(numThreads);
		reader = new BufferedReader(new FileReader(inputFile));
	}

	/*
	 * Mircea: The first execution always takes some extra time. To observe this run the for loop with both increasing and decreasing the thread count. 
	 */
	public static void main(String args[]) throws java.io.IOException {
		System.out.println("threads\ttime\tchunk\twords");
		for (int numThreads = 18; numThreads >= 1; numThreads--) {
			(new WordCounter(TESTFILE, numThreads, CHUNK)).countWords();
		}
	}

	public void countWords() throws java.io.IOException {
		long startTime = System.currentTimeMillis();
		spawnWorkers();
		long endTime = System.currentTimeMillis();
		long elapsed = endTime - startTime;
		// totalCount = sumWordCounts();
		System.out.println("" + numThreads + "\t" + elapsed + "\t" + chunksize
				+ "\t" + totalCount);
	}

	private void spawnWorkers() throws IOException {
		String chunk = this.getChunk();
		while (!chunk.isEmpty()) {
			pool.submit(new ChunkWordCounter(chunk, this));
			chunk = this.getChunk();
		}
		pool.shutdown();
		try {
			pool.awaitTermination(10, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			System.out.println("Pool interrupted!");
			System.exit(1);
		}
	}

	private String getChunk() throws IOException {
		String chunk;
		String res = readFileAsString(reader, chunksize);
		if (res.isEmpty()) {
			chunk = leftover;
			leftover = "";
			return chunk;
		}
		int idx = findLastDelim(res);
		chunk = leftover + res.substring(0, idx);
		leftover = res.substring(idx, res.length());
		return chunk;
	}

	public  void incrementTotalWordCount(int total) {
		totalCount.addAndGet(total);
		//totalCount += total;
	}

	/**
	 * Looks for the last delimiter in the string, and returns its index.
	 */
	private int findLastDelim(String buf) {
		for (int index = buf.length() - 1; index >= 0; index--) {
			for (int j = 0; j < DELIMS.length(); j++) {
				char d = DELIMS.charAt(j);
				if (d == buf.charAt(index)) {
					return index;
				}
			}
		}
		return 0;
	}

	/**
	 * Reads in a chunk of the file into a string.
	 */
	private String readFileAsString(BufferedReader reader, int size)
			throws java.io.IOException {
		int charsRead = 0;
		String chunk = "";

		char[] buf = new char[size];
		charsRead = reader.read(buf, 0, size);
		if (charsRead > 0) {
			chunk = String.valueOf(buf, 0, charsRead);
		}
		return chunk;
	}

}
