import java.util.List;
import java.util.Map;

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

/**
 * Created by Luonanqin on 3/12/14.
 */
public class AppendWordTopology {

	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("taskID", new GenerateTaskID(), 1);
		builder.setBolt("name", new FollowName(), 1).shuffleGrouping("taskID");
		builder.setBolt("engineer", new Engineer(), 1).fieldsGrouping("name", new Fields("myname"));
		builder.setBolt("teacher", new Teacher(), 1).fieldsGrouping("name", new Fields("myname"));
		builder.setBolt("merge", new Merge()).allGrouping("engineer").allGrouping("teacher");
		// builder.setBolt("merge", new Merge(), 1).fieldsGrouping("engineer", new Fields("work1")).fieldsGrouping("teacher", new Fields("work2"));
		// builder.setBolt("merge", new Merge()).shuffleGrouping("engineer").shuffleGrouping("teacher");

		Config conf = new Config();
		conf.setDebug(false);
		conf.setMaxTaskParallelism(3);

		LocalCluster local = new LocalCluster();
		local.submitTopology("append-word", conf, builder.createTopology());

		Utils.sleep(3000);
		local.shutdown();
	}

	public static class GenerateTaskID extends BaseRichSpout {

		private SpoutOutputCollector _collector;
		private int _taskId;

		@Override
		public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
			_collector = collector;
			_taskId = context.getThisTaskId();
		}

		@Override
		public void nextTuple() {
			Utils.sleep(100);
			_collector.emit(new Values(_taskId + ": "));
			_taskId++;
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("taskID"));
		}
	}

	public static class FollowName extends BaseBasicBolt {

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("myname"));
		}

		@Override
		public void execute(Tuple input, BasicOutputCollector collector) {
			String taskID = input.getString(0);
			String name = taskID + "Luonanqin";
			collector.emit(new Values(name));
		}
	}

	public static class Engineer extends BaseBasicBolt {

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("work1"));
		}

		@Override
		public void execute(Tuple input, BasicOutputCollector collector) {
			String name = input.getString(0);
			String work = "Work: Engineer!";
			collector.emit(new Values(name + " " + work));
		}
	}

	public static class Teacher extends BaseBasicBolt {

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("work2"));
		}

		@Override
		public void execute(Tuple input, BasicOutputCollector collector) {
			String name = input.getString(0);
			String work = "Work: Teacher!";
			collector.emit(new Values(name + " " + work));
		}
	}

	public static class Merge extends BaseBasicBolt {

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
		}

		@Override
		public void execute(Tuple input, BasicOutputCollector collector) {
			List<Object> result = input.getValues();
			System.out.println("================== " + result);
		}
	}
}
