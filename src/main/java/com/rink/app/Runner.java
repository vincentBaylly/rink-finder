package com.rink.app;

import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rink.window.Frame;

public class Runner {

    private static final Log LOG = LogFactory.getLog(Runner.class);

    /**
     * @param args
     * @throws URISyntaxException
     */
    public static void main(String[] args) {

        Frame frame = new Frame();
        frame.setSize(800, 600);
        frame.setVisible(true);

    }

}
