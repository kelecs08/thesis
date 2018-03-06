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
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import hu.elte.thesis.controller.MainController;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -9104376452678470583L;
	
	private Properties properties;
	
	private MainController mainController;

	private CustomMenuBar customMenuBar;
	private GamePanel tableBoardView;
	private StarterPanel starterPanel;
	
	public MainWindow() {
		loadMainWindowSettingProperties();
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
		this.tableBoardView = new GamePanel(mainController);
		add(this.tableBoardView.createInitialTableBoard());
		pack();
	}
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	private void setWindowButtonActions() {
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				showExitConfirmation();
			}
		});
	}

	private void setInitialStyle() {
		setTitle(properties.getProperty("title"));
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try(InputStream inputStream = classLoader.getResourceAsStream("images/icons/main_icon.jpg")) {
			BufferedImage image;
			image = ImageIO.read(inputStream);
			setIconImage(image);
		} catch (IOException e) {
			System.out.println("IOException occured during the load of the icon image.");
		}

		int preferredSize = Integer.parseInt(properties.getProperty("preferredSize"));
		setPreferredSize(new Dimension(preferredSize, preferredSize));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loadNimbusLookAndFeel() {
		try {
			for(LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equals(lookAndFeelInfo.getName())) {
					UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			System.out.println("Nimbus look and feel failed to be loaded.");
		}
	}
	
	private void loadSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("System look and feel failed to be loaded.");
		}
	}

	private void loadMainWindowSettingProperties() {
		properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try(InputStream inputStream = classLoader.getResourceAsStream("properties/main_window_setting.properties")) {
			properties.load(inputStream);
		} catch (IOException e) {
			System.out.println("IOException occured during the read of the property file.");
		}
	}
	
	private void showExitConfirmation() {
		int n = JOptionPane.showConfirmDialog(this, "Are you sure, you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION)
			doUponExit();
	}

	private void doUponExit() { 
		this.dispose();
	}
}