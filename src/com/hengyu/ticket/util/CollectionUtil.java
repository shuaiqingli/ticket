package com.hengyu.ticket.util;

import java.lang.reflect.Field;
import java.util.*;

public class CollectionUtil {

    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (null != obj) {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        }
        return map;
    }

    /**
     * 列表去重
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        Set<T> set = new HashSet<T>();
        List<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }

    /**
     * 转换数组到字符串
     */
    public static <T> String arrayToString(T[] array, String separator) {
        StringBuilder sb = new StringBuilder();
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                if (i == 0) {
                    sb.append(array[i]);
                } else {
                    sb.append(separator);
                    sb.append(array[i]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 转换列表到字符串
     */
    public static <T> String listToString(List<T> list, String separator) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append(list.get(i));
                } else {
                    sb.append(separator);
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 转换MAP到字符串
     */
    @SuppressWarnings("unchecked")
    public static <K, V, T> String mapToString(Map<K, V> map) {
        StringBuilder sb = new StringBuilder();
        for (K key : map.keySet()) {
            if (null == key) {
                continue;
            }
            V value = map.get(key);
            if (sb.length() > 0) {
                sb.append("&");
            }
            if (value instanceof List) {
                sb.append(key.toString()).append("=").append(listToString((List) value, ","));
            } else if (value.getClass().isArray()) {
                sb.append(key.toString()).append("=").append(arrayToString((T[]) value, ","));
            } else {
                sb.append(key.toString()).append("=").append(value);
            }
        }
        return sb.toString();
    }

    public static <T> boolean contain(T[] array, T object) {
        if (array == null || object == null) return false;
        for (T item : array) {
            if (object.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }
    
    /**
     * 去掉逗号
     * @param set
     * @return
     */
    public static <T> String removeComma(Set<T> set){
    	StringBuffer sb = new StringBuffer();
    	Iterator<T> it = set.iterator();
    	while(it.hasNext()){
    		sb.append(it.next());
    	}
    	return sb.toString();
    }
}
