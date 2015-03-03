package com.rink.app;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rink.utils.ComparatorForRink;
import com.rink.utils.RinkUtils;
import com.rink.xml.jaxb.model.Borough;
import com.rink.xml.jaxb.model.Rink;
import com.rink.xml.jaxb.model.Rinks;
import java.io.IOException;

public class Treatment {
	
	private static final Log LOG = LogFactory.getLog(Treatment.class);
	
	private static final String MTL_WEBSITE = "www2.ville.montreal.qc.ca";

	private static final String RINKS_XML = "/rinks-jaxb.xml";

	private static final String RINKS_XML_URL = "http://www2.ville.montreal.qc.ca/services_citoyens/pdf_transfert/L29_PATINOIRE.xml";

	private static final ComparatorForRink comparatorForRink = new ComparatorForRink();
	
	private Rinks rinks;
	
	private List<Borough> availableBorough;
	
	public Rinks getRinks() {
		return rinks;
	}

	public void setRinks(Rinks rinks) {
		this.rinks = rinks;
	}
	
	public List<Borough> getAvailableBorough() {
		return availableBorough;
	}

	public void setAvailableBorough(List<Borough> availableBorough) {
		this.availableBorough = availableBorough;
	}

	public void initRinks() {

		Rinks rinks = null;

		try {
			// create JAXB context and instantiate marshaller
			JAXBContext context = null;
			context = JAXBContext.newInstance(Rinks.class, Rink.class, Borough.class);

			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			if (RinkUtils.isInternetReachable(MTL_WEBSITE)) {

				URL u = new URL(RINKS_XML_URL);
				InputStream in = u.openStream();
				
				setRinks((Rinks) unmarshaller.unmarshal(in));
				
				LOG.debug(rinks);
				
				// Write to File
				//marshaller.marshal(getRinks(), new FileOutputStream(new File(RINKS_XML)));
			} else {

				setRinks((Rinks) unmarshaller.unmarshal(new File(RINKS_XML)));
			}
			
			//set the borough available in the list of rinks
			Collections.sort(getRinks().getRinkList(), comparatorForRink.new BoroughComparator());
			List<Borough> boroughAvailable = new ArrayList<Borough>();
			String boroughKey = "";
			
			for(Rink rink : getRinks().getRinkList()){
				if(!boroughKey.equalsIgnoreCase(rink.getBorough().getKey())){
					boroughAvailable.add(rink.getBorough());
					boroughKey = rink.getBorough().getKey();
				}
			}
			
			setAvailableBorough(boroughAvailable);

		} catch (JAXBException ex) {
			LOG.error("jaxb Exception on rink marshalling element", ex);
		} catch (IOException ex) {
			LOG.error("IOException on rink marshalling element", ex);
		}

	}
	
	public Rinks getRinksFromBorough(String borough){
		
		Collections.sort(getRinks().getRinkList(), comparatorForRink.new BoroughComparator());
		
		Rinks rinksFromBorough = new Rinks();
		rinksFromBorough.setRinkList(new ArrayList<Rink>());
		
		for(Rink rink : getRinks().getRinkList()){
			if(borough.equalsIgnoreCase(rink.getBorough().getKey())){
				rinksFromBorough.getRinkList().add(rink);
			}
		}
		
		return rinksFromBorough;
	}
	
	public Rinks getRinks(String sortType){
		
		if("borough".equalsIgnoreCase(sortType)){
			Collections.sort(getRinks().getRinkList(), comparatorForRink.new BoroughComparator());
		}else if("condition".equalsIgnoreCase(sortType)){
			Collections.sort(getRinks().getRinkList(), comparatorForRink.new RinkConditionComparator());
		}
		
		return rinks;
	}
}
