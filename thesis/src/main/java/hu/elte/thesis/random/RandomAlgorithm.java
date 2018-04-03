package hu.elte.thesis.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.minimax.RootChild;
import hu.elte.thesis.model.Position;

public class RandomAlgorithm {

	private MainController mainController;
	private TableBoardService tableBoardService;
	
	private List<RootChild> rootChildList;
	
	public RandomAlgorithm(MainController mainController) {
		this.mainController = mainController;
		this.tableBoardService = new TableBoardService();
		this.rootChildList = new ArrayList<>();
	}
	
	public RootChild getRandomRootChild(List<Position> rootPositions, Position[][] tableBoardPositions) {
		for(int i = 0; i < rootPositions.size(); i++) {
			List<Position> firstLevelPositions = tableBoardService.getFirstLevel(rootPositions.get(i), tableBoardPositions);
			for(int j = 0; j < firstLevelPositions.size(); j++) 
				rootChildList.add(new RootChild(rootPositions.get(i), firstLevelPositions.get(i), 1));
			List<Position> secondLevePositions = tableBoardService.getSecondLevel(rootPositions.get(i), tableBoardPositions);
			for(int j = 0; j < secondLevePositions.size(); j++) 
				rootChildList.add(new RootChild(rootPositions.get(i), secondLevePositions.get(i), 0));
		}
		Random random = new Random();		
		return rootChildList.get(random.nextInt(rootChildList.size()));
	}
	
	
}
