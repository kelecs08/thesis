package hu.elte.thesis.controller.service;

import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;

/**
 * Service class for {@link Player}.
 * 
 * @author kelecs08
 */
public class PlayerService {

	/**
	 * Get winner if all fields are taken.
	 * @param sum
	 * @param playerOne
	 * @param playerTwo
	 * @return {@link Player}
	 */
	public Player getWinner(int sum, Player playerOne, Player playerTwo) {
		int playerOneSpots = playerOne.getReservedSpots();
		int playerTwoSpots = playerTwo.getReservedSpots();
		if (playerOneSpots == 0 || playerTwoSpots == 0 || ((playerOneSpots + playerTwoSpots) == sum)) {
			if (playerOne.getReservedSpots() > playerTwo.getReservedSpots()) {
				return playerOne;
			}
			if (playerTwo.getReservedSpots() > playerOne.getReservedSpots()) {
				return playerTwo;
			}
		}
		return null;
	}

	/**
	 * Decides whether the game ended in a draw when there are no more steps available for a player.
	 * @param playerOne
	 * @param playerTwo
	 * @return boolean
	 */
	public boolean isDrawWhenStepsAreNotAvailable(Player playerOne, Player playerTwo) {
		return playerOne.getReservedSpots() == playerTwo.getReservedSpots();
	}

	/**
	 * Get winner when there are no more steps available for one player.
	 * @param playerOne
	 * @param playerTwo
	 * @return {@link Player}
	 */
	public Player getWinnerWhenStepsAreNotAvailable(Player playerOne, Player playerTwo) {
		if (playerOne.getReservedSpots() > playerTwo.getReservedSpots()) {
			return playerOne;
		}
		return playerTwo;
	}

	/**
	 * Decides whether the game ended in a draw when all fields are taken.
	 * @param sum
	 * @param playerOne
	 * @param playerTwo
	 * @return boolean
	 */
	public boolean isDraw(int sum, Player playerOne, Player playerTwo) {
		int playerOneSpots = playerOne.getReservedSpots();
		int playerTwoSpots = playerTwo.getReservedSpots();
		return playerOneSpots == playerTwoSpots && ((playerOneSpots + playerTwoSpots) == sum);
	}

	/**
	 * Set players to their initial state keeping their actual name.
	 * @param playerOne
	 * @param playerTwo
	 * @param isComputerPlayer
	 */
	public void updatePlayers(Player playerOne, Player playerTwo, boolean isComputerPlayer) {
		setInitialReservedSpots(playerOne, 2);
		setInitialReservedSpots(playerTwo, 2);
		setComputerPlayer(playerTwo, isComputerPlayer);
	}

	private void setInitialReservedSpots(Player player, int reservedSpots) {
		player.setReservedSpots(reservedSpots);
	}

	private void setComputerPlayer(Player player, boolean isComputerPlayer) {
		player.setComputerPlayer(isComputerPlayer);
	}

	/**
	 * Decides whether the given field is one of the first player's fields.
	 * @param tabbleBoardPositions
	 * @param playerOne
	 * @param row
	 * @param column
	 * @return boolean
	 */
	public boolean isPlayerOneField(Position[][] tabbleBoardPositions, Player playerOne, int row, int column) {
		Player player = getPlayerAtGivenPosition(tabbleBoardPositions, row, column);
		if (player == null) {
			return false;
		} else {
			return player.equals(playerOne);
		}
	}

	/**
	 * Decides whether the given field is one of the second player's fields.
	 * @param tableBoardPositions
	 * @param playerTwo
	 * @param row
	 * @param column
	 * @return boolean
	 */
	public boolean isPlayerTwoField(Position[][] tableBoardPositions, Player playerTwo, int row, int column) {
		Player player = getPlayerAtGivenPosition(tableBoardPositions, row, column);
		if (player == null) {
			return false;
		} else {
			return player.equals(playerTwo);
		}
	}
	
	private Player getPlayerAtGivenPosition(Position[][] tableBoardPositions, int row, int column) {
		return tableBoardPositions[row + 2][column + 2].getPlayer();
	}
}
