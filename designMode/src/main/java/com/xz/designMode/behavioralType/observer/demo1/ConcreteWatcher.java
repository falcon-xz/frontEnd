package com.xz.designMode.behavioralType.observer.demo1;

/**
 * falcon -- 2017/5/26.
 */
public class ConcreteWatcher implements Watcher {

    @Override
    public void update(String str) {
        System.out.println(str);
    }

}