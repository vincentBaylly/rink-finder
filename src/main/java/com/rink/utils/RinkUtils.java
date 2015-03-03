package com.rink.utils;

import com.rink.xml.jaxb.model.Rink;
import com.rink.xml.jaxb.model.Rinks;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
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
                } catch (IOException ex) {
                    LOG.error("the url " + hostnameOrIP + " is not reachable ", ex);
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
        } catch (ParseException ex) {
            LOG.error("Unparseable using " + sdf, ex);
        }

        return parsedDate;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     *
     * @param description
     * @param path
     * @return ImageIcon
     */
    public static ImageIcon createImageIcon(String path,
            String description) {

        File imageFile = new File(path);
        ImageIcon imageIcon = null;

        try {

            LOG.info("rink path " + imageFile.getCanonicalPath());
            imageIcon = new ImageIcon(imageFile.toURI().toURL(), description);

        } catch (MalformedURLException ex) {
            LOG.error("Couldn't find file: " + path, ex);
        } catch (IOException ex) {
            LOG.error("Couldn't find file: " + path, ex);
        }

        return imageIcon;
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
