package hu.elte.thesis.controller.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.model.RootChild;

/**
 * Algorithm for computer player with EASY game difficulty.
 * 
 * @author kelecs08
 */
public class RandomAlgorithm {

	private final TableBoardService tableBoardService;

	public RandomAlgorithm() {
		this.tableBoardService = new TableBoardService();
	}

	/**
	 * Returns a random valid position to where the computer player can step.
	 * @param tableBoardPositions
	 * 		the game's table board's positions
	 * @param player
	 * 		the actual player
	 * @return
	 * 		a RootChild object containing two positions - from where to where the computer steps
	 */
	public RootChild getRandomRootChild(Position[][] tableBoardPositions, Player player) {
		List<Position> rootPositions = tableBoardService.getPositionsForGivenPlayer(tableBoardPositions, player);
		
		List<RootChild> rootChildList = new ArrayList<>();
		for (int i = 0; i < rootPositions.size(); i++) {
			List<Position> firstLevelPositions = tableBoardService.getEmptyFirstLevelPositions(rootPositions.get(i), tableBoardPositions);
			for (int j = 0; j < firstLevelPositions.size(); j++) {
				rootChildList.add(new RootChild(rootPositions.get(i), firstLevelPositions.get(j), 1));
			}
			List<Position> secondLevePositions = tableBoardService.getEmptySecondLevelPositions(rootPositions.get(i), tableBoardPositions);
			for (int j = 0; j < secondLevePositions.size(); j++) {
				rootChildList.add(new RootChild(rootPositions.get(i), secondLevePositions.get(j), 0));
			}
		}
		Random random = new Random();
		return rootChildList.get(random.nextInt(rootChildList.size()));
	}

}
