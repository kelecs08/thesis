package hu.elte.thesis.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.Blobs;
import hu.elte.thesis.view.GamePanel;
import hu.elte.thesis.view.service.ImageResizingService;
import hu.elte.thesis.view.service.PropertyService;

public class SettingsMenu extends JMenu {

	private static final long serialVersionUID = -1539287248291630075L;
	
	private MainController mainController;
	private ImageResizingService imageResizingService;
	private PropertyService propertyService;
	private Properties iconProperties;
	
	public SettingsMenu(MainController mainController) {
		super("Settings");
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
		this.propertyService = new PropertyService();
		iconProperties = propertyService.loadIconProperties();
	}
	
	public SettingsMenu getSettingsMenu() {
		addTableSizeChangerMenuItem();
		addImageChangerMenuItem();
		
		return this;
	}

	private void addTableSizeChangerMenuItem() {
		JMenu changeTableSize = new JMenu("Change table size...");
		
		JMenuItem small = new JMenuItem("4 x 4 - small");
		small.addActionListener(getTableSizeChangerActionListener(4, "small"));
		JMenuItem medium = new JMenuItem("6 x 6 - medium");
		medium.addActionListener(getTableSizeChangerActionListener(6, "medium"));
		JMenuItem large = new JMenuItem("8 x 8 - large");
		large.addActionListener(getTableSizeChangerActionListener(8, "large"));
		
		changeTableSize.add(small);
		changeTableSize.addSeparator();
		changeTableSize.add(medium);
		changeTableSize.addSeparator();
		changeTableSize.add(large);
		
		add(changeTableSize);
	}

	private ActionListener getTableSizeChangerActionListener(int tableSize, String size) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.changeTableSize(tableSize);
				mainController.getMainWindow().getGamePanel().updateCenter();
				mainController.getMainWindow().setFrameSettings(size);
				mainController.getMainWindow().getGamePanel().updateFields();
			}
		};
	}

	private void addImageChangerMenuItem() {
		JMenu appearance = new JMenu("Appearance");
		
		JMenu playerOneBlobAppearance =  new JMenu("Change Player1 blob to...");
		JMenu playerTwoBlobAppearance =  new JMenu("Change Player2 blob to...");
		

		int iconSize = Integer.parseInt(iconProperties.getProperty("iconSize"));
		int playerSize = Integer.parseInt(iconProperties.getProperty("playerSize"));
		int playerLabelSize = Integer.parseInt(iconProperties.getProperty("playerLabelSize"));
		
		JMenuItem playerOneBlueBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.BLUE.getBlobColorString()), " blue blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerOneBrownBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.BROWN.getBlobColorString()), " brown blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerOneDarkBlueBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.DARK_BLUE.getBlobColorString()), " dark blue blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerOneDarkGreenBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.DARK_GREEN.getBlobColorString()), " dark green blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerOneGrayBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.GRAY.getBlobColorString()), " gray blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerOneGreenBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.GREEN.getBlobColorString()), " green blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerOneOrangeBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.ORANGE.getBlobColorString()), " orange blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerOnePurpleBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.PURPLE.getBlobColorString()), " purple blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerOneRedBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.RED.getBlobColorString()), " red blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerOneYellowBlob = addPlayerOneBlobChanger(iconProperties.getProperty(Blobs.YELLOW.getBlobColorString()), " yellow blob", iconSize, playerSize, playerLabelSize);

		JMenuItem playerTwoBlueBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.BLUE.getBlobColorString()), " blue blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerTwoBrownBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.BROWN.getBlobColorString()), " brown blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerTwoDarkBlueBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.DARK_BLUE.getBlobColorString()), " dark blue blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerTwoDarkGreenBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.DARK_GREEN.getBlobColorString()), " dark green blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerTwoGrayBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.GRAY.getBlobColorString()), " gray blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerTwoGreenBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.GREEN.getBlobColorString()), " green blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerTwoOrangeBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.ORANGE.getBlobColorString()), " orange blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerTwoPurpleBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.PURPLE.getBlobColorString()), " purple blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerTwoRedBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.RED.getBlobColorString()), " red blob", iconSize, playerSize, playerLabelSize);
		JMenuItem playerTwoYellowBlob = addPlayerTwoBlobChanger(iconProperties.getProperty(Blobs.YELLOW.getBlobColorString()), " yellow blob", iconSize, playerSize, playerLabelSize);
		
		
		
		
		JMenuItem nimbusLookAndFeel = new JMenuItem("Load nimbus look and feel");
		nimbusLookAndFeel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.getMainWindow().loadNimbusLookAndFeel();
			}
		});
		
		JMenuItem metalLookAndFeel = new JMenuItem("Load system look and feel");
		metalLookAndFeel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.getMainWindow().loadMetalLookAndFeel();
			}
		});

		playerOneBlobAppearance.add(playerOneBlueBlob);
		playerOneBlobAppearance.add(playerOneBrownBlob);
		playerOneBlobAppearance.add(playerOneDarkBlueBlob);
		playerOneBlobAppearance.add(playerOneDarkGreenBlob);
		playerOneBlobAppearance.add(playerOneGrayBlob);
		playerOneBlobAppearance.add(playerOneGreenBlob);
		playerOneBlobAppearance.add(playerOneOrangeBlob);
		playerOneBlobAppearance.add(playerOnePurpleBlob);
		playerOneBlobAppearance.add(playerOneRedBlob);
		playerOneBlobAppearance.add(playerOneYellowBlob);
		
		playerTwoBlobAppearance.add(playerTwoBlueBlob);
		playerTwoBlobAppearance.add(playerTwoBrownBlob);
		playerTwoBlobAppearance.add(playerTwoDarkBlueBlob);
		playerTwoBlobAppearance.add(playerTwoDarkGreenBlob);
		playerTwoBlobAppearance.add(playerTwoGrayBlob);
		playerTwoBlobAppearance.add(playerTwoGreenBlob);
		playerTwoBlobAppearance.add(playerTwoOrangeBlob);
		playerTwoBlobAppearance.add(playerTwoPurpleBlob);
		playerTwoBlobAppearance.add(playerTwoRedBlob);
		playerTwoBlobAppearance.add(playerTwoYellowBlob);
		
		appearance.add(playerOneBlobAppearance);
		appearance.add(playerTwoBlobAppearance);
		appearance.addSeparator();
		appearance.add(nimbusLookAndFeel);
		appearance.add(metalLookAndFeel);
		
		add(appearance);
		
	}

	private JMenuItem addPlayerOneBlobChanger(String path, String colorName, int iconSize, int playerSize, int playerLabelSize) {
		ImageIcon blobIcon = imageResizingService.resizeImage(path, iconSize, iconSize, false);
		ImageIcon blobPlayer = imageResizingService.resizeImage(path, playerSize, playerSize, false);
		ImageIcon blobPlayerLabel = imageResizingService.resizeImage(path, playerLabelSize, playerLabelSize, false);
		
		JMenuItem playerOneBlob = new JMenuItem(colorName);
		playerOneBlob.setIcon(blobIcon);
		playerOneBlob.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GamePanel gamePanel = mainController.getMainWindow().getGamePanel();
				gamePanel.setPlayerOneImage(blobPlayer);
				gamePanel.updateFields();
				gamePanel.updatePlayerOneLabel(blobPlayerLabel);
			}
		});
		return playerOneBlob;
	}

	private JMenuItem addPlayerTwoBlobChanger(String path, String colorName, int iconSize, int playerSize, int playerLabelSize) {
		ImageIcon blobIcon = imageResizingService.resizeImage(path, iconSize, iconSize, true);
		ImageIcon blobPlayer = imageResizingService.resizeImage(path, playerSize, playerSize, true);
		ImageIcon blobPlayerLabel = imageResizingService.resizeImage(path, playerLabelSize, playerLabelSize, true);
		
		JMenuItem playerTwoBlob = new JMenuItem(colorName);
		playerTwoBlob.setIcon(blobIcon);
		playerTwoBlob.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GamePanel gamePanel = mainController.getMainWindow().getGamePanel();
				gamePanel.setPlayerTwoImage(blobPlayer);
				gamePanel.updateFields();
				gamePanel.updatePlayerTwoLabel(blobPlayerLabel);
			}
		});
		return playerTwoBlob;
	}
	
	
}
