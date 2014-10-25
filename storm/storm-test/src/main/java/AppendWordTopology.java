import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Luonanqin on 3/12/14.
 */
public class AppendWordTopology {

	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("taskID", new GenerateTaskID(), 1);
		builder.setBolt("lname", new FollowName("luonanqin"), 1).shuffleGrouping("taskID");
		builder.setBolt("pname", new FollowName("pangyang"), 1).shuffleGrouping("taskID");
		builder.setBolt("engineer", new Engineer(), 1).fieldsGrouping("lname", new Fields("luonanqin"));
		builder.setBolt("teacher", new Teacher(), 1).fieldsGrouping("pname", new Fields("pangyang"));
		builder.setBolt("merge", new Merge()).globalGrouping("engineer").globalGrouping("teacher");
		// builder.setBolt("merge", new Merge()).allGrouping("engineer").allGrouping("teacher");
		// builder.setBolt("merge", new Merge(), 1).fieldsGrouping("engineer", new Fields("work1")).fieldsGrouping("teacher", new Fields("work2"));

		Config conf = new Config();
		conf.setDebug(false);
		conf.setMaxTaskParallelism(3);
		// conf.put(Config.STORM_ZOOKEEPER_PORT, 2181);
		// conf.put(Config.STORM_ZOOKEEPER_SERVERS, "127.0.0.1");

		LocalCluster local = new LocalCluster();
		local.submitTopology("append-word", conf, builder.createTopology());

		Utils.sleep(9000);
		local.shutdown();
	}
}

class GenerateTaskID extends BaseRichSpout {

	private SpoutOutputCollector _collector;
	private int _taskId;

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		_collector = collector;
		_taskId = context.getThisTaskId();
	}

	@Override
	public void nextTuple() {
		Utils.sleep(1000);
		String tmp = _taskId + ": ";
		_collector.emit(new Values(tmp));
		_taskId++;
		StormTestUtil.printEmit(tmp);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("taskID"));
	}
}

class FollowName extends BaseBasicBolt {

	private String name;

	FollowName(String name) {
		this.name = name;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(name));
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String taskID = input.getString(0);
		String tmp = taskID + name;
		collector.emit(new Values(tmp));
		StormTestUtil.printEmit(tmp);
	}
}

class Engineer extends BaseBasicBolt {

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("work1"));
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String name = input.getString(0);
		String work = "Work: Engineer!";
		String tmp = name + " " + work;
		collector.emit(new Values(tmp));
		StormTestUtil.printEmit(tmp);
	}
}

class Teacher extends BaseBasicBolt {

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("work2"));
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String name = input.getString(0);
		String work = "Work: Teacher!";
		String tmp = name + " " + work;
		collector.emit(new Values(tmp));
		StormTestUtil.printEmit(tmp);
	}
}

class Merge extends BaseBasicBolt {

	private List<String> workList = new ArrayList<String>();

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String result = input.getString(0);
		workList.add(result);
		StormTestUtil.printEmit(result);
	}
}