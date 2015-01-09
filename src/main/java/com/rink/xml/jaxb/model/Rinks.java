package com.rink.xml.jaxb.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.rink.xml.jaxb.adapter.RinkAdapter;

@XmlRootElement(name="patinoires")
public class Rinks{
	
    private List<Rink> rinkList;
    
    public Rinks() {
    	rinkList = new ArrayList<Rink>();
    }
 
    public Rinks(List<Rink> rinkList) {
        this.rinkList = rinkList;
    }
    
    @XmlElement(type=Rink.class, name="patinoire")
    @XmlJavaTypeAdapter(RinkAdapter.class)
	public List<Rink> getRinkList() {
		return rinkList;
	}

}
