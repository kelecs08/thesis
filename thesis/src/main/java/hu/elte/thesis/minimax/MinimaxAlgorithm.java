package hu.elte.thesis.minimax;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.model.Position;

public class MinimaxAlgorithm {

	private Tree tree;
	private Position[][] tableBoardPositions;
	
	public MinimaxAlgorithm() {}
	
	public Node getPositionToBeStepped(Position rootPosition, int level, Position[][] tableboardPositions) {
		this.tableBoardPositions = tableboardPositions;
		Node rootNode = constructTree(rootPosition, level);
		return getBestChildNode(rootNode);
	}

	private Node constructTree(Position rootPosition, int level) {
		tree = new Tree();
		Node rootNode = new Node(rootPosition, true);
		tree.setRoot(rootNode);
		tree.setLevel(level);
		
		List<Position> possibleChildren = getFirstLevelPositions(rootPosition);
		for(int i = 0; i < possibleChildren.size(); i++ ) {
			int countOfPossibleOvertakes = evaluationFunction(possibleChildren.get(i));
			Node childNode = new Node(possibleChildren.get(i), false);
			childNode.setScore(countOfPossibleOvertakes);
			System.out.println("\nChildnode: " + childNode.toString());
			rootNode.addChildNode(childNode);
		}
		return rootNode;
	}

	private Node getBestChildNode(Node rootNode) {
		List<Node> childrenNodes = rootNode.getChildrenNodes();
		Node bestChild = childrenNodes.get(0);
		for(int i = 1; i < childrenNodes.size(); i++) {
			if(childrenNodes.get(i).getScore() > bestChild.getScore()) {
				bestChild = childrenNodes.get(i);
			}
		}
		return bestChild;
	}		

	private int evaluationFunction(Position position) { //how many fields are overtaken if we step to this position?
		int countOfPossibleOvertakes = 1;
		System.out.println("\tEvaluation: " + position.toString() + " - " + countOfPossibleOvertakes);
		for(int i = position.getRow()-1; i <= position.getRow()+1; i++) {
			for(int j = position.getColumn()-1; j <= position.getColumn()+1; j++) {
				Position actualPosition = this.tableBoardPositions[i][j];
				if(actualPosition.isValidSpace() && actualPosition != position && actualPosition.getPlayer() != null && !actualPosition.getPlayer().isComputerPlayer())
					countOfPossibleOvertakes += 10;	
				System.out.println("act pos: " + actualPosition.toString() + " - " + countOfPossibleOvertakes);
			}
		}
		return countOfPossibleOvertakes;
	}
	
	private List<Position> getFirstLevelPositions(Position position) {  //where can we step?
		List<Position> possibleChildren = new ArrayList<>();
		for(int i = position.getRow()-1; i <= position.getRow()+1; i++) {
			for(int j = position.getColumn()-1; j <= position.getColumn()+1; j++) {
				Position actualPosition = this.tableBoardPositions[i][j];
				if(actualPosition.isValidSpace() && actualPosition != position && actualPosition.getPlayer() == null)
					possibleChildren.add(actualPosition);	
			}
		}
		return possibleChildren;
	}
	
}
