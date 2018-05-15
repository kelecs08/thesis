package hu.elte.thesis.minimax;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hu.elte.thesis.controller.algorithm.RandomAlgorithm;
import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.model.RootChild;

/**
 * Test class for {@link RandomAlgorithm}.
 * 
 * @author kelecs08
 */
public class RandomAlgorithmTest {

	private static RandomAlgorithm underTest;
	
	@BeforeAll
	static void setUp() {
		underTest = new RandomAlgorithm();
	}
	
	@Test
	void testRandomAlgorithm() {
		// GIVEN
		TableBoardService tableBoardService = new TableBoardService();
		Position[][] tableBoardPositions = new Position[8][8];
		Player playerOne = new Player("Player1", false);
		Player playerTwo = new Player("Player2", false);
		tableBoardService.fillDefaultTableBoard(4, tableBoardPositions, playerOne, playerTwo);
		// WHEN
		RootChild result = underTest.getRandomRootChild(tableBoardPositions, playerTwo);
		// THEN
		Assertions.assertNotNull(result);
	}
	
	
}
