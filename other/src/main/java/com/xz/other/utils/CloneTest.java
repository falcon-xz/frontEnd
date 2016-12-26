package com.xz.other.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * clone不会受到构造函数的性能影响
 * 但是为 浅copy
 * falcon -- 2016/12/22.
 */
public class CloneTest implements Cloneable {
    private String name;
    private List<String> list;
    private Integer age ;

    public CloneTest() {
        try {
            Thread.sleep(2000);
            System.out.println("等待");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        CloneTest cloneTest = (CloneTest) super.clone();
        List<String> list = cloneTest.getList();
        List<String> list2 = new ArrayList<>();
        for (String s : list) {
            list2.add(s);
        }
        cloneTest.setList(list2);
        String s = cloneTest.getName() ;
        cloneTest.setName(new String(s));
        cloneTest.setAge(10);
        return cloneTest;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        CloneTest cloneTest = new CloneTest();
        cloneTest.setList(list);
        cloneTest.setName("hello");
        cloneTest.setAge(10);
        CloneTest cloneTest2 = null;
        try {
            cloneTest2 = (CloneTest) cloneTest.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (cloneTest2 != null) {
            System.out.println(cloneTest2.getName() == cloneTest.getName());
            System.out.println(cloneTest2.getList() == cloneTest.getList());
            System.out.println(cloneTest2.getAge() == cloneTest.getAge());
        }
    }
}
