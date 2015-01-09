package com.rink.utils;

import java.util.Comparator;

import com.rink.xml.jaxb.model.Rink;

public class ComparatorForRink {
	
	public class RinkNameComparator implements Comparator<Rink> {
		
	    public int compare(Rink rink1, Rink rink2) {
	        return rink1.getName().compareToIgnoreCase(rink2.getName());
	    }
	}
	
	public class BoroughComparator implements Comparator<Rink>{

		public int compare(Rink rink1, Rink rink2) {
	        return rink1.getBorough().getKey().compareToIgnoreCase(rink2.getBorough().getKey());
	    }
	}
	
	public class RinkConditionComparator implements Comparator<Rink> {
		
	    public int compare(Rink rink1, Rink rink2) {
	        return rink1.getCondition().compareToIgnoreCase(rink2.getCondition());
	    }
	}
	
}
