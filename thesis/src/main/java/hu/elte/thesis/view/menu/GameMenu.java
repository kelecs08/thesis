package hu.elte.thesis.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.ComputerPlayerDifficulty;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.view.service.GameMenuService;
import hu.elte.thesis.view.service.ImageResizingService;

public class GameMenu extends JMenu {

	private static final long serialVersionUID = -182494725336616083L;

	private final MainController mainController;
	private final ImageResizingService imageResizingService;
	private final GameMenuService gameMenuService;

	public GameMenu(MainController mainController) {
		super("Game");
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
		this.gameMenuService = new GameMenuService(mainController);
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
		JMenu newGame = new JMenu("New...");
		newGame.setIcon(newIcon);

		JMenu newComputerGame = new JMenu("New one-player game");

		JMenuItem newOnePlayerGameEasy = new JMenuItem("easy");
		newOnePlayerGameEasy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setGameType(GameType.ONE_PLAYER);
				mainController.setComputerPlayerDifficulty(ComputerPlayerDifficulty.EASY);
				mainController.startNewGame(true);
			}
		});

		JMenuItem newOnePlayerGameMedium = new JMenuItem("medium");
		newOnePlayerGameMedium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setGameType(GameType.ONE_PLAYER);
				mainController.setComputerPlayerDifficulty(ComputerPlayerDifficulty.MEDIUM);
				mainController.startNewGame(true);
			}
		});
		newComputerGame.add(newOnePlayerGameEasy);
		newComputerGame.add(newOnePlayerGameMedium);

		JMenuItem newTwoPlayerGame = new JMenuItem("New two-player game");
		newTwoPlayerGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setGameType(GameType.TWO_PLAYER);
				mainController.startNewGame(false);
			}
		});
		newGame.add(newComputerGame);
		newGame.add(newTwoPlayerGame);

		add(newGame);
	}

	private void addSaveGameMenuItem() {
		ImageIcon saveIcon = imageResizingService.resizeImage("images/icons/save_icon.jpg", false);
		JMenuItem saveGame = new JMenuItem("Save", saveIcon);
		saveGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView()
					.getHomeDirectory());

				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Custom file format (*.an)", "an");
				fileChooser.addChoosableFileFilter(filter);

				int returnValue = fileChooser.showSaveDialog(mainController.getMainWindow());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						gameMenuService.writeToFile(fileChooser.getSelectedFile() + ".an", mainController.getActualPlayer(), mainController.getPlayerOne(),
								mainController.getPlayerTwo(), mainController.getTableBoardPositions());
					} catch (Exception ex) {
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
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView()
					.getHomeDirectory());

				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Custom file format (*.an)", "an");
				fileChooser.addChoosableFileFilter(filter);

				int returnValue = fileChooser.showOpenDialog(mainController.getMainWindow());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						File selectedFile = fileChooser.getSelectedFile();
						gameMenuService.loadFromFile(selectedFile);
						mainController.getMainWindow()
							.getGamePanel()
							.updateFields();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		add(loadGame);
	}

	private void addExitMenuItem() {
		ImageIcon exitIcon = imageResizingService.resizeImage("images/icons/exit_icon.jpg", false);
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
