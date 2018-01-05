package com.oruit.app.util;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BatTest {
	// 在指定范围内产生指定个数的不同随机数
	public Set getRandom(int num, int length) {
		Set<Integer> hs = new TreeSet<Integer>();
		while (true) {
			hs.add((int) (Math.random() * (num) + 1));
			if (hs.size() >= length) {
				break;
			}
		}
		return hs;
	}
	// test method
	public static void main(String[] args) {
		String substring = getString(33,6);
		String substring1 = getString(16,1);
		System.out.println(substring);
		System.out.println(substring1);
	}

	public static String getString(Integer one, Integer two) {
		StringBuffer stringBuffer = new StringBuffer();
		BatTest bt = new BatTest();
		Set hs = new TreeSet();
		hs = (TreeSet) bt.getRandom(one, two);
		Iterator it = hs.iterator();
		while (it.hasNext()) {
			String next = it.next().toString();
			String format = String.format("%02d", Integer.parseInt(next));
			stringBuffer.append(format+" ");

		}
		return stringBuffer.substring(0, stringBuffer.length() - 1);
	}
}
