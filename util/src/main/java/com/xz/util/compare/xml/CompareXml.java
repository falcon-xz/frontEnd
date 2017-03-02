package com.xz.util.compare.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompareXml {

    public static void main(String[] args) {
        String path1 = "C:\\Users\\Administrator\\Desktop\\hbase-hue\\NCMP\\hdfs-site.xml";
        String path2 = "C:\\Users\\Administrator\\Desktop\\hbase-hue\\NMDBP\\hdfs-site.xml";


        System.out.println(path1);
        System.out.println(path2);

        File file1 = new File(path1);
        File file2 = new File(path2);

        Configuration cf1 = XMLStringToBean(file1);
        Configuration cf2 = XMLStringToBean(file2);

        if (cf1==null || cf2==null){
            System.out.println(cf1==null);
            System.out.println(cf2==null);
            return ;
        }

        String s = getDiffrent(cf1, cf2);

        System.out.println(s);
    }

    public static String getDiffrent(Configuration cf1, Configuration cf2 ) {
        Map<String,String> map = new HashMap<>() ;
        List<Property> list1 = cf1.getProperties() ;
        List<Property> list2 = cf2.getProperties() ;
        for (Property property:list1) {
            map.put(property.getName(),property.getValue()) ;
        }
        for (Property property:list2) {
            String value = map.get(property.getName()) ;
            if (value==null){
                map.put(property.getName(),"2--"+property.getValue()) ;
            }else{
                if (value.equals(property.getValue())){
                    map.remove(property.getName()) ;
                }else{
                    map.put(property.getName(),"1--"+value+";2--"+property.getValue());
                }
            }

        }
        StringBuilder sb = new StringBuilder() ;
        for (Map.Entry<String,String> entry:map.entrySet()) {
            sb.append("key="+entry.getKey()+",value="+entry.getValue()).append("\r\n") ;
        }
        return sb.toString();
    }

    /**
     * xml tranto bean object
     *
     * @param file
     * @return
     */
    public static Configuration XMLStringToBean(File file) {
        Configuration configuration = null ;
        try {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            configuration = (Configuration) unmarshaller.unmarshal(file);
            configuration.setFile(file);
            return configuration;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return configuration;
    }
}


