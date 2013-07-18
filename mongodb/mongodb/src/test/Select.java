package test;

import java.net.UnknownHostException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class Select {

	public Log log = LogFactory.getLog(this.getClass());

	private Random r = new Random(47);

	public void select() {
		Mongo mongo = null;
		try {
			mongo = new Mongo("10.10.148.131", 10001);
		} catch (UnknownHostException e) {
		}
		DB db = mongo.getDB("diameter_test");
		DBCollection collection = db.getCollection("dcca_dccr_test");

		int time = 1;
		while (true) {
			long begin = System.currentTimeMillis();
			collection.findOne(new BasicDBObject("test", r.nextInt(1000)));
			long end = System.currentTimeMillis();
			log.debug("Find One consume " + (end - begin) + " millis. Time: " + time + " Count: " + collection.count());
			time++;
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Select s = new Select();
		s.select();
	}

}
