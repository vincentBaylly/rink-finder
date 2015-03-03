package com.rink.xml.jaxb.model;

import javax.swing.ImageIcon;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "patinoire")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rink{
	
    public static final String LANDSCAPED_RINK = "PPL";
    public static final String OPEN_SKATE_RINK = "PP";
    public static final String TEAM_SPORT_RINK = "PSE";

    @XmlElement(name = "nom")
    private String name;

    @XmlElement(type=Borough.class, name = "arrondissement")
    private Borough borough;

    @XmlElement(name = "ouvert")
    private boolean opened;

    @XmlElement(name = "deblaye")
    private boolean cleared;

    @XmlElement(name = "arrose")
    private boolean sprayed;

    @XmlElement(name = "resurface")
    private boolean resurfaced;

    //TODO see if it can be an enum
    @XmlElement(name = "condition")
    private String condition;

    //TODO see if it can be an enum
    private String rinkType;

    @XmlTransient
    private ImageIcon imageIcon;

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public Borough getBorough() {
            return borough;
    }

    public void setBorough(Borough borough) {
            this.borough = borough;
    }

    public boolean isOpened() {
            return opened;
    }

    public void setOpened(boolean opened) {
            this.opened = opened;
    }

    public boolean isCleared() {
            return cleared;
    }

    public void setCleared(boolean cleared) {
            this.cleared = cleared;
    }

    public boolean isSprayed() {
            return sprayed;
    }

    public void setSprayed(boolean sprayed) {
            this.sprayed = sprayed;
    }

    public boolean isResurfaced() {
            return resurfaced;
    }

    public void setResurfaced(boolean resurfaced) {
            this.resurfaced = resurfaced;
    }

    public String getCondition() {
            return condition;
    }

    public void setCondition(String condition) {
            this.condition = condition;
    }

    public String getRinkType() {
            return rinkType;
    }

    public void setRinkType(String rinkType) {
            this.rinkType = rinkType;
    }
	
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
    
    @Override
    public String toString(){
    	
    	String rink = "name " +  name;
    	
    	rink += "borough " + borough;

    	rink += "opened " + opened;
    	
    	rink += "opened " + cleared;
    	
    	rink +=  "arrose " + sprayed;
    	
    	rink += "resurface " + resurfaced;
    	
    	rink += "condition " + condition;
    	
    	rink += "type of rink : " + rinkType;
    	
    	return rink;
    }
}
