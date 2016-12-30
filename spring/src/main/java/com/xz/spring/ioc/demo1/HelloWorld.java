package com.xz.spring.ioc.demo1;

import com.xz.spring.SpringContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * falcon -- 2016/12/26.
 */
public class HelloWorld {
    private String word ;
    public HelloWorld(){
        word = "world" ;
    }
    public HelloWorld(String s){
        this.word = s ;
    }

    public void init(){
        if (word.equals("world")){
            System.out.println(1);
        }else{
            System.out.println(2);
        }
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("springtest/demo1.xml") ;
        HelloWorld helloWorld = SpringContextHolder.getBean("helloWorld") ;
        System.out.println("Word"+helloWorld.getWord());
    }
}
