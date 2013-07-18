package test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

public class Test {

	public Log log = LogFactory.getLog(this.getClass());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mongo mongo = null;
		try {
			ServerAddress sa1 = new ServerAddress("10.10.148.131", 27030);
			ServerAddress sa2 = new ServerAddress("10.10.148.131", 27031);
			List<ServerAddress> saList = new ArrayList<ServerAddress>();
			saList.add(sa1);
			saList.add(sa2);

			mongo = new Mongo(saList);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		ReadPreference r = ReadPreference.secondaryPreferred();

		System.out.println(mongo);
	}

}
