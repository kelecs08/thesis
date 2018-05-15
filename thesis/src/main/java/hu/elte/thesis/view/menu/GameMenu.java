package hu.elte.thesis.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import hu.elte.thesis.controller.MainControllerInterface;
import hu.elte.thesis.model.ComputerPlayerDifficulty;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.view.service.FileHandleService;
import hu.elte.thesis.view.service.ImageResizingService;
import hu.elte.thesis.view.service.PropertyService;

/**
 * The implementation of the Game menu.
 * 
 * @author kelecs08
 */
public class GameMenu extends JMenu {

	private static final long serialVersionUID = -182494725336616083L;

	private final MainControllerInterface mainController;
	private final ImageResizingService imageResizingService;
	private final FileHandleService fileHandleService;
	private PropertyService propertyService;
	private Properties iconProperties;

	public GameMenu(MainControllerInterface mainController) {
		super("Game");
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
		this.fileHandleService = new FileHandleService(mainController);
		this.propertyService = new PropertyService();
		this.iconProperties = this.propertyService.loadIconProperties();
	}

	/**
	 * Get the initialized Game menu.
	 * @return
	 * 		this object
	 */
	public GameMenu getFileMenu() {
		addNewGameMenuItem();
		addSaveGameMenuItem();
		addLoadGameMenuItem();
		addSeparator();
		addExitMenuItem();
		return this;
	}

	/**
	 * Creates new game menu item and adds it to the menu.
	 */
	private void addNewGameMenuItem() {
		ImageIcon newIcon = imageResizingService.resizeImage(iconProperties.getProperty("new"), false);
		JMenu newGame = new JMenu("New...");
		newGame.setIcon(newIcon);

		JMenu newComputerGame = new JMenu("One-player game");

		JMenuItem newOnePlayerGameEasy = new JMenuItem("easy");
		newOnePlayerGameEasy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainController.getGameType().equals(GameType.TWO_PLAYER))
					mainController.setPlayerTwoName("Computer");				
				mainController.setGameType(GameType.ONE_PLAYER);
				mainController.setComputerPlayerDifficulty(ComputerPlayerDifficulty.EASY);
				mainController.startNewGame(true);
			}
		});

		JMenuItem newOnePlayerGameMedium = new JMenuItem("medium");
		newOnePlayerGameMedium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainController.getGameType().equals(GameType.TWO_PLAYER))
					mainController.setPlayerTwoName("Computer");	
				mainController.setGameType(GameType.ONE_PLAYER);
				mainController.setComputerPlayerDifficulty(ComputerPlayerDifficulty.MEDIUM);
				mainController.startNewGame(true);
			}
		});
		
		JMenuItem newOnePlayerGameHard = new JMenuItem("hard");
		newOnePlayerGameHard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainController.getGameType().equals(GameType.TWO_PLAYER))
					mainController.setPlayerTwoName("Computer");	
				mainController.setGameType(GameType.ONE_PLAYER);
				mainController.setComputerPlayerDifficulty(ComputerPlayerDifficulty.HARD);
				mainController.startNewGame(true);
			}
		});
		newComputerGame.add(newOnePlayerGameEasy);
		newComputerGame.add(newOnePlayerGameMedium);
		newComputerGame.add(newOnePlayerGameHard);

		JMenuItem newTwoPlayerGame = new JMenuItem("Two-player game");
		newTwoPlayerGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainController.getGameType().equals(GameType.ONE_PLAYER))
					mainController.setPlayerTwoName("Player2");	
				mainController.setGameType(GameType.TWO_PLAYER);
				mainController.startNewGame(false);
			}
		});
		newGame.add(newComputerGame);
		newGame.add(newTwoPlayerGame);

		add(newGame);
	}

	/**
	 * Creates save game menu item and adds it to the menu.
	 */
	private void addSaveGameMenuItem() {
		ImageIcon saveIcon = imageResizingService.resizeImage(iconProperties.getProperty("save"), false);
		JMenuItem saveGame = new JMenuItem("Save", saveIcon);
		saveGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Custom file format (*.an)", "an");
				fileChooser.addChoosableFileFilter(filter);

				int returnValue = fileChooser.showSaveDialog(mainController.getMainWindow());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						fileHandleService.writeToFile(fileChooser.getSelectedFile() + ".an", mainController.getActualPlayer(), mainController.getPlayerOne(),
								mainController.getPlayerTwo(), mainController.getTableBoardPositions(), mainController.getGameType(), mainController.getComputerPlayerDifficulty());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		add(saveGame);
	}

	private void addLoadGameMenuItem() {
		ImageIcon loadIcon = imageResizingService.resizeImage(iconProperties.getProperty("load"), false);
		JMenuItem loadGame = new JMenuItem("Load", loadIcon);
		loadGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Custom file format (*.an)", "an");
				fileChooser.addChoosableFileFilter(filter);

				int returnValue = fileChooser.showOpenDialog(mainController.getMainWindow());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						File selectedFile = fileChooser.getSelectedFile();
						fileHandleService.loadFromFile(selectedFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		add(loadGame);
	}

	private void addExitMenuItem() {
		ImageIcon exitIcon = imageResizingService.resizeImage(iconProperties.getProperty("exit"), false);
		JMenuItem exitGame = new JMenuItem("Exit", exitIcon);
		exitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.getMainWindow()
					.showExitConfirmation();
			}
		});
		add(exitGame);
	}
}
