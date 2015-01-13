package com.rink.xml.jaxb.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.rink.xml.jaxb.adapter.RinkAdapter;

@XmlRootElement(name="patinoires")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rinks{
	
	@XmlElement(type=Rink.class, name="patinoire")
    @XmlJavaTypeAdapter(RinkAdapter.class)
    private List<Rink> rinkList;
    
    public Rinks() {
    	rinkList = new ArrayList<Rink>();
    }
 
    public Rinks(List<Rink> rinkList) {
        this.rinkList = rinkList;
    }
    
	public List<Rink> getRinkList() {
		return rinkList;
	}
	
    public void setRinkList(List<Rink> rinkList) {
        this.rinkList = rinkList;
    }
	
}
