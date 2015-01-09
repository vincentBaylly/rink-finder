package com.rink.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rink.app.Runner;
import com.rink.utils.RinkUtils;
import com.rink.xml.jaxb.model.Borough;
import com.rink.xml.jaxb.model.Rink;
import com.rink.xml.jaxb.model.Rinks;

public class ApplicationTest extends TestCase{
	
	private static final String RINKS_XML = "src/test/resources/rinks-jaxb.xml";
	
	private static final Log LOG = LogFactory.getLog(Runner.class);
	
	public static void testMarshalling() throws JAXBException, IOException {
		
		List<Rink> rinkList = initRinks();
		Rinks rinks = new Rinks();
//		rinks.set

		// create JAXB context and instantiate marshaller
		JAXBContext context = JAXBContext.newInstance(Rinks.class, Rink.class, Borough.class);

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		marshaller.marshal(rinkList, System.out);

		// Write to File
		marshaller.marshal(rinkList, new FileOutputStream(RINKS_XML));

		// get variables from our xml file, created before
		LOG.info("Output from our XML File: ");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		Rinks rinks2 = (Rinks)unmarshaller.unmarshal(new File(RINKS_XML));
		List<Rink> list = rinks2.getRinkList();
//
		for (Rink rink : list) {
			LOG.info("Rink: " + rink.getName() + " condition " + rink.getCondition());
		}

	}

	public static List<Rink> initRinks() {

		ArrayList<Rink> rinkList = new ArrayList<Rink>();
		
		//create the borough
		Borough boroughCDN = new Borough();
		boroughCDN.setKey("cdn");
		boroughCDN.setName("CÃ´te-des-Neiges - Notre-Dame-de-GrÃ¢ce");
		boroughCDN.setUpdated(RinkUtils.parseDate("2015-01-05 16:52:00"));

		Borough boroughOut = new Borough();
		boroughOut.setKey("out");
		boroughOut.setName("CÃ´te-des-Neiges - Notre-Dame-de-GrÃ¢ce");
		boroughOut.setUpdated(RinkUtils.parseDate("2015-01-06 14:02:11"));

		// create the rinks
		Rink rink1 = new Rink();
		rink1.setName("Aire de patinage libre, Kent (sud) (PSE)");
		rink1.setBorough(boroughCDN);
		rink1.setOpened(false);
		rink1.setCleared(false);
		rink1.setSprayed(false);
		rink1.setResurfaced(false);
		rink1.setCondition("Mauvaise");
		rinkList.add(rink1);

		Rink rink2 = new Rink();
		rink2.setName("Grande patinoire de hockey, Parc Beaubien (PSE)");
		rink2.setBorough(boroughOut);
		rink2.setOpened(false);
		rink2.setCleared(false);
		rink2.setSprayed(false);
		rink2.setResurfaced(false);
		rink2.setCondition("Mauvaise");
		rinkList.add(rink2);

		Rink rink3 = new Rink();
		rink3.setName("Aire de patinage libre, William-Hurst (PP)");
		rink3.setBorough(boroughCDN);
		rink3.setOpened(false);
		rink3.setCleared(false);
		rink3.setSprayed(false);
		rink3.setResurfaced(false);

		rink3.setCondition("Mauvaise");
		rinkList.add(rink3);

		Rink rink4 = new Rink();
		rink4.setName("Aire de patinage libre, De la Savane (PPL)");
		rink4.setBorough(boroughCDN);
		rink4.setOpened(false);
		rink4.setCleared(false);
		rink4.setSprayed(false);
		rink4.setResurfaced(false);
		rink4.setCondition("Mauvaise");
		rinkList.add(rink4);

		return rinkList;

	}

}
