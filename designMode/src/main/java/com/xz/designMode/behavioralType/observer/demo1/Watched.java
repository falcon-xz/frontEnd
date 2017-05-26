package com.xz.designMode.behavioralType.observer.demo1;

/**
 * falcon -- 2017/5/26.
 */
public interface Watched {
    void addWatcher(Watcher watcher);

    void removeWatcher(Watcher watcher);

    void notifyWatchers(String str);
}
