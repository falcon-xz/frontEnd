package com.xz.newland.baseknow.hook.demo1;

/**
 * falcon -- 2016/11/17.
 */
public class Teacher {

    private MyHook myHook ;

    public void addHook(MyHook myHook){
        this.myHook = myHook ;
    }

    public String askStudent(){
        myHook.before();
        Student student = new Student() ;
        return student.answer(this);
    }

    public int getNum(){
        return myHook.after() ;
    }
}
