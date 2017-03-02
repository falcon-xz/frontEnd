package com.xz.util.compare.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

@XmlRootElement(name="configuration")
public class Configuration{

    private File file ;

    private List<Property> properties;

    public Configuration() {
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Configuration(List<Property> properties) {
        this.properties = properties;
    }

    @XmlElement(name="property")
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Property> getProperties() {
        return properties;
    }
}