package hu.elte.thesis.view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.view.menu.GameMenu;
import hu.elte.thesis.view.menu.HelpMenu;
import hu.elte.thesis.view.menu.SettingsMenu;
import hu.elte.thesis.view.service.ImageResizingService;

public class CustomMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1693411529145056061L;

	private MainController mainController;
		
	public CustomMenuBar(MainController mainController) {
		super();
		this.mainController = mainController;
	}
	
	public CustomMenuBar getCustomMenuBar() {		
		GameMenu fileMenu = new GameMenu(mainController);
		add(fileMenu.getFileMenu());
		
		SettingsMenu settingsMenu = new SettingsMenu(mainController);
		add(settingsMenu.getSettingsMenu());
		
		HelpMenu helpMenu = new HelpMenu(mainController);
		add(helpMenu.getHelpMenu());
		
		return this;
	}




}
