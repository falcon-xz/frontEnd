package com.xz.shell.utils;

import com.xz.shell.xml.XMLpo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * falcon -- 2017/1/20.
 */
public class ObjectUtils {
    public static String toString(Object o){
        StringBuilder sb = new StringBuilder() ;
        Class cz = o.getClass() ;
        sb.append(cz.getName()).append("{\n") ;
        Field[] fields = cz.getDeclaredFields();
        try {
            for (Field field:fields) {
                String fileName = field.getName() ;
                String methodName = "get"+fileName.substring(0,1).toUpperCase()+fileName.substring(1) ;
                Method method = cz.getMethod(methodName) ;
                String value = (String)method.invoke(o) ;
                sb.append(field.getName()).append(":").append(value).append("\n") ;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        sb.append("}") ;
        return sb.toString() ;
    }

    public static void main(String[] args) {
        XMLpo xmLpo = new XMLpo() ;
        xmLpo.setContent("1");
        System.out.println(ObjectUtils.toString(xmLpo));
    }
}
