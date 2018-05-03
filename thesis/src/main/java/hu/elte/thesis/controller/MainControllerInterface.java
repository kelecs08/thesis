package hu.elte.thesis.controller;

import java.util.List;

import hu.elte.thesis.model.ComputerPlayerDifficulty;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.view.MainWindow;
import hu.elte.thesis.view.dto.PlayerDto;
import hu.elte.thesis.view.dto.PositionDto;
import hu.elte.thesis.view.dto.RootChildDto;

/**
 * The controller of the application.
 * 
 * @author kelecs08
 */
public interface MainControllerInterface {

	public static final int ADDITIONAL_FIELDS = 4;
	public static final int INITIAL_TABLE_SIZE = 4;
	

	/**
	 * Start new game while keeping the settings.
	 */
	public void startNewGame();

	/**
	 * Start new game while keeping the settings.
	 * @param isComputerPlayer
	 */
	public void startNewGame(boolean isComputerPlayer);

	/**
	 * Change the size of the game board.
	 * @param tableSize
	 */
	public void changeTableSize(int tableSize);
	
	/**
	 * Change the size of the game board, filling it with the given table board positions.
	 * @param tablesize
	 * @param tableBoardPositions
	 */
	public void changeTableSize(int tablesize, Position[][] tableBoardPositions);

	/**
	 * Fills the current table board with starting values.
	 */
	public void fillDefaultTableBoard();
	
	/**
	 * Returns whether the current click was the first click of the actual player.
	 * @param row
	 * @param column
	 * @return {@link Boolean}
	 */
	public boolean isClickOne(int row, int column);
	
	/**
	 * Returns the list of positions that are first level positions of the clicked position.
	 * @return a list of {@link PositionDto}
	 */
	public List<PositionDto> getPositionsToBeRenderedFirstLevel();
	
	/**
	 * Returns the list of positions that are second level positions of the clicked position.
	 * @return a list of {@link PositionDto}
	 */
	public List<PositionDto> getPositionsToBeRenderedSecondLevel();
	
	/**
	 * Checks whether we want to click on the position which we have already clicked on. If we click on the cli
	 * @param row
	 * @param column
	 * @return {@link Boolean}
	 */
	public boolean isClickingTheClickedPosition(int row, int column);

	/**
	 * Step with the actual player.
	 * @param row
	 * @param column
	 * @return {@link Boolean} whether the step was successful.
	 */
	public boolean step(int row, int column);
	
	/**
	 * Returns whether the game ended in a draw when there is at least one player, who cannot step anymore.
	 * @return {@link Boolean}
	 */
	public boolean isDrawWhenStepsAreNotAvailable();
	
	/**
	 * Returns the winner when at least one player cannot step anymore.
	 * @return {@link Player}
	 */
	public PlayerDto getWinnerWhenStepsAreNotAvailable();

	/**
	 * Returns whether there are available steps for the current player.
	 * @return {@link Boolean}
	 */
	public boolean areStepsAvailable();

	/**
	 * Step with a computer player.
	 * @return {@link RootChildDto}
	 */
	public RootChildDto stepWithComputer();

	/**
	 * Returns whether the given position is a player one field.
	 * @param row
	 * @param column
	 * @return {@link Boolean}
	 */
	public boolean isPlayerOneField(int row, int column);
	
	/**
	 * Returns whether the given position is a player two field.
	 * @param row
	 * @param column
	 * @return {@link Boolean}
	 */
	public boolean isPlayerTwoField(int row, int column);

	/**
	 * Returns the winner of the game.
	 * @return {@link PlayerDto}
	 */
	public PlayerDto getWinner();

	/**
	 * Returns whether the game ended in a draw.
	 * @return {@link Boolean}
	 */
	public boolean isDraw();
	
	/**
	 * Updates the label displayed on the panel to match the actual game type and game difficulty if one player is a computer.
	 */
	public void updateGameTypeLabel();
	
	/**
	 * Sets player two's name to the given String.
	 */
	public void setPlayerTwoName(String name);
	
	public MainWindow getMainWindow();
	public void setMainWindow(MainWindow mainWindow);

	public GameType getGameType();
	public void setGameType(GameType gameType);

	public ComputerPlayerDifficulty getComputerPlayerDifficulty();
	public void setComputerPlayerDifficulty(ComputerPlayerDifficulty computerPlayerDifficulty);

	public Position[][] getTableBoardPositions();

	public int getTableSize();
	public void setTableSize(int tableSize);

	public Player getActualPlayer();
	public void setActualPlayer(Player actualPlayer);

	public Player getPlayerOne();
	public void setPlayerOne(Player playerOne);

	public Player getPlayerTwo();
	public void setPlayerTwo(Player playerTwo);
}
