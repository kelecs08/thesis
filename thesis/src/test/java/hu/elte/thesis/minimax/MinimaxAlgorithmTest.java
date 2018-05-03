package hu.elte.thesis.minimax;

import org.junit.Before;
import org.junit.Test;

import hu.elte.thesis.controller.algorithm.MinimaxAlgorithm;
import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;

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
	public void constructMinimaxTest() {
		underTest.getStepUsingMinimax(tableBoardPositions, playerOne, playerTwo, 3, 3);
	}
	
}
