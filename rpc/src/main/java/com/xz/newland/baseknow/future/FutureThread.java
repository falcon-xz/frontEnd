package com.xz.newland.baseknow.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * falcon -- 2016/11/15.
 */
public class FutureThread extends FutureTask{

    public FutureThread(Callable callable) {
        super(callable);
    }

    public FutureThread(Runnable runnable, Object result) {
        super(runnable, result);
    }
}
