
package clock;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

import javax.swing.JComponent;
import javax.swing.JFrame;

import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Demonstrate use of asynchronous Thread to update a clock.
 * Lecture: Java and Concurrency
 * 
 * $Id: Clock.java 24241 2009-01-23 11:17:34Z oscar $
 *
 */
@SuppressWarnings("serial")
public class Clock extends JComponent implements Runnable
{
	private Thread clockThread = null;

	private DateFormat dateFormat;
	private final int MARGIN = 0;
	private final int HEIGHT = 200;
	private final int WIDTH  = 800;

	public static void main(String[] args) {
		final Frame f = new JFrame("Clock Demo");
		final Clock clock = new Clock();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				clock.stopThread();
				f.dispose();
			}
		});
		f.add(clock, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}

	public Clock() {
		super();
		if (clockThread == null) {
			clockThread = new Thread(this, "Clock");
			clockThread.start();
		}
		dateFormat = new SimpleDateFormat("HH:mm:ss");
	}

	public void run() {
		// terminates when clockThread is set to null in stop()
		while (Thread.currentThread() == clockThread) {
			repaint();
			try { Thread.sleep(1000); }
			catch (InterruptedException e){ }
		}
	}

	public void stopThread() {
		clockThread = null;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(myFont());
		FontMetrics metrics = g2d.getFontMetrics(myFont());
		int height = metrics.getAscent();

		String time = dateFormat.format(new Date());
		g2d.drawString(time,
				(getWidth() - metrics.stringWidth(time)) / 2.0f,
				height + ((getHeight() - height) / 2));
	}

	public Dimension getPreferredSize() {
		return new Dimension(WIDTH,HEIGHT);
	}

	/*
	 * font size depends on current canvas size
	 * NB: heuristic to convert pixels to points
	 * may be platform dependent?
	 */
	private Font myFont() {
		Dimension size = getSize();
		return new Font("Helvetica", Font.PLAIN,
				Math.min((size.height-MARGIN)*5/4, (size.width-2*MARGIN)/5));
	}
}
