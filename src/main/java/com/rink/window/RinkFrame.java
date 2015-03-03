package com.rink.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rink.app.Treatment;
import com.rink.utils.RinkUtils;
import com.rink.xml.jaxb.model.Borough;
import com.rink.xml.jaxb.model.Rink;
import com.rink.xml.jaxb.model.Rinks;

public class RinkFrame extends JFrame {

	private static final Log LOG = LogFactory.getLog(RinkFrame.class);
	
	private Treatment treatment;
	
	private JMenuItem exitMenuItem;
	private JMenu fileMenu;
	private JPanel listPanel;
	private JPanel buttonPanel;
	private JButton boroughButton;
	private JButton condButton;
	private JLabel handNameOut;
	private JMenuBar menuBar;
	private JScrollPane scrollPane;
	private JComboBox boroughList;

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	@SuppressWarnings("unused")
	private void initComponents() {
		
            setPreferredSize(new Dimension(600, 400));
		
            boroughList = new JComboBox();
            buttonPanel = new JPanel();
            listPanel = new JPanel();
            boroughButton = new JButton();
            condButton = new JButton();
            handNameOut = new JLabel();
            menuBar = new JMenuBar();
            fileMenu = new JMenu();
            exitMenuItem = new JMenuItem();
            scrollPane = new JScrollPane(handNameOut, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(400, 285));
            listPanel.setPreferredSize(new Dimension(400, 300));
            listPanel.add(scrollPane);

            getContentPane().setLayout(new BorderLayout());

            fillListCombo();

            Container con = getContentPane();
            con.setBackground(new java.awt.Color(0, 0, 51));
            buttonPanel.setBackground(new java.awt.Color(0, 0, 51));
            listPanel.setBackground(new java.awt.Color(0, 0, 51));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            boroughButton.setText("Sort by Borough");
            boroughButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    boroughButtonActionPerformed(evt);
                }
            });

            condButton.setText("Sort by Condition");
            condButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    condButtonActionPerformed(evt);
                }
            });

            boroughList.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    boroughListActionPerformed(evt);
                }
            });

            fileMenu.setMnemonic('f');
            fileMenu.setText("File");

            exitMenuItem.setMnemonic('x');
            exitMenuItem.setText("Exit");
            exitMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    exitMenuItemActionPerformed(evt);
                }
            });
            fileMenu.add(exitMenuItem);

            menuBar.add(fileMenu);

            setJMenuBar(menuBar);

            buttonPanel.add(BorderLayout.EAST, boroughButton);
            buttonPanel.add(BorderLayout.WEST, condButton);

            getContentPane().add(BorderLayout.NORTH, boroughList);
            getContentPane().add(BorderLayout.CENTER, listPanel);
            getContentPane().add(BorderLayout.SOUTH, buttonPanel);
		
            setVisible(true);
            setState(RinkFrame.NORMAL);

            pack();
	}
	
	@SuppressWarnings("unchecked")
	private void fillListCombo(){
		
		for(Borough borough : treatment.getAvailableBorough()){
			boroughList.addItem(borough);
		}
		
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
	 * sort by borough
	 * @param evt
	 */
	private void boroughButtonActionPerformed(ActionEvent evt) {

            Rinks result = treatment.getRinks("borough");
            RinkUtils.printRinks(result);
            handNameOut.setText("");
            ImageIcon imageIcon = new ImageIcon(append(result.getRinkList()));
            handNameOut.setIcon(imageIcon);
            handNameOut.setHorizontalAlignment(SwingConstants.CENTER);

            LOG.debug(result);
	}
	
	/**
	 * find rinks
	 * sort by condition
	 * @param evt
	 */
	private void condButtonActionPerformed(ActionEvent evt) {
		
            Rinks result = treatment.getRinks("condition");
            RinkUtils.printRinks(result);
            handNameOut.setText("");
            ImageIcon imageIcon = new ImageIcon(append(result.getRinkList()));
            handNameOut.setIcon(imageIcon);
            handNameOut.setHorizontalAlignment(SwingConstants.CENTER);

            LOG.debug(result);
		
	}

	/**
	 * find rinks from borough
	 * sort by condition
	 * @param evt
	 */
	private void boroughListActionPerformed(ActionEvent evt) {
		
            JComboBox cb = (JComboBox)evt.getSource();
            Borough borough = (Borough)cb.getSelectedItem();
            Rinks result = treatment.getRinksFromBorough(borough.getKey());
            RinkUtils.printRinks(result);
            handNameOut.setText("");
            ImageIcon imageIcon = new ImageIcon(append(result.getRinkList()));
            handNameOut.setIcon(imageIcon);
            handNameOut.setHorizontalAlignment(SwingConstants.CENTER);

            LOG.debug(result);

	}
	
	/**
	 * add an icon on rink
	 * 
	 * @param rinks
	 * @return
	 */
	public static Image append(List<Rink> rinksResult) {

            BufferedImage bImage = null;

            if (rinksResult.get(0) != null && rinksResult.get(0).getImageIcon() != null) {
                    int wMax = 400;
                    int h1 = 0;
                    bImage = new BufferedImage(wMax, 56 * rinksResult.size(), BufferedImage.TYPE_INT_ARGB);

                    for (Rink rink : rinksResult) {
                            if (rink != null) {

                                    Graphics2D g2 = bImage.createGraphics();
                                    g2.drawImage(rink.getImageIcon().getImage(), 0, h1, null);
                                    g2.setColor(Color.red);
                                    g2.drawLine(0, h1, rink.getImageIcon().getImage().getWidth(null) + 300, h1);
                                    g2.drawString(rink.getName(), rink.getImageIcon().getImage().getWidth(null) + 10, h1 + 15);
                                    g2.drawString(rink.getBorough().getName(), rink.getImageIcon().getImage().getWidth(null) + 10, h1 + 30);
                                    g2.drawString(rink.getCondition(), rink.getImageIcon().getImage().getWidth(null) + 10, h1 + 45);
                                    h1 += rink.getImageIcon().getImage().getHeight(null) + 20;

                            }
                    }

            }

            return bImage;
	}

}
