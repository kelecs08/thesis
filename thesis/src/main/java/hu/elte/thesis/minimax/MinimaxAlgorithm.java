package hu.elte.thesis.minimax;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.model.Position;

public class MinimaxAlgorithm {

	private List<Tree> trees;
	private Position[][] tableBoardPositions;

	private final TableBoardService tableBoardService;

	public MinimaxAlgorithm() {
		this.tableBoardService = new TableBoardService();
	}

	public RootChild getRootAndPositionToBeStepped(List<Position> rootPositions, int level, Position[][] tableboardPositions) {
		this.trees = new ArrayList<>();
		this.tableBoardPositions = tableboardPositions;
		for (Position p : rootPositions) {
			trees.add(constructTree(p, level));
		}
		return getBestRootChild();
	}

	private Tree constructTree(Position rootPosition, int level) {
		Tree tree = new Tree();
		Node rootNode = new Node(rootPosition, true);
		tree.setRoot(rootNode);
		tree.setLevel(level);
		int bestScore = -9999;

		List<Position> possibleChildrenFirstLevel = tableBoardService.getFirstLevel(rootPosition, tableBoardPositions);
		for (int i = 0; i < possibleChildrenFirstLevel.size(); i++) {
			if (possibleChildrenFirstLevel.get(i)
				.getPlayer() == null) {
				int countOfPossibleOvertakes = evaluationFunction(possibleChildrenFirstLevel.get(i), 1);
				Node childNode = new Node(possibleChildrenFirstLevel.get(i), false);
				childNode.setScore(countOfPossibleOvertakes);
				rootNode.addChildNode(childNode);
				if (bestScore < countOfPossibleOvertakes) {
					bestScore = countOfPossibleOvertakes;
				}
			}
		}
		List<Position> possibleChildrenSecondLevel = tableBoardService.getSecondLevel(rootPosition, tableBoardPositions);
		for (int i = 0; i < possibleChildrenSecondLevel.size(); i++) {
			if (possibleChildrenSecondLevel.get(i)
				.getPlayer() == null) {
				int countOfPossibleOvertakes = evaluationFunction(possibleChildrenSecondLevel.get(i), 0);
				Node childNode = new Node(possibleChildrenSecondLevel.get(i), false);
				childNode.setScore(countOfPossibleOvertakes);
				rootNode.addChildNode(childNode);
				if (bestScore < countOfPossibleOvertakes) {
					bestScore = countOfPossibleOvertakes;
				}
			}
		}
		tree.getRoot()
			.setScore(bestScore);
		return tree;
	}

	private RootChild getBestRootChild() {
		List<RootChild> bestRootChildren = new ArrayList<>();

		List<Tree> bestTrees = new ArrayList<>();
		if (trees.size() > 0) {
			int bestScore = trees.get(0)
				.getRoot()
				.getScore();
			for (Tree t : trees) {
				if (t.getRoot()
					.getScore() > bestScore) {
					bestScore = t.getRoot()
						.getScore();
					bestTrees = new ArrayList<>();
					bestTrees.add(t);
				} else if (t.getRoot()
					.getScore() == bestScore) {
					bestTrees.add(t);
				}
			}

			for (Tree bt : bestTrees) {
				List<Node> nodeChildren = bt.getRoot()
					.getChildrenNodes();
				for (Node c : nodeChildren) {
					if (c.getScore() == bestScore) {
						bestRootChildren.add(new RootChild(bt.getRoot()
							.getPosition(), c.getPosition(), bestScore));
						System.out.println("ROOT: " + bt.getRoot()
							.getPosition() + ", CHILD: " + c.getPosition() + ", SCORE: " + bestScore);
					}
				}
			}

			if (bestRootChildren.size() > 0) {
				Random random = new Random();
				return bestRootChildren.get(random.nextInt(bestRootChildren.size()));
			}
		}
		return null;
	}

	private int evaluationFunction(Position position, int initial) { // how many fields are overtaken if we step to this position?
		int countOfPossibleOvertakes = initial;
		for (int i = position.getRow() - 1; i <= position.getRow() + 1; i++) {
			for (int j = position.getColumn() - 1; j <= position.getColumn() + 1; j++) {
				Position actualPosition = this.tableBoardPositions[i][j];
				if (actualPosition.isValidSpace() && actualPosition != position && actualPosition.getPlayer() != null && !actualPosition.getPlayer()
					.isComputerPlayer()) {
					countOfPossibleOvertakes += 10;
				}
			}
		}
		return countOfPossibleOvertakes;
	}

}
