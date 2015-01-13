package com.rink.utils;

import com.rink.app.Runner;
import com.rink.xml.jaxb.model.Rink;
import com.rink.xml.jaxb.model.Rinks;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RinkUtils {
    
    private static final Log LOG = LogFactory.getLog(RinkUtils.class);
    
    public static boolean isInternetReachable(String hostnameOrIP) throws UnknownHostException, IOException {

        Socket socket = null;
        boolean reachable = false;
        try {
            socket = new Socket(hostnameOrIP, 80);
            reachable = true;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        return reachable;
    }

    /**
     *
     *
     * @param inputDate
     * @return the formated date
     */
    public static Date parseDate(String inputDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date parsedDate = null;

        try {
            parsedDate = sdf.parse(inputDate);
            System.out.println(parsedDate);
        } catch (ParseException e) {
            System.out.println("Unparseable using " + sdf);
        }

        return parsedDate;
    }
    
    	/** 
     * Returns an ImageIcon, or null if the path was invalid.
     * @param description
     * @param path
     * @return ImageIcon
     */
    public static ImageIcon createImageIcon(String path,
                                               String description) {
        URL imgURL = RinkUtils.class.getResource(path);
        LOG.info("rink path " + imgURL);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
        	LOG.error("Couldn't find file: " + path);
            return null;
        }
    }
    
    public static void printRinks(Rinks rinks) {

        String lastBorough = "";

        for (Rink rink : (List<Rink>) rinks.getRinkList()) {

            if (!lastBorough.equalsIgnoreCase(rink.getBorough().getKey())) {
                LOG.info("\n-----------------------------------------------");
                LOG.info("Updated at : " + rink.getBorough().getUpdated());
                LOG.info("-----------------------------------------------\n");
                lastBorough = rink.getBorough().getKey();
            }

            if (Rink.TEAM_SPORT_RINK.equalsIgnoreCase(rink.getRinkType())) {
                LOG.info("         ");
                LOG.info("\\       " + rink.getName());
                LOG.info(" \\      " + rink.getBorough().getName());
                LOG.info("  \\_O_  " + rink.getCondition());
            } else if (Rink.LANDSCAPED_RINK.equalsIgnoreCase(rink.getRinkType())) {
                LOG.info("     __  ");
                LOG.info("    (  ) " + rink.getName());
                LOG.info("| |__||  " + rink.getBorough().getName());
                LOG.info("|_____)  " + rink.getCondition());
                LOG.info(" _|_|_)  ");

            } else if (Rink.OPEN_SKATE_RINK.equalsIgnoreCase(rink.getRinkType())) {
                LOG.info("         ");
                LOG.info("| |__    " + rink.getName());
                LOG.info("|_____)  " + rink.getBorough().getName());
                LOG.info(" _|_|_)  " + rink.getCondition());
            }

        }
    }

}
