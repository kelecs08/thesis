package hu.elte.thesis.controller;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.controller.algorithm.MinimaxAlgorithm;
import hu.elte.thesis.controller.algorithm.RandomAlgorithm;
import hu.elte.thesis.controller.service.PlayerService;
import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.model.ComputerPlayerDifficulty;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.model.RootChild;
import hu.elte.thesis.model.transformer.ModelTransformer;
import hu.elte.thesis.view.MainWindow;
import hu.elte.thesis.view.dto.PlayerDto;
import hu.elte.thesis.view.dto.PositionDto;
import hu.elte.thesis.view.dto.RootChildDto;

/**
 * The implementation of the controller of the application.
 * 
 * @author kelecs08
 */
public class MainController implements MainControllerInterface {

	private MainWindow mainWindow;

	private int tableSize;
	private Position[][] tableBoardPositions;

	private GameType gameType = GameType.TWO_PLAYER;
	private ComputerPlayerDifficulty computerPlayerDifficulty = ComputerPlayerDifficulty.EASY;

	private Player playerOne, playerTwo;
	private Player actualPlayer;

	private Position clickedPosition;

	private List<Position> firstLevel, secondLevel;

	private final MinimaxAlgorithm minimaxAlgorithm = new MinimaxAlgorithm();
	private final RandomAlgorithm randomAlgorithm = new RandomAlgorithm();
	private final TableBoardService tableBoardService = new TableBoardService();
	private final PlayerService playerService = new PlayerService();
	private final ModelTransformer modelTransformer = new ModelTransformer();

	/**
	 * Constructor for the MainController class.
	 */
	public MainController() {
		initializeTableBoard(INITIAL_TABLE_SIZE);
		initializePlayers(false);
		initializeActualPlayer();
	}

	private void initializeTableBoard(int tableSize) {
		this.tableSize = tableSize;
		this.tableBoardPositions = new Position[tableSize + ADDITIONAL_FIELDS][tableSize + ADDITIONAL_FIELDS];
		this.clickedPosition = null;

		this.firstLevel = new ArrayList<>();
		this.secondLevel = new ArrayList<>();
	}

	private void initializePlayers(boolean isComputerPlayer) {
		this.playerOne = new Player("Player1", false);
		this.playerTwo = new Player("Player2", false);
	}

	private void initializeActualPlayer() {
		this.actualPlayer = this.playerOne;
	}

	@Override
	public void startNewGame() {
		startNewGame(playerTwo.isComputerPlayer());
	}

	@Override
	public void startNewGame(boolean isComputerPlayer) {
		initializeTableBoard(this.tableSize);

		this.playerService.updatePlayers(playerOne, playerTwo, isComputerPlayer);
		updateGameTypeLabel();
		initializeActualPlayer();
		this.clickedPosition = null;

		fillDefaultTableBoard();
		this.mainWindow.getGamePanel().updateFields();
	}

	@Override
	public void updateGameTypeLabel() {
		mainWindow.getGamePanel().getFooter().setText((gameType.equals(GameType.TWO_PLAYER)) ? gameType.getTypeString() : gameType.getTypeString() + ": " + computerPlayerDifficulty.getDifficultyString());
	}

	@Override
	public void changeTableSize(int tableSize) {
		initializeTableBoard(tableSize);
		this.playerService.updatePlayers(playerOne, playerTwo, playerTwo.isComputerPlayer());
		fillDefaultTableBoard();
	}
	
	@Override
	public void changeTableSize(int tablesize, Position[][] tableBoardPositions) {
		this.tableSize = tablesize;
		this.tableBoardPositions = tableBoardPositions;
		this.clickedPosition = null;
		this.firstLevel = new ArrayList<>();
		this.secondLevel = new ArrayList<>();
	}

	@Override
	public void fillDefaultTableBoard() {
		tableBoardService.fillDefaultTableBoard(tableSize, tableBoardPositions, playerOne, playerTwo);
	}

	@Override
	public boolean isClickOne(int row, int column) {
		if (this.clickedPosition != null) {
			return false; 												// someone already clicked before
		}
		Position position = this.tableBoardPositions[row][column]; 		// the clicked position
		Player player = position.getPlayer(); 							// the player in the clicked position --> player1, player2, null !!
		if (player != null && player.equals(actualPlayer) && !actualPlayer.isComputerPlayer() && tableBoardService.checkStepAvailable(position, tableBoardPositions)) {
			this.clickedPosition = position;
		}
		return this.clickedPosition != null;
	}

	@Override
	public List<PositionDto> getPositionsToBeRenderedFirstLevel() { // because of not valid fields the array size differs according to clicked position
		List<PositionDto> positionsToBeRenderedFirstLevel = new ArrayList<>();
		int row = this.clickedPosition.getRow();
		int column = this.clickedPosition.getColumn();
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				Position position = this.tableBoardPositions[i][j];
				if (position.isValidSpace() && position != this.clickedPosition && position.getPlayer() == null) {
					firstLevel.add(position);
					positionsToBeRenderedFirstLevel.add(modelTransformer.transformPositionToPositionDto(tableBoardPositions[i - 2][j - 2]));
				}
			}
		}
		return positionsToBeRenderedFirstLevel;
	}

	@Override
	public List<PositionDto> getPositionsToBeRenderedSecondLevel() {
		List<PositionDto> positionsToBeRenderedSecondLevel = new ArrayList<>();
		int row = this.clickedPosition.getRow();
		int column = this.clickedPosition.getColumn();
		for (int i = row - 2; i <= row + 2; i++) {
			for (int j = column - 2; j <= column + 2; j++) {
				Position position = this.tableBoardPositions[i][j];
				if (position.isValidSpace() && position != this.clickedPosition && position.getPlayer() == null) {
					secondLevel.add(position);
					positionsToBeRenderedSecondLevel.add(modelTransformer.transformPositionToPositionDto(tableBoardPositions[i - 2][j - 2]));
				}
			}
		}
		List<PositionDto> positionsToBeRenderedFirstLevel = getPositionsToBeRenderedFirstLevel();
		secondLevel.removeAll(firstLevel);
		positionsToBeRenderedSecondLevel.removeAll(positionsToBeRenderedFirstLevel);
		return positionsToBeRenderedSecondLevel;
	}

	@Override
	public boolean isClickingTheClickedPosition(int row, int column) {
		if (isClickedPosition(row, column)) {
			this.clickedPosition = null;
			firstLevel = new ArrayList<>();
			secondLevel = new ArrayList<>();
			return true;
		}
		return false;
	}

	private boolean isClickedPosition(int row, int column) {
		return (this.clickedPosition != null && this.clickedPosition.getRow() == row && this.clickedPosition.getColumn() == column);
	}

	@Override
	public boolean step(int row, int column) {
		Position position = cleavage(row, column); // osztódás
		if (position != null) {
			position.setPlayer(actualPlayer);
			actualPlayer.modifyReservedSpotsNumber(1);
			overtakeFields(position, actualPlayer);
			updateDataFields();
			return true;
		} else {
			position = jump(row, column); // ugrás
			if (position != null) {
				position.setPlayer(actualPlayer);
				clickedPosition.setPlayer(null);
				overtakeFields(position, actualPlayer);
				updateDataFields();
				return true;
			}
		}
		return false;
	}

	private void overtakeFields(Position position, Player player) {
		List<Position> firstLevelPositions = tableBoardService.getFirstLevel(position, tableBoardPositions);
		int countOfOvertakenPositions = 0;
		for (int i = 0; i < firstLevelPositions.size(); i++) {
			Position possibleOvertake = firstLevelPositions.get(i);
			if (possibleOvertake.getPlayer() != null && possibleOvertake.getPlayer() != player) {
				firstLevelPositions.get(i)
					.setPlayer(player);
				countOfOvertakenPositions++;
			}
		}
		player.modifyReservedSpotsNumber(countOfOvertakenPositions);
		countOfOvertakenPositions *= -1;
		getOtherPlayer(player).modifyReservedSpotsNumber(countOfOvertakenPositions);
	}

	private void updateDataFields() {
		firstLevel = new ArrayList<>();
		secondLevel = new ArrayList<>();
		switchPlayer();
		clickedPosition = null;
	}

	private Position cleavage(int row, int column) {
		for (int i = 0; i < firstLevel.size(); i++) {
			Position position = firstLevel.get(i);
			if (position.getRow() == row && position.getColumn() == column) {
				return position;
			}
		}
		return null;
	}

	private Position jump(int row, int column) {
		for (int i = 0; i < secondLevel.size(); i++) {
			Position position = secondLevel.get(i);
			if (position.getRow() == row && position.getColumn() == column) {
				return position;
			}
		}
		return null;
	}

	private void switchPlayer() {
		if (this.actualPlayer.equals(playerOne)) {
			this.actualPlayer = playerTwo;
		} else {
			this.actualPlayer = playerOne;
		}
	}

	@Override
	public boolean isDrawWhenStepsAreNotAvailable() {
		return playerService.isDrawWhenStepsAreNotAvailable(playerOne, playerTwo);
	}

	@Override
	public PlayerDto getWinnerWhenStepsAreNotAvailable() {
		return modelTransformer.transformPlayerToPlayerDto(playerService.getWinnerWhenStepsAreNotAvailable(playerOne, playerTwo));
	}

	@Override
	public boolean areStepsAvailable() {
		List<Position> positions = new ArrayList<>();
		for (int i = 2; i < tableSize + 2; i++) {
			for (int j = 2; j < tableSize + 2; j++) {
				Player tmp = tableBoardPositions[i][j].getPlayer();
				if (tmp != null && tmp.equals(this.actualPlayer)) {
					positions.add(tableBoardPositions[i][j]);
				}
			}
		}
		return this.tableBoardService.checkStepsAvailableForAllPlayerField(positions, tableBoardPositions);
	}

	@Override
	public RootChildDto stepWithComputer() {
		if (playerTwo.isComputerPlayer()) {
			RootChild rootChild = null;
			if (this.computerPlayerDifficulty == ComputerPlayerDifficulty.EASY) {
				rootChild = randomAlgorithm.getRandomRootChild(tableBoardPositions, playerTwo);
			} else if(this.computerPlayerDifficulty == ComputerPlayerDifficulty.MEDIUM) {
				rootChild = minimaxAlgorithm.getStepUsingMinimax(tableBoardPositions, playerOne, playerTwo, 3, 3);
			} else {
				rootChild = minimaxAlgorithm.getBestStepAtEachRound(tableBoardPositions, playerTwo);
			}
			Position rootPosition = rootChild.getRootPosition();
			Position positionToStep = rootChild.getBestChildPosition();
			if (positionToStep != null) {
				if (tableBoardService.isFirstLevelPositionOfTheGivenPosition(tableBoardPositions, rootPosition, positionToStep)) {
					playerTwo.modifyReservedSpotsNumber(1);
				} else if (tableBoardService.isSecondLevelPositionOfTheGivenPosition(tableBoardPositions, rootPosition, positionToStep)) {
					tableBoardPositions[rootPosition.getRow()][rootPosition.getColumn()].setPlayer(null);
				}
				positionToStep.setPlayer(playerTwo);
				overtakeFields(positionToStep, playerTwo);
				updateDataFields();
				return modelTransformer.transformRootChildToRootChildDto(rootChild);
			}
		}
		return null;
	}

	private Player getOtherPlayer(Player player) {
		if (player.equals(playerOne)) {
			return playerTwo;
		}
		return playerOne;
	}
	
	@Override
	public boolean isPlayerOneField(int row, int column) {
		return playerService.isPlayerOneField(tableBoardPositions, playerOne, row, column);
	}
	
	@Override
	public boolean isPlayerTwoField(int row, int column) {
		return playerService.isPlayerTwoField(tableBoardPositions, playerTwo, row, column);
	}

	@Override
	public PlayerDto getWinner() {
		return modelTransformer.transformPlayerToPlayerDto(playerService.getWinner(tableSize * tableSize, playerOne, playerTwo));
	}

	@Override
	public boolean isDraw() {
		return playerService.isDraw(tableSize * tableSize, playerOne, playerTwo);
	}
	
	@Override
	public void setPlayerTwoName(String name) { 
		this.playerTwo.setName(name);
		mainWindow.getGamePanel().setPlayerTwoNameButtonText(name);
	}

	public MainWindow getMainWindow() { return this.mainWindow; }
	public void setMainWindow(MainWindow mainWindow) { this.mainWindow = mainWindow; }

	public GameType getGameType() {	return this.gameType; }
	public void setGameType(GameType gameType) { this.gameType = gameType; }

	public ComputerPlayerDifficulty getComputerPlayerDifficulty() { return this.computerPlayerDifficulty; }
	public void setComputerPlayerDifficulty(ComputerPlayerDifficulty computerPlayerDifficulty) { this.computerPlayerDifficulty = computerPlayerDifficulty; }

	public Position[][] getTableBoardPositions() { return this.tableBoardPositions; }

	public int getTableSize() {	return this.tableSize; }
	public void setTableSize(int tableSize) { this.tableSize = tableSize; }

	public Player getActualPlayer() { return this.actualPlayer; }
	public void setActualPlayer(Player actualPlayer) { this.actualPlayer = actualPlayer; }

	public Player getPlayerOne() { return playerOne; }
	public void setPlayerOne(Player playerOne) { this.playerOne = playerOne; }

	public Player getPlayerTwo() { return playerTwo; }
	public void setPlayerTwo(Player playerTwo) { this.playerTwo = playerTwo; }

}
