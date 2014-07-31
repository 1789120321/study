import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		Map<String, Integer> testMap = new HashMap<String, Integer>(2, 0.5f);
		testMap.put("luo", 1);
		testMap.put("nan", 2);
		testMap.put("luo", 3);
		testMap.put("pangy", 4);

		for (Iterator<String> iter = testMap.keySet().iterator(); iter.hasNext(); ) {
			String next = iter.next();

		}
	}

}
