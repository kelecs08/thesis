package hu.elte.thesis.controller;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.view.MainWindow;
import hu.elte.thesis.view.dto.SimplePosition;

public abstract class MainController {

	private static final int ADDITIONAL_FIELDS = 4;
	private static final int INITIAL_TABLE_SIZE = 4;

	protected MainWindow mainWindow;
	
	protected int tableSize;
	protected int additionalTableSize;
	protected Position[][] tableBoardPositions;
	
	protected Player playerOne;
	protected Player playerTwo;
	
	protected Position clickedPosition;
	protected Player actualPlayer;
	
	protected List<Position> firstLevel;
	protected List<Position> secondLevel;
	
	
	public MainController() {
		initializeTableBoard(INITIAL_TABLE_SIZE);
		initializePlayers();
	}

	private void initializeTableBoard(int tableSize) {
		this.tableSize = tableSize;
		this.additionalTableSize = tableSize + ADDITIONAL_FIELDS;
		
		this.tableBoardPositions = new Position[additionalTableSize][additionalTableSize];
		
		this.firstLevel = new ArrayList<>();
		this.secondLevel = new ArrayList<>();
	}

	private void initializePlayers() {
		this.playerOne = new Player("Player1", true);
		this.playerTwo = new Player("Player2", false);
		this.actualPlayer = this.playerOne;
	}
	
	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public int getTableSize() {
		return this.tableSize;
	}

	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}
	
	public Player getActualPlayer() {
		return this.actualPlayer;
	}
	
	public void setActualPlayer(Player actualPlayer) {
		this.actualPlayer = actualPlayer;
	}
	
	public void fillDefaultTableBoard() {
		for(int i = 0; i < 2; i++) { //first two row, column
			for(int j = 0; j < additionalTableSize; j++) {
				this.tableBoardPositions[i][j] = new Position(i, j, null, false);
				this.tableBoardPositions[j][i] = new Position(j, i, null, false);
			}
		}
		for(int i = additionalTableSize-2; i < additionalTableSize; i++) { //last two row, column
			for(int j = 0; j < additionalTableSize; j++) {
				this.tableBoardPositions[i][j] = new Position(i, j, null, false);
				this.tableBoardPositions[j][i] = new Position(j, i, null, false);
			}
		}		
		for(int i = 2; i < this.additionalTableSize - 2; i++) {
			for(int j = 2; j < this.additionalTableSize - 2; j++) {
				this.tableBoardPositions[i][j] = new Position(i, j, null, true); //table center
			}
		}
		//initial player fields
		this.tableBoardPositions[2][2] = new Position(2, 2, playerOne, true);
		this.tableBoardPositions[additionalTableSize-3][2] = new Position(additionalTableSize-3, 2, playerOne, true);
		this.tableBoardPositions[2][additionalTableSize-3] = new Position(2, additionalTableSize-3, playerTwo, true);
		this.tableBoardPositions[additionalTableSize-3][additionalTableSize-3] = new Position(additionalTableSize-3, additionalTableSize-3, playerTwo, true);
		
		this.mainWindow.createDefaultTableBoard();
	}
	
	public boolean isPlayerOneField(int row, int column) {
		Player player = getPlayerAtGivenPosition(row, column);
		if(player == null) return false;
		else return player.equals(playerOne);
	}
	
	public boolean isPlayerTwoField(int row, int column) {
		Player player = getPlayerAtGivenPosition(row, column);
		if(player == null) return false;
		else return player.equals(playerTwo);
	}

	private Player getPlayerAtGivenPosition(int row, int column) {
		return this.tableBoardPositions[row+2][column+2].getPlayer();
	}

	public boolean isClickOne(int row, int column) {
		if(this.clickedPosition != null) return false; 				//someone already clicked before
		Position position = this.tableBoardPositions[row][column]; 	//the clicked position
		Player player = position.getPlayer(); 						//the player in the clicked position --> player1, player2, null !!
		if(player != null && player.equals(actualPlayer))
			this.clickedPosition = position;
		return this.clickedPosition != null;
	}
	
	public List<SimplePosition> getPositionsToBeRenderedFirstLevel() { //because of not valid fields the array size differs according to clicked position
		List<SimplePosition> positionsToBeRenderedFirstLevel = new ArrayList<>();
		int row = this.clickedPosition.getRow();
		int column = this.clickedPosition.getColumn();
		for(int i = row-1; i <= row+1; i++) {
			for(int j = column-1; j <= column+1; j++) {
				Position position = this.tableBoardPositions[i][j];
				if(position.isValidSpace() && position != this.clickedPosition && position.getPlayer() == null) {
					firstLevel.add(position);
					positionsToBeRenderedFirstLevel.add(new SimplePosition(i-2, j-2));
				}
			}
		}
		return positionsToBeRenderedFirstLevel;
	}

	public List<SimplePosition> getPositionsToBeRenderedSecondLevel() {
		List<SimplePosition> positionsToBeRenderedSecondLevel = new ArrayList<>();
		int row = this.clickedPosition.getRow();
		int column = this.clickedPosition.getColumn();
		for(int i = row-2; i <= row+2; i++) {
			for(int j = column-2; j <= column+2; j++) {
				Position position = this.tableBoardPositions[i][j];
				if(position.isValidSpace() && position != this.clickedPosition && position.getPlayer() == null) {
					secondLevel.add(position);
					positionsToBeRenderedSecondLevel.add(new SimplePosition(i-2, j-2));
				}
			}
		}
		List<SimplePosition>  positionsToBeRenderedFirstLevel = getPositionsToBeRenderedFirstLevel();
		secondLevel.removeAll(firstLevel);
		positionsToBeRenderedSecondLevel.removeAll(positionsToBeRenderedFirstLevel);
		return positionsToBeRenderedSecondLevel;
	}

	public boolean isClickingTheClickedPosition(int row, int column) {
		if(this.clickedPosition != null && this.clickedPosition.getRow() == row && this.clickedPosition.getColumn() == column) {
			this.clickedPosition = null;
			firstLevel = new ArrayList<>();
			secondLevel = new ArrayList<>();
			return true;
		}
		return false;
	}

	public boolean step(int row, int column) {
		Position position = cleavage(row, column);
		if(position != null) {
			position.setPlayer(actualPlayer);
			actualPlayer.modifyReservedSpotsNumber(1);
			overtakeFields(position);
			updateDataFields();
			return true;
		} else {
			position = jump(row, column);
			if(position != null) {
				position.setPlayer(actualPlayer);
				clickedPosition.setPlayer(null);
				overtakeFields(position);
				updateDataFields();
				return true;
			}
		}
		return false;
	}
	
	private void overtakeFields(Position position) {
		List<Position> firstLevelPositions = getFirstLevel(position);		
		int countOfOvertakenPositions = 0;
		for(int i = 0; i < firstLevelPositions.size(); i++) {
			Position possibleOvertake = firstLevelPositions.get(i);
			if(possibleOvertake.getPlayer() != null && possibleOvertake.getPlayer() != actualPlayer) {
				firstLevelPositions.get(i).setPlayer(actualPlayer);
				countOfOvertakenPositions++;
			}
		}
		actualPlayer.modifyReservedSpotsNumber(countOfOvertakenPositions);
		countOfOvertakenPositions *= -1;
		getOtherPlayer(actualPlayer).modifyReservedSpotsNumber(countOfOvertakenPositions);
	}

	private List<Position> getFirstLevel(Position position) {
		List<Position> firstLevelPositions = new ArrayList<>();
		int row = position.getRow();
		int column = position.getColumn();
		for(int i = row-1; i <= row+1; i++) {
			for(int j = column-1; j <= column+1; j++) {
				Position p = this.tableBoardPositions[i][j];
				if(p.isValidSpace() && p != position) {
					firstLevelPositions.add(p);
				}
			}
		}
		return firstLevelPositions;
	}

	private void updateDataFields() {
		firstLevel = new ArrayList<>();
		secondLevel = new ArrayList<>();
		switchPlayer();
		clickedPosition = null;
	}

	private void switchPlayer() {
		if(this.actualPlayer.equals(playerOne))
			this.actualPlayer = playerTwo;
		else
			this.actualPlayer = playerOne;
	}

	public Position cleavage(int row, int column) {
		for(int i = 0; i < firstLevel.size(); i++) {
			Position position = firstLevel.get(i);
			if(position.getRow() == row && position.getColumn() == column) {
				return position;
			}
		}
		return null;
	}
	
	public Position jump(int row, int column) {
		for(int i = 0; i < secondLevel.size(); i++) {
			Position position = secondLevel.get(i);
			if(position.getRow() == row && position.getColumn() == column) {
				return position;
			}
		}
		return null;
	}
	
	public Player getOtherPlayer(Player player) {
		if(player.equals(playerOne)) {
			return playerTwo;
		}
		return playerOne;
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

}
