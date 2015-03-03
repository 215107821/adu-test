package com.adu.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class ConcurrentHashMapTest {
	private final Log logger = LogFactory.getLog(this.getClass());

	@Test
	public void test() {
		ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<Integer, String>();
		map.put(1, "a");
		map.put(2, "b");
		logger.debug("map1=" + map);

		Iterator<Map.Entry<Integer, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, String> entry = iter.next();
			if (entry.getKey() < 10) {
				iter.remove();
			}
		}
		logger.debug("map2=" + map);
		map.put(3, "b");
		logger.debug("map3=" + map);
	}

	@Test
	public void test1() throws InterruptedException {
		final Map<Integer, String> map = new ConcurrentHashMap<Integer, String>();

		for (int i = 0; i < 1000; i++) {
			new Thread() {
				public void run() {
					for (int i = 0; i < 100; i++) {
						map.put(i, i + "");
					}
				};

			}.start();
		}
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			logger.debug(entry);
		}
		Thread.sleep(10000);
		List<Integer> keyList = new ArrayList<Integer>(map.keySet());
		Collections.sort(keyList);
		logger.debug("size=" + map.size() + ",keys=" + keyList);

	}
}
