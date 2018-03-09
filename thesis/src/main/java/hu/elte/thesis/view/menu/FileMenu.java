package hu.elte.thesis.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.view.service.ImageResizingService;

public class FileMenu extends JMenu {

	private static final long serialVersionUID = -182494725336616083L;
	
	private MainController mainController;
	private ImageResizingService imageResizingService;
		
	public FileMenu(MainController mainController) {
		super("File");
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
	}
	
	public FileMenu getFileMenu() {
		addNewGameMenuItem();
		addSaveGameMenuItem();
		addLoadGameMenuItem();
		addSeparator();
		addExitMenuItem();
		return this;
	}

	private void addNewGameMenuItem() {
		ImageIcon newIcon = imageResizingService.resizeImage("images/icons/new_icon.jpg");
		JMenu newGame =  new JMenu("New...");
		newGame.setIcon(newIcon);
		
		JMenuItem newOnePlayerGame = new JMenuItem("New one-player game");
		
		
		JMenuItem newTwoPlayerGame = new JMenuItem("New two-player game");
		newTwoPlayerGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.startNewGame();				
			}
		});
		newGame.add(newOnePlayerGame);
		newGame.add(newTwoPlayerGame);
		
		add(newGame);
	}

	private void addExitMenuItem() {
		ImageIcon exitIcon = imageResizingService.resizeImage("images/icons/exit_icon.jpg");
		JMenuItem exitGame = new JMenuItem("Exit", exitIcon);
		exitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.getMainWindow().showExitConfirmation();
			}
		});
		add(exitGame);		
	}

	private void addSaveGameMenuItem() {
		ImageIcon saveIcon = imageResizingService.resizeImage("images/icons/save_icon.jpg");
		JMenuItem saveGame = new JMenuItem("Save", saveIcon);
		saveGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(saveGame);
	}

	private void addLoadGameMenuItem() {
		ImageIcon loadIcon = imageResizingService.resizeImage("images/icons/load_icon.jpg");
		JMenuItem loadGame = new JMenuItem("Load game", loadIcon);
		loadGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(loadGame);
	}

}
