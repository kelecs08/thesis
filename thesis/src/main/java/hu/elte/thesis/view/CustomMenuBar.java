package hu.elte.thesis.view;

import javax.swing.JMenuBar;

import hu.elte.thesis.controller.MainControllerInterface;
import hu.elte.thesis.view.menu.GameMenu;
import hu.elte.thesis.view.menu.HelpMenu;
import hu.elte.thesis.view.menu.SettingsMenu;

/**
 * The menubar that the main window contains.
 * 
 * @author kelecs08
 */
public class CustomMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1693411529145056061L;

	private MainControllerInterface mainController;
		
	public CustomMenuBar(MainControllerInterface mainController) {
		super();
		this.mainController = mainController;
	}
	
	/**
	 * Crates the initialized menubar for main window.
	 * @return
	 * 		this object
	 */
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
