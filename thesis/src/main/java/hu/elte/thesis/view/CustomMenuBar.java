package hu.elte.thesis.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import hu.elte.thesis.controller.MainController;

public class CustomMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1693411529145056061L;

	private MainController mainController;
	
	private static final int IMG_WIDTH = 20;
	private static final int IMG_HEIGHT = 20;
	
	public CustomMenuBar(MainController mainController) {
		super();
		this.mainController = mainController;
	}
	
	public CustomMenuBar getCustomMenuBar() {
		
		Image newIcon = resizeImage("images/icons/new_icon.jpg");
		Image saveIcon = resizeImage("images/icons/save_icon.jpg");
		Image loadIcon = resizeImage("images/icons/load_icon.jpg");
		Image exitIcon = resizeImage("images/icons/exit_icon.jpg");
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newGame = new JMenuItem("New", new ImageIcon(newIcon));
		JMenuItem saveGame = new JMenuItem("Save", new ImageIcon(saveIcon));
		JMenuItem loadGame = new JMenuItem("Load game", new ImageIcon(loadIcon));
		JMenuItem exitGame = new JMenuItem("Exit", new ImageIcon(exitIcon));
		
		fileMenu.add(newGame);
		fileMenu.add(saveGame);
		fileMenu.add(loadGame);
		fileMenu.addSeparator();
		fileMenu.add(exitGame);
		
		add(fileMenu);
		
		return this;
	}

	private Image resizeImage(String imagePath) {
		BufferedImage originalImage;
		BufferedImage resizedImage = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try(InputStream inputStream = classLoader.getResourceAsStream(imagePath)) {
			originalImage = ImageIO.read(inputStream);
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
			Graphics2D graphics = resizedImage.createGraphics();
			graphics.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
			graphics.dispose();
		} catch (IOException e) {
			System.out.println("IOException occured during the load of the icon image.");
		}
		return resizedImage;
	}
}
