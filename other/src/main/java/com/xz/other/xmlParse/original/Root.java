package com.xz.other.xmlParse.original;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="root")
public class Root {

    private List<Property1> properties1;

    public List<Property1> getProperties1() {
        return properties1;
    }
    @XmlElement(name="property1")
    public void setProperties1(List<Property1> properties1) {
        this.properties1 = properties1;
    }
}