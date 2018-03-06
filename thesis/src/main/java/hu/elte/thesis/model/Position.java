package hu.elte.thesis.model;

public class Position {

	private int row;
	private int column;
	private Player player;
	
	
	public Position(int row, int column, Player player) {
		this.row = row;
		this.column = column;
		this.player = player;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "Position [row=" + row + ", column=" + column + ", player=" + player + "]";
	}
	
}
