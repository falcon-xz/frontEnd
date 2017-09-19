package com.xz.other.classload;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassLoaderTest that = (ClassLoaderTest) o;

        if (age != that.age) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }

    private int age;

    public ClassLoaderTest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new MyClassLoad();
        String className = "com.xz.other.classload.ClassLoaderTest";
        Object obj1 = myLoader.loadClass(className).getConstructor(String.class, int.class).newInstance("testMy", 100);
        Object obj2 = ClassLoader.getSystemClassLoader().loadClass(className).getConstructor(String.class, int.class).newInstance("testMy", 100);
        System.out.println(obj1.getClass());
        System.out.println(obj2.getClass());
        System.out.println(obj1 instanceof ClassLoaderTest);
        System.out.println(obj2 instanceof ClassLoaderTest);
        System.out.println("equals: " + obj1.equals(obj2));
        System.out.println(obj1.getClass()==obj2.getClass());
    }

}
