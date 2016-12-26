package com.xz.rpc.baseknow.masterwork;

/**
 * falcon -- 2016/12/22.
 */
public class PlusWork extends Work {
    @Override
    public Object handle(Object input) {
        Integer i = (Integer)input ;
        return i;
    }
}
