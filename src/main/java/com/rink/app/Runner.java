package com.rink.app;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rink.utils.ComparatorForRink;
import com.rink.utils.RinkUtils;
import com.rink.xml.jaxb.model.Rink;
import com.rink.xml.jaxb.model.impl.Borough;
import com.rink.xml.jaxb.model.impl.Rinks;

public class Runner {

	private static final String MTL_WEBSITE = "www2.ville.montreal.qc.ca";

	private static final String RINKS_XML = "src/main/resources/rinks-jaxb.xml";

	private static final String RINKS_XML_URL = "http://www2.ville.montreal.qc.ca/services_citoyens/pdf_transfert/L29_PATINOIRE.xml";

	private static final Log LOG = LogFactory.getLog(Runner.class);
	
	private static final ComparatorForRink comparatorForRink = new ComparatorForRink();

	/**
	 * @param args
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) {
		
		try {
			// create JAXB context and instantiate marshaller
			JAXBContext context = null;
			context = JAXBContext.newInstance(Rinks.class, Rink.class, Borough.class);
	
			
			Unmarshaller unmarshaller = null;
	
			unmarshaller = context.createUnmarshaller();
			Rinks rinks = null;
			
			if(RinkUtils.isInternetReachable(MTL_WEBSITE)){

				URL u = new URL(RINKS_XML_URL);
				InputStream  in = u.openStream();
				rinks = (Rinks) unmarshaller.unmarshal(in);
			}else{
	
				rinks = (Rinks)unmarshaller.unmarshal(new File(RINKS_XML));
			}
			
			//Sort by Rink Name
//			Collections.sort(rinks.getRinkList(), comparatorForRink.new RinkNameComparator());
//			printRinks(rinks);
			
			//Sort by Borough Name
			Collections.sort(rinks.getRinkList(), comparatorForRink.new BoroughComparator());
			printRinks(rinks);
			
			//Sort by Rink Condition
//			Collections.sort(rinks.getRinkList(), comparatorForRink.new RinkConditionComparator());
//			printRinks(rinks);
			
			
		
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printRinks(Rinks rinks){
		
		String lastBorough = "";
		
		for(Rink rink : (List<Rink>)rinks.getRinkList()){
			
			if(!lastBorough.equalsIgnoreCase(rink.getBorough().getKey())){
				LOG.info("\n-----------------------------------------------");
				LOG.info("Updated at : " + rink.getBorough().getUpdated());
				LOG.info("-----------------------------------------------\n");
				lastBorough = rink.getBorough().getKey();
			}
			
			if(Rink.TEAM_SPORT_RINK.equalsIgnoreCase(rink.getRinkType())){
				LOG.info("         ");
				LOG.info("\\       "+ rink.getName());
				LOG.info(" \\      " + rink.getBorough().getName());
				LOG.info("  \\_O_  " + rink.getCondition());
			}else if(Rink.LANDSCAPED_RINK.equalsIgnoreCase(rink.getRinkType())){
				LOG.info("     __  ");
				LOG.info("    (  ) " + rink.getName());
				LOG.info("| |__||  " + rink.getBorough().getName());
				LOG.info("|_____)  " + rink.getCondition());
				LOG.info(" _|_|_)  ");
				
			}else if(Rink.OPEN_SKATE_RINK.equalsIgnoreCase(rink.getRinkType())){
				LOG.info("         ");
				LOG.info("| |__    " + rink.getName());
				LOG.info("|_____)  " + rink.getBorough().getName());
				LOG.info(" _|_|_)  " + rink.getCondition());
			}
			
		}
	}
}
