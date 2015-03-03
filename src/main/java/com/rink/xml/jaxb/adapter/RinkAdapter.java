package com.rink.xml.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.rink.utils.RinkUtils;
import com.rink.window.RinkFrame;
import com.rink.xml.jaxb.model.Rink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RinkAdapter extends XmlAdapter<Object, Rink> {
        
    private static final Log LOG = LogFactory.getLog(RinkFrame.class);
    
	public RinkAdapter() {
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

                    LOG.info("image path : src/main/resources/icon/" + rink.getRinkType() + ".png");
                    rink.setImageIcon(RinkUtils.createImageIcon("src/main/resources/icon/" + rink.getRinkType() + ".png", rink.toString()));
            }


            return rink;

	}

	@Override
	public Object marshal(Rink rink) throws Exception {

            if (null == rink) {
                    return null;
            }

            return rink;

	}

}
