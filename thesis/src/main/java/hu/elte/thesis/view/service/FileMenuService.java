package hu.elte.thesis.view.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;

public class FileMenuService {
	
	private MainController mainController;
	
	public FileMenuService(MainController mainController) {
		this.mainController = mainController;
	}

	public void writeToFile(String fileName, Player actualPlayer, Player playerOne, Player playerTwo, Position[][] tableBoardPositions) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
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
	
	public void loadFromFile(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = bufferedReader.readLine();
		setPlayerOneFromFile(line);
		
		line = bufferedReader.readLine();
		setPlayerTwoFromFile(line);
		
		line = bufferedReader.readLine();
		setActualPlayerFromFile(line);
		
		line = bufferedReader.readLine();
		mainController.setTableSize(Integer.parseInt(line)-4);
		
		Position[][] tableBoardPositions = new Position[mainController.getTableSize()+4][mainController.getTableSize()+4];
		while((line = bufferedReader.readLine()) != null) {
			String[] tokens = line.split(";");
			int row = Integer.parseInt(tokens[0]);
			int column = Integer.parseInt(tokens[1]);
			Player player = getPlayerByName(tokens[2]);
			boolean isValidSpace = true;
			if("false".equals(tokens[3])) isValidSpace = false;
			tableBoardPositions[row][column] = new Position(row, column, player, isValidSpace);
		}
		mainController.setTableBoardPositions(tableBoardPositions);
		bufferedReader.close();
	}

	private void setPlayerOneFromFile(String line) {
		String[] tokens = line.split(";");
		Player player = new Player(tokens[0], false);
		player.setReservedSpots(Integer.parseInt(tokens[1]));
		mainController.setPlayerOne(player);
	}

	private void setPlayerTwoFromFile(String line) {
		String[] tokens = line.split(";");
		Player player = new Player(tokens[0], Boolean.getBoolean(tokens[2]));
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
