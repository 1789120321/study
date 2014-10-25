import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * Created by Luonanqin on 10/25/14.
 */
class GenerateNumber extends BaseRichSpout {

	private SpoutOutputCollector collector = null;
	private Random random = new Random(System.currentTimeMillis());

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(true, new Fields("number"));
	}

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		System.out.println();
	}

	@Override
	public void nextTuple() {
		Utils.sleep(100);
		int number = random.nextInt(100) % 10;
		collector.emitDirect(2, new Values(number));
		StormTestUtil.printEmit(number);
	}
}

class NumberAppender extends BaseRichBolt {

	private int taskID;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		taskID = context.getThisTaskId();
		System.out.println("TaskID: " + taskID);
	}

	@Override
	public void execute(Tuple input) {
		int number = input.getInteger(0);
		StormTestUtil.printReceive("taskID=" + taskID + " Num: " + number);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}
}

public class DirectGroupingTest {

	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("generate", new GenerateNumber(), 1);
		builder.setBolt("appender", new NumberAppender(), 2).directGrouping("generate");

		Config conf = new Config();
		conf.setDebug(false);
		conf.setMaxTaskParallelism(3);
		// conf.put(Config.STORM_ZOOKEEPER_PORT, 2181);
		// conf.put(Config.STORM_ZOOKEEPER_SERVERS, "127.0.0.1");

		LocalCluster local = new LocalCluster();
		local.submitTopology("append-number", conf, builder.createTopology());

		Utils.sleep(3000);
		local.shutdown();
	}
}
