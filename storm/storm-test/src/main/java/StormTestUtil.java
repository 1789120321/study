/**
 * Created by Luonanqin on 10/24/14.
 */
public class StormTestUtil {

	public static void printEmit(Object... objs) {
		String str = "";
		for (Object obj : objs) {
			str = obj.toString() + " | ";
		}
		System.out.println("Emit: " + str.substring(0, str.length() - 3));
	}
}
