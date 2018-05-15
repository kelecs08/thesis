package hu.elte.thesis.minimax;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.elte.thesis.controller.algorithm.MinimaxAlgorithm;
import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.model.RootChild;

/**
 * Test class for {@link MinimaxAlgorithm}.
 * 
 * @author kelecs08
 */
public class MinimaxAlgorithmTest {
	
	private MinimaxAlgorithm underTest;
	
	private TableBoardService tableBoardService;
	private Position[][] tableBoardPositions;
	private Player playerOne, playerTwo;
	
	@Before
	public void setup() {
		playerOne = new Player("PlayerOne", false);
		playerTwo = new Player("PlayerTwo", true);
		
		tableBoardService = new TableBoardService();
		underTest = new MinimaxAlgorithm();
		
		tableBoardPositions = new Position[12][12];
		tableBoardService.fillDefaultTableBoard(8, tableBoardPositions, playerOne, playerTwo);
	}
	
	@Test
	public void testgetStepUsingMinimax() {
		// GIVEN
		// WHEN
		RootChild result = underTest.getStepUsingMinimax(tableBoardPositions, playerOne, playerTwo, 3, 3);
		// THEN
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testGetBestStepAtEachRound() {
		// GIVEN
		// WHEN
		RootChild result = underTest.getBestStepAtEachRound(tableBoardPositions, playerOne);
		// THEN
		Assert.assertNotNull(result);
	}
	
}
