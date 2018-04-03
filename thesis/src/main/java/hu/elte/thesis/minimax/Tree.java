package hu.elte.thesis.minimax;

import java.util.ArrayList;
import java.util.List;

public class Tree {

	private Node root;
	private List<Node> bestChildren;
	private int bestScore;
	private int level;
	
	public Tree() {
		this.bestChildren = new ArrayList<>();
	}
	
	public Node getRoot() { return this.root; }
	public void setRoot(Node root) { this.root = root; }
	
	public List<Node> getBestChildren() { return this.bestChildren; }
	public void addBestChild(Node bestChild) { this.bestChildren.add(bestChild); }
	public void emptyBestChildrenList() { this.bestChildren = new ArrayList<>(); }
	
	public int getBestScore() {	return bestScore; }
	public void setBestScore(int bestScore) { this.bestScore = bestScore; }

	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; }

	@Override
	public String toString() {
		return "Tree [root=" + root + ", bestChildren=" + bestChildren + ", bestScore=" + bestScore + ", level=" + level
				+ "]\n";
	}
	
	
}
