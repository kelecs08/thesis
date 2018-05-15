package hu.elte.thesis.controller.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.model.Node;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.model.RootChild;
import hu.elte.thesis.model.Tree;

/**
 * Algorithm for computer player with MEDIUM and HARD game difficulty using Minimax algorithm as base logic.
 * 
 * @author kelecs08
 */
public class MinimaxAlgorithm {

	private List<Tree> trees;
	private Position[][] copy;
	private boolean firstRound;

	private final TableBoardService tableBoardService;

	public MinimaxAlgorithm() {
		this.tableBoardService = new TableBoardService();
	}

	/**
	 * Gets from which position to which field the computer player will step.
	 * @param tableboardPositions
	 * @param player
	 * @return {@link RootChild}
	 */
	public RootChild getBestStepAtEachRound(Position[][] tableboardPositions, Player player) {
		this.copy = copyArray(tableboardPositions);
		List<Position> rootPositions = tableBoardService.getPositionsForGivenPlayer(copy, player);
		
		this.trees = new ArrayList<>();
		for (Position p : rootPositions) {
			trees.add(constructTree(p, player));
		}
		return getBestRootChild();
	}

	private Position[][] copyArray(Position[][] tableBoardPositions) {
		Position[][] copy = new Position[tableBoardPositions.length][tableBoardPositions.length];
		for(int i = 0; i < tableBoardPositions.length; i++) {
			for(int j = 0; j < tableBoardPositions.length; j++) {
				copy[i][j] = tableBoardPositions[i][j];
			}
		}
		return copy;
	}

	private Tree constructTree(Position rootPosition, Player player) {
		Tree tree = new Tree();
		Node rootNode = new Node(rootPosition, true);
		tree.setRoot(rootNode);
		int bestScore = -9999;

		bestScore = getFirstLevelScoreAndSetPossibleFirstLevelChildren(player, rootNode, bestScore);
		bestScore = getSecondLevelScoreAndSetPossibleSecondLevelChildren(player, rootNode, bestScore);
		tree.getRoot().setScore(bestScore);
		return tree;
	}

	private int getFirstLevelScoreAndSetPossibleFirstLevelChildren(Player player, Node rootNode, int bestScore) {
		List<Position> possibleChildrenFirstLevel = tableBoardService.getFirstLevel(rootNode.getPosition(), copy);
		for (int i = 0; i < possibleChildrenFirstLevel.size(); i++) {
			if (possibleChildrenFirstLevel.get(i).getPlayer() == null) {
				int countOfPossibleOvertakes = evaluationFunction(possibleChildrenFirstLevel.get(i), 1, player, true);
				Node childNode = new Node(possibleChildrenFirstLevel.get(i), !player.isComputerPlayer());
				childNode.setScore(countOfPossibleOvertakes);
				rootNode.addChildNode(childNode);
				if (bestScore < countOfPossibleOvertakes) {
					bestScore = countOfPossibleOvertakes;
				}
			}
		}
		return bestScore;
	}

	private int getSecondLevelScoreAndSetPossibleSecondLevelChildren(Player player, Node rootNode, int bestScore) {
		List<Position> possibleChildrenSecondLevel = tableBoardService.getSecondLevel(rootNode.getPosition(), copy);
		for (int i = 0; i < possibleChildrenSecondLevel.size(); i++) {
			if (possibleChildrenSecondLevel.get(i).getPlayer() == null) {
				int countOfPossibleOvertakes = evaluationFunction(possibleChildrenSecondLevel.get(i), 0, player, true);
				Node childNode = new Node(possibleChildrenSecondLevel.get(i), !player.isComputerPlayer());
				childNode.setScore(countOfPossibleOvertakes);
				rootNode.addChildNode(childNode);
				if (bestScore < countOfPossibleOvertakes) {
					bestScore = countOfPossibleOvertakes;
				}
			}
		}
		return bestScore;
	}
	
	private RootChild getBestRootChild() {
		List<RootChild> bestRootChildren = new ArrayList<>();

		List<Tree> bestTrees = new ArrayList<>();
		if (trees.size() > 0) {
			int bestScore = trees.get(0).getRoot().getScore();
			for (Tree t : trees) {
				if (t.getRoot().getScore() > bestScore) {
					bestScore = t.getRoot().getScore();
					bestTrees = new ArrayList<>();
					bestTrees.add(t);
				} else if (t.getRoot().getScore() == bestScore) {
					bestTrees.add(t);
				}
			}

			for (Tree bt : bestTrees) {
				List<Node> nodeChildren = bt.getRoot().getChildrenNodes();
				for (Node c : nodeChildren) {
					if (c.getScore() == bestScore) {
						bestRootChildren.add(new RootChild(bt.getRoot().getPosition(), c.getPosition(), bestScore));
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

	/**
	 * Evaluates the given position according to how many fields it can overtake.
	 * @param position
	 * @param initial
	 * @param player
	 * @param isMaxPlayer
	 * @return int
	 */
	private int evaluationFunction(Position position, int initial, Player player, boolean isMaxPlayer) { // how many fields are overtaken if we step to this position?
		int countOfPossibleOvertakes = initial;
		for (int i = position.getRow() - 1; i <= position.getRow() + 1; i++) {
			for (int j = position.getColumn() - 1; j <= position.getColumn() + 1; j++) {
				Position actualPosition = this.copy[i][j];
				if (actualPosition.isValidSpace() && actualPosition != position && actualPosition.getPlayer() != null && !actualPosition.getPlayer().equals(player)) {
					if(isMaxPlayer)	countOfPossibleOvertakes += 10;
					else countOfPossibleOvertakes -=10;
				}
			}
		}
		return countOfPossibleOvertakes;
	}
	
	/**
	 * Gets from which position to which field the computer player will step using minimax algorithm as base.
	 * @param tableboardPositions
	 * @param playerOne
	 * @param playerTwo
	 * @param level
	 * @param lev
	 * @return
	 */
	public RootChild getStepUsingMinimax(Position[][] tableboardPositions, Player playerOne, Player playerTwo, int level, int lev) {
		this.copy = copyArray(tableboardPositions);
		this.firstRound = true;
		List<Position> rootPositions = tableBoardService.getPositionsForGivenPlayer(copy, playerTwo);
	
		this.trees = new ArrayList<>();
		for (Position p : rootPositions) {
			firstRound = true;
			trees.add(constTree(p, level, playerOne, playerTwo, lev));
		}
		return getBestRootChild();
	}
	
	/**
	 * Construct a tree of possible steps for a player's blob.
	 * @param rootPosition
	 * @param level
	 * @param playerOne
	 * @param playerTwo
	 * @param lev
	 * @return {@link Tree}
	 */
	private Tree constTree(Position rootPosition, int level, Player playerOne, Player playerTwo, int lev) {
		Tree tree = new Tree();
		Node rootNode = new Node(rootPosition, playerTwo.isComputerPlayer());
		tree.setRoot(rootNode);
		tree.setLevel(level);
		
		int bestScore = minimax(playerOne, playerTwo, rootNode, level, lev);
		rootNode.setScore(bestScore);
		tree.getRoot().setScore(bestScore);
		return tree;
	}
	
	/**
	 * Implementation of the minimax algorithm.
	 * @param playerOne
	 * @param playerTwo
	 * @param rootNode
	 * @param level
	 * @param lev
	 * @return int
	 */
	private int minimax(Player playerOne, Player playerTwo, Node rootNode, int level, int lev) {
		if(level == 0) return 0;
		
		if(rootNode.isMaxPlayer()) { 			//maximizing
			int bestScore = -9999;
			List<Position> rootPositions = new ArrayList<>();
			if(firstRound) {
				rootPositions.add(rootNode.getPosition());
				firstRound = false;
			} else {
				rootPositions = tableBoardService.getPositionsForGivenPlayer(copy, playerTwo);
			}
			for(Position rp: rootPositions) {
				List<Position> firstLevel = tableBoardService.getEmptyFirstLevelPositions(rp, copy);
				List<Position> secondLevel = tableBoardService.getEmptySecondLevelPositions(rp, copy);
				for(Position p: firstLevel) {
					copy[p.getRow()][p.getColumn()].setPlayer(playerTwo);
					Node newRoot = new Node(p, !rootNode.isMaxPlayer());
					int score = minimax(playerOne, playerTwo, newRoot, level-1, lev) + evaluationFunction(newRoot.getPosition(), 1, playerTwo, true);
					if(level == lev) {
						newRoot.setScore(score);
						rootNode.addChildNode(newRoot);
					}
					copy[p.getRow()][p.getColumn()].setPlayer(null);
					if(score > bestScore) bestScore = score;
				}
				for(Position p: secondLevel) {
					copy[p.getRow()][p.getColumn()].setPlayer(playerTwo);
					Node newRoot = new Node(p, !rootNode.isMaxPlayer());
					int score = minimax(playerOne, playerTwo, newRoot, level-1, lev) + evaluationFunction(newRoot.getPosition(), 0, playerTwo, true);
					if(level == lev) {
						newRoot.setScore(score);
						rootNode.addChildNode(newRoot);
					}
					copy[p.getRow()][p.getColumn()].setPlayer(null);
					if(score > bestScore) bestScore = score;
				}
			}
			return bestScore;
		} else { 								//minimizing
			int bestScore = 9999;
			List<Position> rootPositions = tableBoardService.getPositionsForGivenPlayer(copy, playerOne);
			for(Position rp: rootPositions) {
				List<Position> firstLevel = tableBoardService.getEmptyFirstLevelPositions(rp, copy);
				List<Position> secondLevel = tableBoardService.getEmptySecondLevelPositions(rp, copy);
				for(Position p: firstLevel) {
					copy[p.getRow()][p.getColumn()].setPlayer(playerOne);
					Node newRoot = new Node(p, !rootNode.isMaxPlayer());
					int score = minimax(playerOne, playerTwo, newRoot, level-1, lev) + evaluationFunction(newRoot.getPosition(), -1, playerOne, true);
					copy[p.getRow()][p.getColumn()].setPlayer(null);
					if(score < bestScore) bestScore = score;
				}
				for(Position p: secondLevel) {
					copy[p.getRow()][p.getColumn()].setPlayer(playerOne);
					Node newRoot = new Node(p, !rootNode.isMaxPlayer());
					int score = minimax(playerOne, playerTwo, newRoot, level-1, lev) + evaluationFunction(newRoot.getPosition(), 0, playerOne, true);
					copy[p.getRow()][p.getColumn()].setPlayer(null);
					if(score < bestScore) bestScore = score;
				}
			}
			return bestScore;
		}
	}
}
