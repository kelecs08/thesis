package hu.elte.thesis.model;

/**
 * Model class representing a possible step of the computer player.
 * 
 * @author kelecs08
 */
public class Tree {

	private Node root;
	private int level;

	public Tree() {}

	public Node getRoot() {
		return this.root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
