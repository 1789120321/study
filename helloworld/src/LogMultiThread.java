import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.IOException;

/**
 * Created by Luonanqin on 6/20/14.
 */
class LogTest implements Runnable {

	private Logger logger;
	private int i;

	public LogTest(int i) {
		logger = Logger.getLogger(this.getClass().getName());
		logger.removeAllAppenders();
		try {
			logger.addAppender(new FileAppender(new PatternLayout(), this.getClass().getName() + "-" + i + ".log", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.i = i;
	}

	public void run() {
		logger.info("Test Log: LogTest " + i);
	}
}

public class LogMultiThread {

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new Thread(new LogTest(i), "LogTest-" + i).start();
		}
	}
}
