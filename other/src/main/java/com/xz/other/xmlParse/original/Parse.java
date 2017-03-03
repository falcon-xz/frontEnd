package com.xz.other.xmlParse.original;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * falcon -- 2017/3/3.
 */
public class Parse {

    public static Root getRoot(InputStream is){
        Root root = null ;
        try {
            JAXBContext context = JAXBContext.newInstance(Root.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            root = (Root) unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return root ;
    }

}
