package linkedList;

/**
 * Created by Luonanqin on 10/24/14.
 */
public class LinkedListItem {

	private int data;
	private char flag;
	private LinkedListItem next;

	public LinkedListItem() {
	}

	public LinkedListItem(int data) {
		this.data = data;
	}

	public LinkedListItem(int data, LinkedListItem next) {
		this.data = data;
		this.next = next;
	}

	public LinkedListItem(int data, char flag, LinkedListItem next) {
		this.data = data;
		this.flag = flag;
		this.next = next;
	}

	public LinkedListItem(int data, char flag) {
		this.data = data;
		this.flag = flag;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getData() {
		return data;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	public boolean isLast() {
		return next == null;
	}

	public void setNext(LinkedListItem next) {
		this.next = next;
	}

	public LinkedListItem next() {
		return next;
	}

	@Override
	public String toString() {
		return "Item{" + "data=" + data + ", flag=" + flag + "}";
	}
}
