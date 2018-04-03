package hu.elte.thesis.minimax;

import hu.elte.thesis.model.Position;

public class RootChild {

	private Position rootPosition;
	private Position bestChildPosition;
	private int bestScore;
	
	public RootChild(Position root, Position bestChild, int bestScore) {
		this.rootPosition = root;
		this.bestChildPosition = bestChild;
		this.bestScore = bestScore;
	}
	
	public Position getRootPosition() {
		return rootPosition;
	}
	public void setRootPosition(Position rootPosition) {
		this.rootPosition = rootPosition;
	}
	public Position getBestChildPosition() {
		return bestChildPosition;
	}
	public void setBestChildPosition(Position bestChildPosition) {
		this.bestChildPosition = bestChildPosition;
	}

	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}
	
	
}
