package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;

public class Insert {

	public Log log = LogFactory.getLog(this.getClass());

	public void test() {
		Mongo mongo = null;
		try {
			mongo = new Mongo("10.10.148.131", 10000);
		} catch (UnknownHostException e) {
		}
		DB db = mongo.getDB("diameter_test");
		DBCollection collection = db.getCollection("dcca_dccr_test");

		BasicDBObject uniqueIndex = new BasicDBObject();
		uniqueIndex.append("__avpSessionId", 1);
		uniqueIndex.append("__avpCCRequestNumber", 1);
		uniqueIndex.append("request", 1);
		collection.ensureIndex(uniqueIndex, "sessionIdIndex", true);

		BufferedReader bis = null;
		Calendar c = Calendar.getInstance();
		try {
			bis = new BufferedReader(new InputStreamReader(new FileInputStream("test_data.txt")));
			String json = bis.readLine();
			long i = 1;
			log.debug("monogdb test begin.ip:10.10.148.131, port:10000. Date:" + c.getTime());
			long begin = c.getTimeInMillis();
			long current = 0;
			int ips = 0;
			Calendar c2 = (Calendar) c.clone();
			while (true) {
				insert(collection, json);
				if (i % 10000 == 0) {
					log.debug("client insert count is " + i + ", db collection count is " + collection.count());
				}
				if (i % 500000 == 0) {
					current = System.currentTimeMillis();
					c2.setTimeInMillis(current);
					ips = (int) (i / ((current - begin) / 1000));
					log.debug("client insert ips is " + ips + ", count is " + i + ". BEGIN:" + c.getTime() + ", CURRENT:" + c2.getTime());
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.error("monogdb error!", e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (mongo != null) {
				mongo.close();
			}
			c.setTimeInMillis(System.currentTimeMillis());
			log.debug("monogdb test end! Date:" + c.getTime());
		}
	}

	private Random r = new Random(47);

	public void insert(DBCollection collection, String json) {
		DBObject dbObject = (DBObject) JSON.parse(json);
		dbObject.removeField("__avpSessionId");
		dbObject.put("__avpSessionId", UUID.randomUUID().toString());
		dbObject.put("test", r.nextInt(1000));
		collection.insert(dbObject, WriteConcern.SAFE);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Insert t = new Insert();
		t.test();
	}
}
