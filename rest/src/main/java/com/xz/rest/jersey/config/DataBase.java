package com.xz.rest.jersey.config;


import com.xz.rest.jersey.po.Student;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBase {
	private static Map<Integer, Student> map;
	private static AtomicInteger index ;

	static {
		map = new ConcurrentHashMap<>();
		index = new AtomicInteger(0);
		for (int i = 0; i < 10; i++) {
			int id = index.addAndGet(1) ;
			Student s = new Student(id, "邢舟" + id, 10 + id) ;
			map.put(s.getId(), s);
		}
	}

	public static int put(String name, Integer age) {
		if (name == null || age == null){
			return 0 ;
		}
		int id = index.addAndGet(1) ;
		Student s = new Student(id, "邢舟" + id, 10 + id) ;
		map.put(s.getId(), s);
		return id;
	}

	public static boolean delete(int id) {
		boolean bo = false ;
		if (map.containsKey(id)){
			map.remove(id);
			bo = true ;
		}
		return bo;
	}

	public static Student get(int id) {
		return map.get(id);
	}

	public static List<Student> getAll() {
		List<Student> list = new ArrayList<>();
		Iterator<Integer> it = map.keySet().iterator();
		while (it.hasNext()) {
			int key = it.next();
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

	public static boolean update(int id, String name,int age) {
		boolean bo = false ;
		if (map.containsKey(id)){
			Student s = map.get(id) ;
			s.setName(name);
			s.setAge(age);
			map.put(id,s) ;
			bo = true ;
		}
		return bo;
	}
}
