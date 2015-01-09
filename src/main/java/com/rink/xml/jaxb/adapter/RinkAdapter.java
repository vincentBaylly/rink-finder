package com.rink.xml.jaxb.adapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.rink.xml.jaxb.model.Rink;

public class RinkAdapter extends XmlAdapter<Object, Rink> {

	private DocumentBuilder documentBuilder;
	private JAXBContext jaxbContext;

	public RinkAdapter() {
	}

	private DocumentBuilder getDocumentBuilder() throws Exception {
		// Lazy load the DocumentBuilder as it is not used for unmarshalling.
		if (null == documentBuilder) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			documentBuilder = dbf.newDocumentBuilder();
		}
		return documentBuilder;
	}

	private JAXBContext getJAXBContext(Class<?> type) throws Exception {
		if (null == jaxbContext) {
			// A JAXBContext was not set, so create a new one based  on the
			// type.
			return JAXBContext.newInstance(type);
		}
		return jaxbContext;
	}

	@Override
	public Rink unmarshal(Object xmlElement) throws Exception {

		if (null == xmlElement) {
			return null;
		}

		Rink rink = (Rink) xmlElement;

		if (null != rink.getName()) {
			if (rink.getName().contains(Rink.LANDSCAPED_RINK)) {
				rink.setRinkType(Rink.LANDSCAPED_RINK);

			} else if (rink.getName().contains(Rink.OPEN_SKATE_RINK)) {
				rink.setRinkType(Rink.OPEN_SKATE_RINK);
				
			} else if (rink.getName().contains(Rink.TEAM_SPORT_RINK)) {
				rink.setRinkType(Rink.TEAM_SPORT_RINK);

			}
		}
		
		
		return rink;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object marshal(Rink rink) throws Exception {

		if (null == rink) {
			return null;
		}

		// 1. Build the JAXBElement to wrap the instance of Parameter.
		QName rootElement = new QName("patinoire");
		Class<?> type = rink.getClass();

		JAXBElement jaxbElement = new JAXBElement(rootElement, type, rink);

		// 2. Marshal the JAXBElement to a DOM element.
		Document document = getDocumentBuilder().newDocument();
		Marshaller marshaller = getJAXBContext(type).createMarshaller();
		marshaller.marshal(jaxbElement, document);
		Element element = document.getDocumentElement();

		return element;
	}

}
