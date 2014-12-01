package algorithm;

import java.util.Random;

/**
 *
 * 给你一个数组A[0..n]，请你在O(n)的时间里构造一个新的数组B[0..n]，使得B[i]=A[0]*A[1]*A[2]*…*A[n]/A[i]，但是不能使用除法运算。
 * 
 * Created by Luonanqin on 12/1/14.
 */
public class ArrayA2ArrayB {

	public static int[] buildArrayB(int[] arrayA) {
		int len = arrayA.length;
		int[] order = new int[len];
		int[] invertedOrder = new int[len];

		order[0] = 1;
		invertedOrder[len - 1] = 1;
		for (int i = 1; i < len; i++) {

			// 计算从前到后依次的乘积，即
			// order[1] = order[0] * A[1]
			// order[2] = order[0] * A[1] * A[2] = order[1] * A[2]
			// 直到order[n]=order[n-2]*A[n-1]
			order[i] = arrayA[i - 1] * order[i - 1];

			// 计算从后到前的乘积，即
			// invertedOrder[n-1] = invertedOrder[n] * A[n]
			// invertedOrder[n-2] = invertedOrder[n] * A[n] * A[n-1] = invertedOrder[n-1] * A[n-1]
			// 直到invertedOrder[0] = invertedOrder[1] * A[1]
			invertedOrder[len - i - 1] = arrayA[len - i] * invertedOrder[len - i];
		}

		int[] arrayB = new int[arrayA.length];
		for (int i = 0; i < arrayB.length; i++) {
			arrayB[i] = order[i] * invertedOrder[i];
		}

		return arrayB;
	}

	public static void main(String[] args) {
		int[] arrayA = new int[10];
		Random r = new Random(System.currentTimeMillis());

		for (int i = 0; i < arrayA.length; i++) {
			int num = 0;
			// 随机产生小于10的数值，但不能为0
			while ((num = r.nextInt(10)) == 0)
				;
			arrayA[i] = num;
		}
		// 打印数组A
		System.out.print("Array A: ");
		for (int i = 0; i < arrayA.length; i++) {
			System.out.print(arrayA[i]);
			if (i < arrayA.length - 1) {
				System.out.print(", ");
			}
		}

		System.out.println();

		// 打印数组B
		int[] arrayB = buildArrayB(arrayA);
		System.out.print("Array B: ");
		for (int i = 0; i < arrayB.length; i++) {
			System.out.print(arrayB[i]);
			if (i < arrayB.length - 1) {
				System.out.print(", ");
			}
		}
	}
}
