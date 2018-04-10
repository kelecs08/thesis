package hu.elte.thesis.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.minimax.RootChild;
import hu.elte.thesis.model.Position;

public class RandomAlgorithm {

	private final TableBoardService tableBoardService;

	public RandomAlgorithm() {
		this.tableBoardService = new TableBoardService();
	}

	public RootChild getRandomRootChild(List<Position> rootPositions, Position[][] tableBoardPositions) {
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
