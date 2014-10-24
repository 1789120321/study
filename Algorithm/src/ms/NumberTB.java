package ms;

public class NumberTB {

	public final static int len = 10;
	private int top[];
	private int bottom[];
	private boolean success;

	public NumberTB() {
		top = new int[len];
		bottom = new int[len];
		success = false;

		for (int i = 0; i < len; i++) {
			top[i] = i;
		}
	}

	public int[] getBottom() {
		int i = 0;
		while (!success) {
			i = i + 1;
			setNextBottom();
		}
		return bottom;
	}

	public void setNextBottom() {
		boolean reB = true;

		for (int i = 0; i < len; i++) {
			int frequecy = getFrequecy(i);

			if (bottom[i] != frequecy) {
				bottom[i] = frequecy;
				reB = false;
			}
		}
		success = reB;
	}

	public int getFrequecy(int num) // 此处的num即指上排的数 i
	{
		int count = 0;

		for (int i = 0; i < len; i++) {
			if (bottom[i] == num)
				count++;
		}
		return count; // cout即对应 frequecy

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NumberTB nTB = new NumberTB();
		int[] result = nTB.getBottom();

		for (int i = 0; i < len; i++) {
			System.out.print(result[i] + " ");
		}
	}
}
