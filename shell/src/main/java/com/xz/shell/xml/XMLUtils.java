package com.xz.shell.xml;

import com.xz.common.utils.reflect.ObjectUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * falcon -- 2017/1/20.
 */
public class XMLUtils {

    public static XMLpo parse(Reader reader){
        XMLpo xmLpo = null;
        try {
            xmLpo = new XMLpo();
            Class cz = XMLpo.class ;
            SAXBuilder saxBuilder = new SAXBuilder() ;
            Document document = saxBuilder.build(reader) ;
            Element root = document.getRootElement();
            List<Element> list = root.getChildren() ;
            for (Element e:list) {
                String name = e.getName() ;
                String methodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1,name.length()) ;
                Method method = cz.getMethod(methodName,String.class) ;
                method.invoke(xmLpo,e.getText()) ;
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return xmLpo ;
    }

    public static XMLpo parse(InputStream inputStream){
        XMLpo xmLpo = null;
        try {
            xmLpo = new XMLpo();
            Class cz = XMLpo.class ;
            SAXBuilder saxBuilder = new SAXBuilder() ;
            Document document = saxBuilder.build(inputStream) ;
            Element root = document.getRootElement();
            List<Element> list = root.getChildren() ;
            for (Element e:list) {
                String name = e.getName() ;
                String methodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1,name.length()) ;
                Method method = cz.getMethod(methodName,String.class) ;
                method.invoke(xmLpo,e.getText()) ;
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return xmLpo ;
    }

    public static XMLpo parse(String xml){
        XMLpo xmLpo = null;
        try {
            xmLpo = new XMLpo();
            Class cz = XMLpo.class ;
            SAXBuilder saxBuilder = new SAXBuilder() ;
            InputStream is = new ByteArrayInputStream(xml.getBytes()) ;
            Document document = saxBuilder.build(is) ;
            Element root = document.getRootElement();
            List<Element> list = root.getChildren() ;
            for (Element e:list) {
                String name = e.getName() ;
                String methodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1,name.length()) ;
                Method method = cz.getMethod(methodName,String.class) ;
                method.invoke(xmLpo,e.getText()) ;
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return xmLpo ;
    }

    public static void main(String[] args) {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                +"<root>"
                +"<ip>1</ip>"
                +"<type>2</type>"
                +"<date>3</date>"
                +"<content>5</content>"
                +"</root>" ;
        System.out.println(s);
        XMLpo xmLpo = XMLUtils.parse(new ByteArrayInputStream(s.getBytes()));
        System.out.println(ObjectUtils.println(xmLpo));
    }

}
