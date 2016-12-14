package com.xz.rest.jersey.config;


import com.xz.rest.jersey.po.Student;

import java.util.*;

public class DataBase {
	private static Map<Long, Student> map;
	private static int index = 0;

	static {
		map = new HashMap<Long, Student>();
		for (int i = 0; i < 10; i++) {
			map.put((long) i, new Student((long) i, "xz" + i, 10 + i));
			index++;
		}
	}

	public static long put(String name, int age) {
		index++;
		map.put((long) index, new Student((long) index, name, age));
		return index;
	}

	public static boolean delete(long id) {
		map.remove(id);
		return true;
	}

	public static Student get(long id) {
		Student student = map.get(id);
		return student;
	}

	public static List<Student> getAll() {
		List<Student> list = new ArrayList<Student>();
		Iterator<Long> it = map.keySet().iterator();
		while (it.hasNext()) {
			Long key = it.next();
			Student student = map.get(key);
			list.add(student);
		}
		Collections.sort(list, new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				return o1.getId() > o2.getId() ? 1 : -1;
			}
		});
		return list;
	}

	public static boolean update(long id, Student student) {
		map.put(id, student);
		return true;
	}
}
