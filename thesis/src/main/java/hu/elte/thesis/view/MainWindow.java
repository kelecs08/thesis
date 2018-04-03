package hu.elte.thesis.view;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.view.service.PropertyService;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -9104376452678470583L;

	private final PropertyService propertyService;

	private MainController mainController;

	private CustomMenuBar customMenuBar;
	private GamePanel gamePanel;

	public MainWindow() {
		this.propertyService = new PropertyService();
		loadNimbusLookAndFeel();

		setInitialStyle();
		setWindowButtonActions();
	}

	public void createCustomMenuBar() {
		customMenuBar = new CustomMenuBar(mainController);
		JMenuBar menuBar = customMenuBar.getCustomMenuBar();
		setJMenuBar(menuBar);
	}

	public void createDefaultTableBoard() {
		this.gamePanel = new GamePanel(mainController);
		add(this.gamePanel.createInitialTableBoard());
		pack();
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	private void setWindowButtonActions() {
		setResizable(false);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				showExitConfirmation();
			}
		});
	}

	private void setInitialStyle() {
		Properties mainWindowSettingProperties = propertyService.loadMainWindowSettingProperties("small");
		setTitle(mainWindowSettingProperties.getProperty("title"));

		ClassLoader classLoader = Thread.currentThread()
			.getContextClassLoader();
		try (InputStream inputStream = classLoader.getResourceAsStream(mainWindowSettingProperties.getProperty("image"))) {
			BufferedImage image;
			image = ImageIO.read(inputStream);
			setIconImage(image);
		} catch (IOException e) {
			System.out.println("IOException occured during the load of the icon image.");
		}

		int preferredWindowSizeX = Integer.parseInt(mainWindowSettingProperties.getProperty("preferredWindowSizeX"));
		int preferredWindowSizeY = Integer.parseInt(mainWindowSettingProperties.getProperty("preferredWindowSizeY"));
		setPreferredSize(new Dimension(preferredWindowSizeX, preferredWindowSizeY));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setFrameSettings(String size) {
		Properties mainWindowSettingProperties = propertyService.loadMainWindowSettingProperties(size);
		int preferredWindowSizeX = Integer.parseInt(mainWindowSettingProperties.getProperty("preferredWindowSizeX"));
		int preferredWindowSizeY = Integer.parseInt(mainWindowSettingProperties.getProperty("preferredWindowSizeY"));
		setPreferredSize(new Dimension(preferredWindowSizeX, preferredWindowSizeY));
		pack();
		setLocationRelativeTo(null);
	}

	public void showExitConfirmation() {
		int n = JOptionPane.showConfirmDialog(this, "Are you sure, you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
			doUponExit();
		}
	}

	public void doUponExit() {
		this.dispose();
	}

	public void loadNimbusLookAndFeel() {
		try {
			for (LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(lookAndFeelInfo.getName())) {
					UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
					break;
				}
			}
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			System.out.println("Nimbus look and feel failed to be loaded.");
		}
	}

	public void loadMetalLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
