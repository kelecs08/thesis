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
	
	public boolean isDraw(int sum, Player playerOne, Player playerTwo) {
		int playerOneSpots = playerOne.getReservedSpots();
		int playerTwoSpots = playerTwo.getReservedSpots();
		return playerOneSpots == playerTwoSpots && ( (playerOneSpots + playerTwoSpots) == sum);
	}
}
