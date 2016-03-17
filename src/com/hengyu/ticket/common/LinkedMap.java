package com.hengyu.ticket.common;

import java.util.LinkedHashMap;

public class LinkedMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;
	
	public LinkedMap<K, V> add(K key, V value) {
		super.put(key, value);
		return this;
	}

}
