import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

class ThreadTest implements Runnable {

	public void run() {
		int i = GenerateNum.get();
		System.out.println(i + " GenerateNum: " + i);
		int j = GenerateNum1.get();
		System.out.println(j + " GenerateNum1: " + j);
		GenerateNum.set(i * 2);
		System.out.println("After Change! " + i + " GenerateNum: " + GenerateNum.get());
		GenerateNum1.set(j * 2);
		System.out.println("After Change! " + j + " GenerateNum1: " + GenerateNum1.get());
		while (true) {
			System.out.println("Execution: " + i++);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class GenerateNum {

	private static AtomicInteger ai = new AtomicInteger(2);

	private static ThreadLocal<Integer> t = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return ai.getAndIncrement();
		}
	};

	public static Integer get() {
		return t.get();
	}

	public static void set(int i) {
		t.set(i);
	}
}

class GenerateNum1 {

	private static AtomicInteger ai = new AtomicInteger(10);
	WeakReference<String> sr = null;

	private static ThreadLocal<Integer> t = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return ai.getAndIncrement();
		}
	};

	public static Integer get() {
		return t.get();
	}

	public static void set(int i) {
		t.set(i);
	}

	public void test() {
		String str = "luonanqin";
		sr = new WeakReference<String>(str);
	}

	public WeakReference<String> getSr() {
		return sr;
	}
}

class Father {

	private int age;
	private String name;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Father father = (Father) o;

		if (age != father.age) {
			return false;
		}
		if (!name.equals(father.name)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = name.hashCode();
		return result;
	}
}

public class Helloworld {

	private static Object obj = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		// referenceQueueTest();
		// integerTest();
		// hashmapTest();
		// reflect();
		// arrayReference();
		// arrayListTest();
		// arrayDequeTest();
		// collectionsTest();
		// identityHashMap();
		// linkedHashMapTest();
		// priorityQueueTest();
		// treeMapTest();
		// proxyTest();
		lockSupportTest();
	}

	private static void lockSupportTest() {
		final Object lock = new Object();
		class LockTest implements Runnable {

			private int i = 0;
			private Thread t;

			LockTest(int i) {
				this.i = i;
			}

			public void setT(Thread t) {
				this.t = t;
			}

			public void run() {
				System.out.println("Lock" + " " + i);
				if (i != 2) {
					LockSupport.park(lock);
				}else{
					LockSupport.unpark(t);
				}
				System.out.println("Unlock" + " " + i);
			}

			@Override
			public String toString() {
				return "Thread " + i;
			}
		}
		LockTest lt1 = new LockTest(1);
		LockTest lt2 = new LockTest(2);
		Thread t1 = new Thread(lt1);
		t1.start();
		Thread t2 = new Thread(lt2);
		t2.start();
		lt2.setT(t1);
		System.out.println();
	}

	interface IProxy {
		void print();
	}

	private static void proxyTest() {
		class ProxyImpl implements IProxy {

			public void print() {
				System.out.println("Hello Proxy");
			}
		}
		class ProxyHandler implements InvocationHandler {

			Object obj;

			ProxyHandler(Object object) {
				obj = object;
			}

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("Before Hello");
				Object obj = method.invoke(this.obj, args);
				System.out.println("After Hello");
				return obj;
			}
		}
		ProxyImpl proxyImpl = new ProxyImpl();
		IProxy iproxy = (IProxy) Proxy.newProxyInstance(proxyImpl.getClass().getClassLoader(), proxyImpl.getClass().getInterfaces(),
				new ProxyHandler(proxyImpl));
		iproxy.print();
	}

	private static void treeMapTest() {
		NavigableMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		map.put(2, 4);
		map.put(5, 6);
		map.put(1, 3);
		map.put(7, 7);
		map.put(3, 1);
		map.put(6, 5);
		map.put(4, 2);
		// natural ordering
		for (Integer key : map.keySet()) {
			System.out.print(key + " ");
		}
		System.out.println();
		// according key order
		for (Integer value : map.values()) {
			System.out.print(value + " ");
		}
		System.out.println();
		System.out.println(map.firstKey());
		System.out.println(map.lastEntry());
		System.out.println(map.higherKey(3));
		System.out.println(map.lowerKey(3));
		System.out.println(map.floorKey(3));
		System.out.println(map.ceilingKey(3));
	}

	private static void priorityQueueTest() {
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(3);
		intList.add(4);
		intList.add(7);
		intList.add(1);
		intList.add(6);
		intList.add(2);
		intList.add(5);
		Queue<Integer> queue = new PriorityQueue<Integer>(intList);
		System.out.println(queue);
		queue.remove(4);
		System.out.println(queue);
		Iterator<Integer> iter = queue.iterator();
		while (iter.hasNext()) {
			Integer next = iter.next();
			System.out.println(next);
			if (next == 3) {
				iter.remove();
			}
		}
	}

	private static void linkedHashMapTest() {
		Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>(16, 0.75f, true);
		map.put(1, 2);
		map.put(2, 3);
		System.out.println(map.get(1));
		map.put(3, 4);
		map.put(4, 5);
		map.remove(3);
		System.out.println(map.get(2));
		for (Integer integer : map.keySet()) {
			System.out.print(integer + " ");
		}
		System.out.println(map.containsKey(1));
		map.put(1, 2);
		for (Integer integer : map.keySet()) {
			System.out.print(integer + " ");
		}
	}

	private static void identityHashMap() {
		int hash = 2;
		System.out.println((hash << 1) - (hash << 8));
	}

	private static void collectionsTest() {
		List list1 = new ArrayList();
		list1.add(1);
		list1.add(3);
		list1.add(2);

		Object obj = new String("2");
		// can't find different type
		// list1.add(obj);

		List list2 = Collections.checkedList(list1, Integer.class);
		// throw Exception in thread "main" java.lang.ClassCastException: Attempt to insert class java.lang.String element into collection with element type
		// class java.lang.Integer
		// list2.add(obj);

		List<String> list3 = Collections.emptyList();
		List<String> list4 = Collections.emptyList();
		System.out.println(list3 == list4);
		// throw Exception in thread "main" java.lang.UnsupportedOperationException
		// because EmptyList extends AbstractList, which add method is not be implemented
		// list3.add("a");
	}

	private static void arrayDequeTest() {
		Deque<Integer> deque = new ArrayDeque<Integer>(3);
		deque.add(1);
		deque.addFirst(2);
		deque.add(3);
		deque.addLast(4);
		deque.addLast(5);
		deque.addFirst(6);
		deque.addLast(7);
		// deque.add(8);
		System.out.println(deque);
	}

	private static void arrayListTest() {
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(1);
		list1.add(2);
		list1.add(5);
		list1.add(3);
		list1.add(8);
		list1.add(9);
		list1.add(7);
		list1.add(0);
		list1.add(10);
		list1.add(-1);
		list1.add(13);
		System.out.println(list1);

		Integer[] i1 = new Integer[] { new Integer(1), 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		Integer[] i2 = new Integer[] { new Integer(1), 2, 3, 4, 5, 6, 7, 8 };

		Object[] o1 = list1.toArray();
		printArray(o1);
		Integer[] i3 = list1.toArray(i1);
		printArray(i3);
		Integer[] i4 = list1.toArray(i2);
		printArray(i4);

		List<Integer> list2 = new ArrayList<Integer>(list1);

	}

	private static void arrayReference() {
		String[] as1 = { "l", "n", "q", "p", "y" };
		String[] as2 = as1;
		as2[2] = "a";
		System.out.println(as1 == as2);
	}

	private static void hashmapTest() {
		Father f1 = new Father();
		f1.setAge(1);
		f1.setName("luonq");

		Father f2 = new Father();
		f2.setAge(2);
		f2.setName("luonq");

		Map<Father, String> map = new HashMap<Father, String>();
		map.put(f1, "luonq");
		map.put(f2, "pangyang");

		reflect(map.getClass());

		System.out.println(map.get(f1));
		System.out.println(map.get(f2));
	}

	private static void reflect(Class clazz) {
		Class<?>[] classes = clazz.getDeclaredClasses();
		for (int i = 0; i < classes.length; i++) {
			Class<?> c = classes[i];
			System.out.println(c.getSimpleName());
			if (c.getSimpleName().equals("Entry")) {
				Field f = null;
				try {
					f = c.getDeclaredField("next");
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
				Object obj = new Object();
				try {
					System.out.println(f.get(obj));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		// Method[] methods = HashMap.class.getDeclaredMethods();
		// for (int i = 0; i < methods.length; i++) {
		// System.out.println(methods[i].getName());
		// }
	}

	private static void integerTest() {
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 3;
		Integer e = 321;
		Integer f = 321;
		Long g = 3L;
		System.out.println(c == d);
		System.out.println(e == f);
		System.out.println(c == (a + b));
		System.out.println(c.equals(a + b));
		System.out.println(g == (a + b));
		System.out.println(g.equals(a + b));
	}

	private static void referenceQueueTest() throws InterruptedException {
		class QueueTest {

			@Override
			protected void finalize() throws Throwable {
				super.finalize();
				obj = QueueTest.this;
			}
		}
		QueueTest queueTest = new QueueTest();
		// GenerateNum1 gn2 = queueTest;
		ReferenceQueue<QueueTest> rq = new ReferenceQueue<QueueTest>();
		// WeakReference<QueueTest> wr = new WeakReference<QueueTest>(queueTest, rq);
		// PhantomReference<QueueTest> wr = new PhantomReference<QueueTest>(queueTest, rq);
		SoftReference<QueueTest> wr = new SoftReference<QueueTest>(queueTest, rq);
		queueTest = null;

		System.out.println(wr.get());
		System.gc();
		Thread.sleep(1000);

		System.out.println(wr.get());

		System.out.println("******");
		Reference<QueueTest> g = (Reference<QueueTest>) rq.poll();
		if (g != null) {
			System.out.println(g);
			System.out.println(g.get());
		}
		// rq.remove();

		System.out.println("******");
		System.out.println(wr.isEnqueued());
		wr.enqueue();
		System.out.println(wr.isEnqueued());

		g = (Reference<QueueTest>) rq.poll();
		if (g != null) {
			System.out.println(g);
			System.out.println(g.get());
			System.out.println(rq.poll());
		}
	}

	private static <T> void printArray(T[] array) {
		if (array == null) {
			System.out.println("Empty!");
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i] + ", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		System.out.println("Array: " + sb.toString());
	}

}

class Recovery implements Runnable {

	private GenerateNum1 g1;
	private GenerateNum1 g2;

	Recovery(GenerateNum1 g1, GenerateNum1 g2) {
		this.g1 = g1;
		this.g2 = g2;
	}

	public void run() {
		System.out.println();
		g1 = g2;
	}
}
