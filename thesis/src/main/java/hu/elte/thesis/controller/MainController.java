package hu.elte.thesis.controller;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.controller.service.PlayerService;
import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.minimax.MinimaxAlgorithm;
import hu.elte.thesis.minimax.RootChild;
import hu.elte.thesis.model.ComputerPlayerDifficulty;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.random.RandomAlgorithm;
import hu.elte.thesis.view.MainWindow;
import hu.elte.thesis.view.dto.SimplePosition;

/**
 * The controller of the application.
 */
public class MainController {

	public static final int ADDITIONAL_FIELDS = 4;
	public static final int INITIAL_TABLE_SIZE = 4;

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
		this.playerService.setComputerPlayer(playerTwo, isComputerPlayer);
	}

	private void initializeActualPlayer() {
		this.actualPlayer = this.playerOne;
	}

	public void startNewGame() {
		startNewGame(playerTwo.isComputerPlayer());
	}

	public void startNewGame(boolean isComputerPlayer) {
		initializeTableBoard(this.tableSize);

		this.playerService.updatePlayers(playerOne, playerTwo, isComputerPlayer);
		initializeActualPlayer();
		this.clickedPosition = null;

		fillDefaultTableBoard();
		this.mainWindow.getGamePanel()
			.updateFields();
	}

	public void changeTableSize(int tableSize) {
		initializeTableBoard(tableSize);
		this.playerService.updatePlayers(playerOne, playerTwo, playerTwo.isComputerPlayer());
		fillDefaultTableBoard();
	}

	public void fillDefaultTableBoard() {
		tableBoardService.fillDefaultTableBoard(tableSize, tableBoardPositions, playerOne, playerTwo);
	}

	public boolean isPlayerOneField(int row, int column) {
		Player player = getPlayerAtGivenPosition(row, column);
		if (player == null) {
			return false;
		} else {
			return player.equals(playerOne);
		}
	}

	public boolean isPlayerTwoField(int row, int column) {
		Player player = getPlayerAtGivenPosition(row, column);
		if (player == null) {
			return false;
		} else {
			return player.equals(playerTwo);
		}
	}

	private Player getPlayerAtGivenPosition(int row, int column) {
		return this.tableBoardPositions[row + 2][column + 2].getPlayer();
	}

	public boolean isClickOne(int row, int column) {
		if (this.clickedPosition != null) {
			return false; // someone already clicked before
		}
		Position position = this.tableBoardPositions[row][column]; // the clicked position
		Player player = position.getPlayer(); // the player in the clicked position --> player1, player2, null !!
		if (player != null && player.equals(actualPlayer) && !actualPlayer.isComputerPlayer()
				&& tableBoardService.checkStepAvailable(position, tableBoardPositions)) {
			this.clickedPosition = position;
		}
		return this.clickedPosition != null;
	}

	public List<SimplePosition> getPositionsToBeRenderedFirstLevel() { // because of not valid fields the array size differs according to clicked position
		List<SimplePosition> positionsToBeRenderedFirstLevel = new ArrayList<>();
		int row = this.clickedPosition.getRow();
		int column = this.clickedPosition.getColumn();
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				Position position = this.tableBoardPositions[i][j];
				if (position.isValidSpace() && position != this.clickedPosition && position.getPlayer() == null) {
					firstLevel.add(position);
					positionsToBeRenderedFirstLevel.add(new SimplePosition(i - 2, j - 2));
				}
			}
		}
		return positionsToBeRenderedFirstLevel;
	}

	public List<SimplePosition> getPositionsToBeRenderedSecondLevel() {
		List<SimplePosition> positionsToBeRenderedSecondLevel = new ArrayList<>();
		int row = this.clickedPosition.getRow();
		int column = this.clickedPosition.getColumn();
		for (int i = row - 2; i <= row + 2; i++) {
			for (int j = column - 2; j <= column + 2; j++) {
				Position position = this.tableBoardPositions[i][j];
				if (position.isValidSpace() && position != this.clickedPosition && position.getPlayer() == null) {
					secondLevel.add(position);
					positionsToBeRenderedSecondLevel.add(new SimplePosition(i - 2, j - 2));
				}
			}
		}
		List<SimplePosition> positionsToBeRenderedFirstLevel = getPositionsToBeRenderedFirstLevel();
		secondLevel.removeAll(firstLevel);
		positionsToBeRenderedSecondLevel.removeAll(positionsToBeRenderedFirstLevel);
		return positionsToBeRenderedSecondLevel;
	}

	public boolean isClickedPosition(int row, int column) {
		return (this.clickedPosition != null && this.clickedPosition.getRow() == row && this.clickedPosition.getColumn() == column);
	}

	public boolean isClickingTheClickedPosition(int row, int column) {
		if (isClickedPosition(row, column)) {
			this.clickedPosition = null;
			firstLevel = new ArrayList<>();
			secondLevel = new ArrayList<>();
			return true;
		}
		return false;
	}

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

	public Position cleavage(int row, int column) {
		for (int i = 0; i < firstLevel.size(); i++) {
			Position position = firstLevel.get(i);
			if (position.getRow() == row && position.getColumn() == column) {
				return position;
			}
		}
		return null;
	}

	public Position jump(int row, int column) {
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

	public boolean isDrawWhenStepsAreNotAvailable() {
		return playerService.isDrawWhenStepsAreNotAvailable(playerOne, playerTwo);
	}

	public Player getWinnerWhenStepsAreNotAvailable() {
		return playerService.getWinnerWhenStepsAreNotAvailable(playerOne, playerTwo);
	}

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
		System.out.println(positions.size());
		return this.tableBoardService.checkStepsAvailableForAllPlayerField(positions, tableBoardPositions);
	}

	public RootChild stepWithComputer() {
		if (playerTwo.isComputerPlayer()) {
			List<Position> rootPositions = new ArrayList<>();
			for (int i = 2; i < tableSize + 2; i++) {
				for (int j = 2; j < tableSize + 2; j++) {
					Player tmp = tableBoardPositions[i][j].getPlayer();
					if (tmp != null && tmp.isComputerPlayer()) {
						rootPositions.add(tableBoardPositions[i][j]);
					}
				}
			}
			RootChild rootChild = null;
			if (this.computerPlayerDifficulty == ComputerPlayerDifficulty.EASY) {
				rootChild = randomAlgorithm.getRandomRootChild(rootPositions, tableBoardPositions);
			} else {
				rootChild = minimaxAlgorithm.getRootAndPositionToBeStepped(rootPositions, 1, tableBoardPositions);
			}
			Position rootPosition = rootChild.getRootPosition();
			Position positionToStep = rootChild.getBestChildPosition();
			if (positionToStep != null) {
				positionToStep.setPlayer(playerTwo);
				int score = rootChild.getBestScore();
				if (score % 10 == 1) {
					playerTwo.modifyReservedSpotsNumber(1);
				} else if (score % 10 == 0) {
					tableBoardPositions[rootPosition.getRow()][rootPosition.getColumn()].setPlayer(null);
				}
				overtakeFields(positionToStep, playerTwo);
				updateDataFields();
				return rootChild;
			}
		}
		return null;
	}

	public Player getOtherPlayer(Player player) {
		if (player.equals(playerOne)) {
			return playerTwo;
		}
		return playerOne;
	}

	public Player getWinner() {
		return playerService.getWinner(tableSize * tableSize, playerOne, playerTwo);
	}

	public boolean isDraw() {
		return playerService.isDraw(tableSize * tableSize, playerOne, playerTwo);
	}

	public MainWindow getMainWindow() {
		return this.mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public GameType getGameType() {
		return this.gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public ComputerPlayerDifficulty getComputerPlayerDifficulty() {
		return this.computerPlayerDifficulty;
	}

	public void setComputerPlayerDifficulty(ComputerPlayerDifficulty computerPlayerDifficulty) {
		this.computerPlayerDifficulty = computerPlayerDifficulty;
	}

	public Position[][] getTableBoardPositions() {
		return this.tableBoardPositions;
	}

	public void setTableBoardPositions(Position[][] tableBoardPositions2) {
		this.tableBoardPositions = tableBoardPositions;
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

	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

}
