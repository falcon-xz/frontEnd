package com.xz.designMode.behavioralType.observer.demo2;

/**
 * Created by xz on 2018/5/31.
 */
public class Main {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ServerManager sm = new ServerManager();
        AObserver a = new AObserver(sm);
        BObserver b = new BObserver(sm);
        sm.setData(5);
        sm.deleteObserver(a);//注销观察者，以后被观察者有数据变化就不再通知这个已注销的观察者
        sm.setData(10);
    }
}
