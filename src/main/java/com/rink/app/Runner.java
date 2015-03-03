package com.rink.app;

import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Runner {

    private static final Log LOG = LogFactory.getLog(Runner.class);

    /**
     * @param args
     * @throws URISyntaxException
     */
    public static void main(String[] args) {

        LOG.debug("run rink-finder application");
		
        String[] contextPaths = new String[] {"app-context.xml"};
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(contextPaths);
        applicationContext.close();

    }

}
