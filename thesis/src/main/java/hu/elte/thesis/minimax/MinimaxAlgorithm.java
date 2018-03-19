package hu.elte.thesis.minimax;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.model.Position;

public class MinimaxAlgorithm {

	private List<Tree> trees;
	private Position[][] tableBoardPositions;
	
	private TableBoardService tableBoardService = new TableBoardService();
	
	public MinimaxAlgorithm() {}
	
	public Tree getPositionToBeStepped(List<Position> rootPositions, int level, Position[][] tableboardPositions) {
		this.trees = new ArrayList<>();
		this.tableBoardPositions = tableboardPositions;
		for(Position p: rootPositions) trees.add(constructTree(p, level));
		return getBestTree();
	}

	private Tree constructTree(Position rootPosition, int level) {
		Tree tree = new Tree();
		Node rootNode = new Node(rootPosition, true);
		tree.setRoot(rootNode);
		tree.setLevel(level);
		int bestScore = 0;
		
		List<Position> possibleChildrenFirstLevel = tableBoardService.getFirstLevel(rootPosition, tableBoardPositions);
		for(int i = 0; i < possibleChildrenFirstLevel.size(); i++) {
			if(possibleChildrenFirstLevel.get(i).getPlayer() == null) {
				int countOfPossibleOvertakes = evaluationFunction(possibleChildrenFirstLevel.get(i), 1);
				Node childNode = new Node(possibleChildrenFirstLevel.get(i), false);
				childNode.setScore(countOfPossibleOvertakes);
				rootNode.addChildNode(childNode);
				if(bestScore < countOfPossibleOvertakes) bestScore = countOfPossibleOvertakes;
			}
		}
		List<Position> possibleChildrenSecondLevel = tableBoardService.getSecondLevel(rootPosition, tableBoardPositions);
		for(int i = 0; i < possibleChildrenSecondLevel.size(); i++) {
			if(possibleChildrenSecondLevel.get(i).getPlayer() == null) {
				int countOfPossibleOvertakes = evaluationFunction(possibleChildrenSecondLevel.get(i), 0);
				Node childNode = new Node(possibleChildrenSecondLevel.get(i), false);
				childNode.setScore(countOfPossibleOvertakes);
				rootNode.addChildNode(childNode);
				if(bestScore < countOfPossibleOvertakes) bestScore = countOfPossibleOvertakes;
			}
		}
		tree.setBestScore(bestScore);
		System.out.println(tree);
		return tree;
	}

	private Tree getBestTree() {
		List<Tree> bestTrees = new ArrayList<>();
		Node bestChild = null;
		for(Tree t: trees) {
			Node rootNode = t.getRoot();
			List<Node> childrenNodes = rootNode.getChildrenNodes();
			if(bestChild == null && childrenNodes.size() > 0) {
				bestChild = childrenNodes.get(0);
				t.addBestChild(bestChild);
			//	System.out.println("Initial best child: " + bestChild);
			}
			if(bestChild != null) {
				for(int i = 0; i < childrenNodes.size(); i++) {
				//	System.out.println("Children nodes: " + childrenNodes.get(i).toString());
					if(childrenNodes.get(i).getScore() > bestChild.getScore()) {
						t.emptyBestChildrenList();
						bestChild = childrenNodes.get(i);
						t.addBestChild(bestChild);
					//	System.out.println("\n Best child: " + bestChild);
					} else if(childrenNodes.get(i).getScore() == bestChild.getScore()) {
						t.addBestChild(childrenNodes.get(i));
					//	System.out.println("\n  Added : " + childrenNodes.get(i));
					}
				}
				bestTrees.add(t);
			//	System.out.println(t.toString());
			}
		}
		Tree bestTree = null;
		if(bestTrees.size() > 0) bestTree = bestTrees.get(0);
		for(int i = 0; i < bestTrees.size(); i++) {
			if(bestTree.getBestChildren().get(0).getScore() < bestTrees.get(i).getBestChildren().get(0).getScore()) {
				bestTree = bestTrees.get(i);
			//	System.out.println(bestTree.toString());
			}
		}
		return bestTree;
	/*
		Tree bestTree = trees.get(0);
		for(int i = 0; i < trees.size(); i++) {
			if(bestTree.getBestScore() < trees.get(i).getBestScore())
				bestTree = trees.get(i);
		}
		return bestTree;*/
	}		

	private int evaluationFunction(Position position, int initial) { //how many fields are overtaken if we step to this position?
		int countOfPossibleOvertakes = initial;
		for(int i = position.getRow()-1; i <= position.getRow()+1; i++) {
			for(int j = position.getColumn()-1; j <= position.getColumn()+1; j++) {
				Position actualPosition = this.tableBoardPositions[i][j];
				if(actualPosition.isValidSpace() && actualPosition != position && actualPosition.getPlayer() != null && !actualPosition.getPlayer().isComputerPlayer())
					countOfPossibleOvertakes += 10;	
			}
		}
		return countOfPossibleOvertakes;
	}
	
}
