package com.xz.util.compare.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Object property
 */
@XmlRootElement(name="property")
public class Property {

    private String name;

    private String value;

    public Property() {
    }

    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    @XmlElement
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Property prprty = (Property) o;

        if (name != null ? !name.equals(prprty.name) : prprty.name != null) return false;
        return value != null ? value.equals(prprty.value) : prprty.value == null;

    }

}