package hu.elte.thesis.controller.service;

import static org.junit.Assert.*;

import org.junit.Test;

import hu.elte.thesis.controller.service.PlayerService;
import hu.elte.thesis.model.Player;

/**
 * Test class for {@link PlayerService}.
 * 
 * @author kelecs08
 */
public class PlayerServiceTest {

	private PlayerService underTest = new PlayerService();

	@Test
	public void getWinnerWhenWinnerIsPlayerOne() {
		// GIVEN
		Player playerOne = getPlayer(10);
		Player playerTwo = getPlayer(6);
		// WHEN
		Player winner = underTest.getWinner(16, playerOne, playerTwo);
		// THEN
		assertEquals(playerOne, winner);
	}

	@Test
	public void getWinnerWhenOnePlayerHasNoMoreSpots() {
		// GIVEN
		Player playerOne = getPlayer(0);
		Player playerTwo = getPlayer(5);
		// WHEN
		Player winner = underTest.getWinner(10, playerOne, playerTwo);
		// THEN
		assertEquals(playerTwo, winner);
	}

	@Test
	public void getWinnerWhenThereIsNoWinner() {
		// GIVEN
		Player playerOne = getPlayer(1);
		Player playerTwo = getPlayer(1);
		// WHEN
		Player winner = underTest.getWinner(3, playerOne, playerTwo);
		// THEN
		assertEquals(null, winner);
	}

	@Test
	public void isDrawWhenStepsAreNotAvailableTrue() {
		// GIVEN
		Player playerOne = getPlayer(6);
		Player playerTwo = getPlayer(6);
		// WHEN
		boolean isDraw = underTest.isDrawWhenStepsAreNotAvailable(playerOne, playerTwo);
		// THEN
		assertTrue(isDraw);
	}

	@Test
	public void isDrawWhenStepsAreNotAvailableFalse() {
		// GIVEN
		Player playerOne = getPlayer(5);
		Player playerTwo = getPlayer(6);
		// WHEN
		boolean isDraw = underTest.isDrawWhenStepsAreNotAvailable(playerOne, playerTwo);
		// THEN
		assertFalse(isDraw);
	}

	@Test
	public void getWinnerWhenStepsAreNotAvailable() {
		// GIVEN
		Player playerOne = getPlayer(8);
		Player playerTwo = getPlayer(6);
		// WHEN
		Player winner = underTest.getWinnerWhenStepsAreNotAvailable(playerOne, playerTwo);
		// THEN
		assertEquals(playerOne, winner);
	}

	@Test
	public void isDrawTrue() {
		// GIVEN
		Player playerOne = getPlayer(5);
		Player playerTwo = getPlayer(5);
		// WHEN
		boolean isDraw = underTest.isDraw(10, playerOne, playerTwo);
		// THEN
		assertTrue(isDraw);
	}
	
	@Test
	public void updatePlayers() {
		// GIVEN
		Player playerOne = getPlayer(6);
		Player playerTwo = getPlayer(6);
		// WHEN
		underTest.updatePlayers(playerOne, playerTwo, true);
		//THEN
		assertEquals(2, playerOne.getReservedSpots());
		assertEquals(2, playerTwo.getReservedSpots());
		assertTrue(playerTwo.isComputerPlayer());
	}
	
	@Test
	public void isPlayerOneFieldReturnsTrue() {
		// GIVEN
		
		// WHEN
		
		//THEN
		
	}

	private Player getPlayer(int reservedSpots) {
		Player player = new Player("Anna", false);
		player.setReservedSpots(reservedSpots);
		return player;
	}
}
