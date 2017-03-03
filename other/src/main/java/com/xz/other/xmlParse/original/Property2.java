package com.xz.other.xmlParse.original;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "property2")
@XmlAccessorType(XmlAccessType.FIELD)
public class Property2 {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "age")
    private String age;

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }
}