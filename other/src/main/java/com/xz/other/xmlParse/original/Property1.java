package com.xz.other.xmlParse.original;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="property1")
@XmlAccessorType(XmlAccessType.FIELD)//代表不用get和set @XmlAttribute不需要放到set方法上
public class Property1 {
    @XmlAttribute(name = "type")
    private String type;
    @XmlElement(name = "value")
    private String value;
    @XmlElement(name = "property2")
    private Property2 property2 ;

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Property2 getProperty2() {
        return property2;
    }
}