package hu.elte.thesis.view.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import hu.elte.thesis.controller.MainControllerInterface;
import hu.elte.thesis.model.ComputerPlayerDifficulty;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;

/**
 * Service class for the game menu item.
 * 
 * @author kelecs08
 */
public class FileHandleService {
	
	private MainControllerInterface mainController;
	
	public FileHandleService(MainControllerInterface mainController) {
		this.mainController = mainController;
	}

	/**
	 * Writing game informations to the given file.
	 * @param fileName
	 * 		the name of the file, in which the informations are written
	 * @param actualPlayer
	 * 		the current player of the game
	 * @param playerOne
	 * 		the first player of the game
	 * @param playerTwo
	 * 		the second player of the game
	 * @param tableBoardPositions
	 * 		the game's table board positions
	 * @param gameType
	 * 		the type of the game
	 * @param computerPlayerDifficulty
	 * 		the difficulty level of the computer player
	 * @throws IOException
	 */
	public void writeToFile(String fileName, Player actualPlayer, Player playerOne, Player playerTwo, Position[][] tableBoardPositions, GameType gameType, ComputerPlayerDifficulty computerPlayerDifficulty) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
		String difficulty = "";
		if(gameType.equals(GameType.ONE_PLAYER)) difficulty = ";" + computerPlayerDifficulty.name();
		printWriter.print(gameType.name() + difficulty + "\n");
		printWriter.printf("%s;%d\n", playerOne.getName(), playerOne.getReservedSpots());
		printWriter.printf("%s;%d;%b\n", playerTwo.getName(), playerTwo.getReservedSpots(), playerTwo.isComputerPlayer());
		printWriter.printf("%s\n", actualPlayer.getName());
		printWriter.printf("%d\n", tableBoardPositions.length);
		for(int i = 0; i < tableBoardPositions.length; i++) {
			for(int j = 0; j < tableBoardPositions.length; j++) {
				Position act = tableBoardPositions[i][j];
				String playerName = " ";
				if(act.getPlayer() != null) {
					if(act.getPlayer().equals(playerOne)) playerName = "ONE";
					else playerName = "TWO";
				}
				printWriter.printf("%d;%d;%s;%b\n", act.getRow(), act.getColumn(), playerName, act.isValidSpace());
			}
		}
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * Loading game informations from the given file.
	 * @param file
	 * 		the file from which the informations are being read
	 * @throws IOException
	 */
	public void loadFromFile(File file) throws IOException {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch(FileNotFoundException e) {
			return;
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = bufferedReader.readLine();
		setGameDetails(line);
		
		line = bufferedReader.readLine();
		setPlayerOneFromFile(line);
		
		line = bufferedReader.readLine();
		setPlayerTwoFromFile(line);
		
		line = bufferedReader.readLine();
		setActualPlayerFromFile(line);
		
		line = bufferedReader.readLine();
		int tableSize = Integer.parseInt(line)-4;
		
		Position[][] tableBoardPositions = new Position[tableSize+4][tableSize+4];
		while((line = bufferedReader.readLine()) != null) {
			String[] tokens = line.split(";");
			int row = Integer.parseInt(tokens[0]);
			int column = Integer.parseInt(tokens[1]);
			Player player = getPlayerByName(tokens[2]);
			boolean isValidSpace = true;
			if("false".equals(tokens[3])) isValidSpace = false;
			tableBoardPositions[row][column] = new Position(row, column, player, isValidSpace);
		}
		
		mainController.changeTableSize(tableSize, tableBoardPositions);
		mainController.getMainWindow().getGamePanel().updateCenter();
		String size = getSizeString(tableSize);
		mainController.getMainWindow().setFrameSettings(size);
		mainController.getMainWindow().getGamePanel().updateFields();
		mainController.updateGameTypeLabel();
		mainController.getMainWindow().getGamePanel().setPlayerTwoNameButtonText(mainController.getPlayerTwo().getName());
		bufferedReader.close();
	}

	private String getSizeString(int tableSize) {
		if(tableSize == 4) return "small";
		if(tableSize == 6) return "medium";
		return "large";
	}

	private void setGameDetails(String line) {
		String[] tokens = line.split(";");
		if(GameType.ONE_PLAYER.name().equals(tokens[0])) mainController.setGameType(GameType.ONE_PLAYER);
		else mainController.setGameType(GameType.TWO_PLAYER);
		if(mainController.getGameType().equals(GameType.ONE_PLAYER)) {
			if(ComputerPlayerDifficulty.EASY.name().equals(tokens[1])) mainController.setComputerPlayerDifficulty(ComputerPlayerDifficulty.EASY);
			else if(ComputerPlayerDifficulty.MEDIUM.name().equals(tokens[1])) mainController.setComputerPlayerDifficulty(ComputerPlayerDifficulty.MEDIUM);
		}	
	}

	private void setPlayerOneFromFile(String line) {
		String[] tokens = line.split(";");
		Player player = new Player(tokens[0], false);
		player.setReservedSpots(Integer.parseInt(tokens[1]));
		mainController.setPlayerOne(player);
	}

	private void setPlayerTwoFromFile(String line) {
		String[] tokens = line.split(";");
		boolean isComputerPlayer = false;
		if(tokens[2].equals("true")) isComputerPlayer = true;
		Player player = new Player(tokens[0], isComputerPlayer);
		player.setReservedSpots(Integer.parseInt(tokens[1]));
		mainController.setPlayerTwo(player);
	}

	private void setActualPlayerFromFile(String name) {
		if(mainController.getPlayerOne().getName().equals(name)) mainController.setActualPlayer(mainController.getPlayerOne());
		else mainController.setActualPlayer(mainController.getPlayerTwo());
	}
	
	private Player getPlayerByName(String name) {
		if("ONE".equals(name)) return mainController.getPlayerOne();
		else if("TWO".equals(name)) return mainController.getPlayerTwo();
		return null;
	}
	
	
}
