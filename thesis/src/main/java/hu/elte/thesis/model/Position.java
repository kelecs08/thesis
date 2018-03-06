package hu.elte.thesis.model;

public class Position {

	private int row;
	private int column;
	private Player player;
	private boolean validSpace;
	
	
	public Position(int row, int column, Player player, boolean valiSpace) {
		this.row = row;
		this.column = column;
		this.player = player;
		this.validSpace = valiSpace;
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

	public boolean isValidSpace() {
		return validSpace;
	}

	public void setValidSpace(boolean validSpace) {
		this.validSpace = validSpace;
	}
	
	public int getCoordinatesAdditionedValue() {
		return this.row + this.column;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + row;
		result = prime * result + (validSpace ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (column != other.column)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (row != other.row)
			return false;
		if (validSpace != other.validSpace)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [row=" + row + ", column=" + column + ", player=" + player + ", validSpace=" + validSpace
				+ "]";
	}
	
}
