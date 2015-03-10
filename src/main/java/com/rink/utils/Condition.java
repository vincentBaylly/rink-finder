package com.rink.utils;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum(String.class)
public enum Condition {
	
	@XmlEnumValue("Excellente")
	EXCELLENT(1, "Excellente"),
	@XmlEnumValue("Bonne")
	BONNE(2, "Bonne"),
	@XmlEnumValue("Mauvaise")
	MAUVAISE(3, "Mauvaise"),
	@XmlEnumValue("N/A")
	NA(4, "N/A");
	
	private final int order;
	private final String label;
	
	Condition(int order, String label){
		this.order = order;
		this.label = label;
	}
	
	public Integer getOrder() {
		return order;
	}

	public String getLabel() {
		return label;
	}
}
