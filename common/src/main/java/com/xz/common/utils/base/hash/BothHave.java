package com.xz.common.utils.base.hash;

import java.util.Collection;
import java.util.HashSet;

/**
 ************ 相等的对象必须有相等的hashcode  ************
 ************ hashcode不等   equals一定不等   指未重写的equals ************
 ************ hashcode相等   equals不一定相等 ************
 * hashcode是用于散列数据的快速存取，
 * 如利用HashSet/HashMap/Hashtable类来存储数据时，
 * 都是根据存储对象的hashcode值来进行判断是否相同的
 * 此例 hashcode相等   不判断 equals 认定为不同user对象
 */
public class BothHave {

    public static void main(String[] args) {
        Collection<User> c = new HashSet<>();
        User u1 = new User("xz",12);
        User u2 = new User("xz",12);
        User u3 = new User("xz",12);
        c.add(u1);
        c.add(u2);
        c.add(u3);
        for (User u:c) {
            System.out.println(u);
        }
    }


    static class User{
        public User(String name, int ager) {
            this.name = name;
            this.ager = ager;
        }

        private String name ;
        private int ager ;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAger() {
            return ager;
        }

        public void setAger(int ager) {
            this.ager = ager;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;

            if (ager != user.ager) return false;
            return name.equals(user.name) ;

        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", ager=" + ager +
                    '}';
        }

    }
}
