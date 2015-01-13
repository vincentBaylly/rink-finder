package com.rink.window;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
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

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private static final Log LOG = LogFactory.getLog(Frame.class);

	private static final String MTL_WEBSITE = "www2.ville.montreal.qc.ca";

	private static final String RINKS_XML = "src/main/resources/rinks-jaxb.xml";

	private static final String RINKS_XML_URL = "http://www2.ville.montreal.qc.ca/services_citoyens/pdf_transfert/L29_PATINOIRE.xml";

	private static final ComparatorForRink comparatorForRink = new ComparatorForRink();

	private JMenuItem exitMenuItem;
	private JMenu fileMenu;
	private JButton findButton;
	private JLabel handNameOut;
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
	private void initComponents() {

		findButton = new JButton();
		handNameOut = new JLabel();
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		exitMenuItem = new JMenuItem();
		scrollPane = new JScrollPane(handNameOut, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVisible(false);

		Container con = getContentPane();
		con.setBackground(new java.awt.Color(0, 0, 51));

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		findButton.setText("Find");
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				findButtonActionPerformed(evt);
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

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

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
	 * 
	 * @param evt
	 */
	private void findButtonActionPerformed(ActionEvent evt) {

		Rinks result = unmarshallFlux();
		RinkUtils.printRinks(result);
		handNameOut.setText("");
		ImageIcon imageIcon = new ImageIcon(append(result.getRinkList()));
		handNameOut.setIcon(imageIcon);
		handNameOut.setHorizontalAlignment(SwingConstants.RIGHT);
		scrollPane.setVisible(true);
		
		LOG.info(result);
	}

	public static Rinks unmarshallFlux() {

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
				rinks = (Rinks) unmarshaller.unmarshal(in);

				// Write to File
				marshaller.marshal(rinks, new FileOutputStream(RINKS_XML));
			} else {

				rinks = (Rinks) unmarshaller.unmarshal(new File(RINKS_XML));
			}

			Collections.sort(rinks.getRinkList(), comparatorForRink.new BoroughComparator());

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rinks;
	}

	/**
	 * add flop images to make one
	 * 
	 * @param cards
	 * @return
	 */
	public static Image append(List<Rink> rinksResult) {

		BufferedImage buf = null;

		if (rinksResult.get(0) != null && rinksResult.get(0).getImageIcon() != null) {
			int wMax = rinksResult.get(0).getImageIcon().getImage().getWidth(null);
			int hMax = 800;
			int h1 = 0;
			buf = new BufferedImage(wMax, hMax, BufferedImage.TYPE_INT_ARGB);
			for (Rink rink : rinksResult) {
				if (rink != null) {

					Graphics2D g2 = buf.createGraphics();
					g2.drawImage(rink.getImageIcon().getImage(), 0, h1, null);
					h1 += rink.getImageIcon().getImage().getWidth(null);

				}
			}

		}

		return buf;
	}

}
