package hu.elte.thesis.minimax;

public class Tree {

	private Node root;
	private int level;
	
	Tree() {}
	
	public Node getRoot() { return this.root; }
	public void setRoot(Node root) { this.root = root; }
	
	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; }
}
