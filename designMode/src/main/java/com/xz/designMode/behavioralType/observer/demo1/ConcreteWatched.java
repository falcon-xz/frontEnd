package com.xz.designMode.behavioralType.observer.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * falcon -- 2017/5/26.
 */
public class ConcreteWatched implements Watched {
    // 存放观察者
    private List<Watcher> list = new ArrayList<>();

    @Override
    public void addWatcher(Watcher watcher) {
        list.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        list.remove(watcher);
    }

    @Override
    public void notifyWatchers(String str) {
        // 自动调用实际上是主题进行调用的
        for (Watcher watcher : list) {
            watcher.update(str);
        }
    }

}
