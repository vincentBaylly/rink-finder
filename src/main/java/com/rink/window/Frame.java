package com.rink.window;

import com.rink.utils.ComparatorForRink;
import com.rink.utils.ComparatorForRink.BoroughComparator;
import com.rink.utils.RinkUtils;
import com.rink.xml.jaxb.model.Borough;
import com.rink.xml.jaxb.model.Rink;
import com.rink.xml.jaxb.model.Rinks;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.JFrame;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Frame extends JFrame {
    
    private static final Log LOG = LogFactory.getLog(Frame.class);
    
    private static final String MTL_WEBSITE = "www2.ville.montreal.qc.ca";

    private static final String RINKS_XML = "src/main/resources/rinks-jaxb.xml";

    private static final String RINKS_XML_URL = "http://www2.ville.montreal.qc.ca/services_citoyens/pdf_transfert/L29_PATINOIRE.xml";

    private static final ComparatorForRink comparatorForRink = new ComparatorForRink();
    
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private JButton playButton;
    private JEditorPane handNameOut;
    private JMenuBar menuBar;
    private JScrollPane scrollPane;

    /**
     * Creates new form Frame
     */
    public Frame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        playButton = new JButton();
        handNameOut = new JEditorPane();
        handNameOut.setEditable(false); // Read-only
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        exitMenuItem = new JMenuItem();
        scrollPane = new JScrollPane(handNameOut, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Container con = getContentPane();
        con.setBackground(new java.awt.Color(0, 0, 51));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        playButton.setText("Find");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                findButtonActionPerformed(evt);
            }
        });

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(playButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playButton))
                .addGap(109, 109, 109))
        );
        
        pack();
    }

    /**
     * exit menu catch event
     *
     * @param evt
     */
    private void exitMenuItemActionPerformed(ActionEvent evt) {
        System.exit(0);
    }
    
        /**
     * find rinks
     * @param evt 
     */
    private void findButtonActionPerformed(ActionEvent evt) {
        String result = unmarshallFlux().toString();
        LOG.info(result);
        handNameOut.setText(result);
    }
    
    public static StringBuilder unmarshallFlux() {
        
        StringBuilder sb = null;
        
        try {
            // create JAXB context and instantiate marshaller
            JAXBContext context = null;
            context = JAXBContext.newInstance(Rinks.class, Rink.class, Borough.class);

            Unmarshaller unmarshaller = null;

            unmarshaller = context.createUnmarshaller();
            Rinks rinks = null;

            if (RinkUtils.isInternetReachable(MTL_WEBSITE)) {

                URL u = new URL(RINKS_XML_URL);
                InputStream in = u.openStream();
                rinks = (Rinks) unmarshaller.unmarshal(in);
            } else {

                rinks = (Rinks) unmarshaller.unmarshal(new File(RINKS_XML));
            }

            //Sort by Rink Name
//			Collections.sort(rinks.getRinkList(), comparatorForRink.new RinkNameComparator());
//			printRinks(rinks);
            //Sort by Borough Name
            Collections.sort(rinks.getRinkList(), comparatorForRink.new BoroughComparator());
            sb = resultForRinks(rinks);

            //Sort by Rink Condition
//			Collections.sort(rinks.getRinkList(), comparatorForRink.new RinkConditionComparator());
//			printRinks(rinks);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return sb;
    }
    
    public static StringBuilder resultForRinks(Rinks rinks) {
        
        StringBuilder sb = new StringBuilder();
        String lastBorough = "";

        for (Rink rink : (List<Rink>) rinks.getRinkList()) {

            if (!lastBorough.equalsIgnoreCase(rink.getBorough().getKey())) {
                sb.append("\n-----------------------------------------------\n");
                sb.append("Updated at : " + rink.getBorough().getUpdated() + "");
                sb.append("\n-----------------------------------------------\n");
                lastBorough = rink.getBorough().getKey();
            }

            if (Rink.TEAM_SPORT_RINK.equalsIgnoreCase(rink.getRinkType())) {
                sb.append("\n         ");
                sb.append("\n\\       " + rink.getName());
                sb.append("\n \\      " + rink.getBorough().getName());
                sb.append("\n  \\_O_  " + rink.getCondition());
            } else if (Rink.LANDSCAPED_RINK.equalsIgnoreCase(rink.getRinkType())) {
                sb.append("\n      __  ");
                sb.append("\n     (  ) " + rink.getName());
                sb.append("\n| |__||  " + rink.getBorough().getName());
                sb.append("\n|_____)  " + rink.getCondition());
                sb.append("\n _|_|_)  ");

            } else if (Rink.OPEN_SKATE_RINK.equalsIgnoreCase(rink.getRinkType())) {
                sb.append("\n         ");
                sb.append("\n| |__    " + rink.getName());
                sb.append("\n|_____)  " + rink.getBorough().getName());
                sb.append("\n _|_|_)  " + rink.getCondition());
            }

        }
        
        return sb;
    }

}
