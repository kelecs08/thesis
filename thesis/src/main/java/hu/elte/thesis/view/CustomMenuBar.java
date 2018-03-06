package hu.elte.thesis.view;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.view.service.ImageResizingService;

public class CustomMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1693411529145056061L;

	private MainController mainController;
	private ImageResizingService imageResizingService;
		
	public CustomMenuBar(MainController mainController) {
		super();
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
	}
	
	public CustomMenuBar getCustomMenuBar() {
		
		ImageIcon newIcon = imageResizingService.resizeImage("images/icons/new_icon.jpg");
		ImageIcon saveIcon = imageResizingService.resizeImage("images/icons/save_icon.jpg");
		ImageIcon loadIcon = imageResizingService.resizeImage("images/icons/load_icon.jpg");
		ImageIcon exitIcon = imageResizingService.resizeImage("images/icons/exit_icon.jpg");
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newGame = new JMenuItem("New", newIcon);
		JMenuItem saveGame = new JMenuItem("Save", saveIcon);
		JMenuItem loadGame = new JMenuItem("Load game", loadIcon);
		JMenuItem exitGame = new JMenuItem("Exit", exitIcon);
		
		fileMenu.add(newGame);
		fileMenu.add(saveGame);
		fileMenu.add(loadGame);
		fileMenu.addSeparator();
		fileMenu.add(exitGame);
		
		add(fileMenu);
		
		return this;
	}


}
