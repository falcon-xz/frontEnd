package com.xz.rest.jersey.po;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {

	private int id;
	private String name;
	private int age;

	public Student() {

	}

	public Student(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id:").append(id).append(",").append("name:").append(name)
				.append(",").append("age:").append(age);
		return sb.toString();
	}

}
