package hu.elte.thesis.minimax;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.model.Position;

public class Node {

	private Position position;
	private int score;
	private boolean maxPlayer;
	private List<Node> childrenNodes;
	
	public Node(Position position, boolean maxPlayer) {
		this.position = position;
		this.maxPlayer = maxPlayer;
		this.childrenNodes = new ArrayList<>();
	}

	public Position getPosition() { return this.position; }
	public void setPosition(Position position) { this.position = position; }
	
	public int getScore() {	return score; }
	public void setScore(int score) { this.score = score; }

	public boolean isMaxPlayer() { return maxPlayer; }
	public void setMaxPlayer(boolean maxPlayer) { this.maxPlayer = maxPlayer; }

	public List<Node> getChildrenNodes() { return childrenNodes; }
	public void addChildNode(Node childNode) { this.childrenNodes.add(childNode); }

	@Override
	public String toString() {
		return "Node [position=" + position + ", score=" + score + ", maxPlayer=" + maxPlayer + ", childrenNodes="
				+ childrenNodes + "]";
	}
	
	
	
	
}
