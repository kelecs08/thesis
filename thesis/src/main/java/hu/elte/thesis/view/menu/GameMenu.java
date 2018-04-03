package hu.elte.thesis.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.view.service.ImageResizingService;

public class GameMenu extends JMenu {

	private static final long serialVersionUID = -182494725336616083L;
	
	private MainController mainController;
	private ImageResizingService imageResizingService;
		
	public GameMenu(MainController mainController) {
		super("Game");
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
	}
	
	public GameMenu getFileMenu() {
		addNewGameMenuItem();
		addSaveGameMenuItem();
		addLoadGameMenuItem();
		addSeparator();
		addExitMenuItem();
		return this;
	}

	private void addNewGameMenuItem() {
		ImageIcon newIcon = imageResizingService.resizeImage("images/icons/new_icon.jpg", false);
		JMenu newGame =  new JMenu("New...");
		newGame.setIcon(newIcon);
		
		JMenuItem newOnePlayerGame = new JMenuItem("New one-player game");
		newOnePlayerGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setGameType(GameType.ONE_PLAYER);
				mainController.startNewGame(true);
			}
		});
		
		JMenuItem newTwoPlayerGame = new JMenuItem("New two-player game");
		newTwoPlayerGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setGameType(GameType.TWO_PLAYER);
				mainController.startNewGame(false);				
			}
		});
		newGame.add(newOnePlayerGame);
		newGame.add(newTwoPlayerGame);
		
		add(newGame);
	}

	private void addExitMenuItem() {
		ImageIcon exitIcon = imageResizingService.resizeImage("images/icons/exit_icon.jpg", false);
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
		ImageIcon saveIcon = imageResizingService.resizeImage("images/icons/save_icon.jpg", false);
		JMenuItem saveGame = new JMenuItem("Save", saveIcon);
		saveGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			//	mainController.
				String test = "test";
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Custom file format (*.an)", "an");
				fileChooser.addChoosableFileFilter(filter);
								
				int returnValue = fileChooser.showSaveDialog(mainController.getMainWindow());
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					try(FileWriter fileWriter = new FileWriter(fileChooser.getSelectedFile()+".an")) {
						fileWriter.write(test);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		add(saveGame);
	}

	private void addLoadGameMenuItem() {
		ImageIcon loadIcon = imageResizingService.resizeImage("images/icons/load_icon.jpg", false);
		JMenuItem loadGame = new JMenuItem("Load game", loadIcon);
		loadGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Custom file format (*.an)", "an");
				fileChooser.addChoosableFileFilter(filter);
				
				int returnValue = fileChooser.showOpenDialog(mainController.getMainWindow());
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile.getPath());
				}
			}
		});
		add(loadGame);
	}

}
