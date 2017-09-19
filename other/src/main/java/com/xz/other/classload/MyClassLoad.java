package com.xz.other.classload;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017-9-19.
 */
public class MyClassLoad extends ClassLoader{
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            //System.out.println(fileName);
            InputStream is = getClass().getResourceAsStream(fileName);
            //System.out.println(is);
            if (is == null) {
                return super.loadClass(name);
            }
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
}
