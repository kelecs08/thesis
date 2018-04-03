package hu.elte.thesis.controller.service;

import hu.elte.thesis.model.Player;

public class PlayerService {

	public Player getWinner(int sum, Player playerOne, Player playerTwo) {
		int playerOneSpots = playerOne.getReservedSpots();
		int playerTwoSpots = playerTwo.getReservedSpots();
		if(playerOneSpots == 0 || playerTwoSpots == 0 || ( (playerOneSpots + playerTwoSpots) == sum) ) {
			if(playerOne.getReservedSpots() > playerTwo.getReservedSpots()) return playerOne;
			if(playerTwo.getReservedSpots() > playerOne.getReservedSpots()) return playerTwo;
		}
		return null;
	}
	
	public boolean isDrawWhenStepsAreNotAvailable(Player playerOne, Player playerTwo) {
		return playerOne.getReservedSpots() == playerTwo.getReservedSpots();
	}
	
	public Player getWinnerWhenStepsAreNotAvailable(Player playerOne, Player playerTwo) {
		if(playerOne.getReservedSpots() > playerTwo.getReservedSpots()) return playerOne;
		return playerTwo;
	}
	
	public boolean isDraw(int sum, Player playerOne, Player playerTwo) {
		int playerOneSpots = playerOne.getReservedSpots();
		int playerTwoSpots = playerTwo.getReservedSpots();
		return playerOneSpots == playerTwoSpots && ( (playerOneSpots + playerTwoSpots) == sum);
	}

	public void updatePlayers(Player playerOne, Player playerTwo, boolean isComputerPlayer) {
		setInitialReservedSpots(playerOne, 2);
		setInitialReservedSpots(playerTwo, 2);
		setComputerPlayer(playerTwo, isComputerPlayer);
	}
	
	public void setInitialReservedSpots(Player player, int reservedSpots) {
		player.setReservedSpots(reservedSpots);
	}
	
	public void setComputerPlayer(Player player, boolean isComputerPlayer) {
		player.setComputerPlayer(isComputerPlayer);
	}
}
