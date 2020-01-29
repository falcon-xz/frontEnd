package com.xz.util.protostuff;

import com.xz.util.json.Ret;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by xz on 2020/1/29.
 */
public class ProtostuffUtilsTest {
    private Ret ret ;
    private List<Ret> list ;
    @Before
    public void setUp() throws Exception {
        ret = new Ret() ;
        ret.setErrcode(1);
        ret.setErrmsg("1");

        list = new ArrayList<>() ;
        for (int i = 0; i < 100; i++) {
            Ret ret = new Ret() ;
            ret.setErrcode(i);
            ret.setErrmsg("msg"+i);
            list.add(ret) ;
        }
    }

    @Test
    public void serialize() throws Exception {
        byte[] bytes = ProtostuffUtils.serialize(ret) ;
        Ret ret2 = ProtostuffUtils.deserialize(bytes,Ret.class) ;
        System.out.println(ret2);
    }


    @Test
    public void serializeList() throws Exception {
        byte[] bytes = ProtostuffUtils.serializeList(list) ;
        List<Ret> list2 = ProtostuffUtils.deserializeList(bytes,Ret.class) ;
        list2.forEach(System.out::println);
    }


}