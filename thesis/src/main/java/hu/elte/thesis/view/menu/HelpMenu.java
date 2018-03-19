package hu.elte.thesis.view.menu;

import javax.swing.JMenu;

import hu.elte.thesis.controller.MainController;

public class HelpMenu extends JMenu {

	private static final long serialVersionUID = 6667707369562342166L;

	private MainController mainController;
	
	public HelpMenu(MainController mainController) {
		super("Help");
		this.mainController = mainController;
	}
	
	public HelpMenu getHelpMenu() {
		
		
		
		return this;
	}
}
