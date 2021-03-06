package com.xz.common.utils.properties;

import com.xz.common.exception.PropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * falcon -- 2016/11/22.
 */
public class PropertiesUtil {
    private static Map<String, Properties> mapAll = new HashMap<>();

    public static Properties getProperties(String path)  {
        Properties properties = null ;
        if (mapAll.containsKey(path)) {
            properties = mapAll.get(path);
        } else {
            try {
                properties = parse(path) ;
            } catch (Exception e) {
                new PropertiesException(e) ;
            }
            mapAll.put(path, properties) ;
        }
        return properties;
    }

    private static Properties parse(String path) {
        Properties properties = new Properties();
        InputStream is = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream(path);
        try {
            properties.load(is);

            is.close();
            is = null ;
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (is != null) {
                try {
                    is.close();
                    is = null ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }


}
