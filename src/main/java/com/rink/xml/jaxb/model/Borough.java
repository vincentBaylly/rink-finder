package com.rink.xml.jaxb.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.rink.xml.jaxb.adapter.DateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Borough {
	
    @XmlElement(name = "cle")
    private String key;

    @XmlElement(name = "nom_arr")
    private String name;

    @XmlElement(name = "date_maj")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date updated;

    public String getKey() {
            return key;
    }

    public void setKey(String key) {
            this.key = key;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public Date getUpdated() {
            return updated;
    }

    public void setUpdated(Date updated) {
            this.updated = updated;
    }

    @Override
    public String toString(){
        return name;
    }
}
