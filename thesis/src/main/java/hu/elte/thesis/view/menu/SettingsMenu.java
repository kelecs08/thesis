package hu.elte.thesis.view.menu;

import javax.swing.JMenu;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.view.service.ImageResizingService;

public class SettingsMenu extends JMenu {

	private static final long serialVersionUID = -1539287248291630075L;
	
	private MainController mainController;
	private ImageResizingService imageResizingService;
	
	public SettingsMenu(MainController mainController) {
		super("Settings");
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
	}
	
	public SettingsMenu getSettingsMenu() {
		
		return this;
	}


}
