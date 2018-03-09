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
import hu.elte.thesis.view.menu.FileMenu;
import hu.elte.thesis.view.service.ImageResizingService;

public class CustomMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1693411529145056061L;

	private MainController mainController;
		
	public CustomMenuBar(MainController mainController) {
		super();
		this.mainController = mainController;
	}
	
	public CustomMenuBar getCustomMenuBar() {		
		FileMenu fileMenu = new FileMenu(mainController);
		add(fileMenu.getFileMenu());
		
		return this;
	}




}
