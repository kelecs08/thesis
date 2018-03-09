package hu.elte.thesis.model;

public class Player {

	private String name;
	private int reservedSpots = 2;
	private boolean computerPlayer;
	
	public Player() {}
	
	public Player(String name, boolean computerPlayer) {
		this.name = name;
		this.computerPlayer = computerPlayer;
	}

	public String getName() { return this.name;	}
	public void setName(String name) { this.name = name; }
	
	public int getReservedSpots() {	return reservedSpots; }
	public void modifyReservedSpotsNumber(int number) {	this.reservedSpots += number; }
	
	public boolean isComputerPlayer( ) { return this.computerPlayer; }
	public void setComputerPlayer(boolean computerPlayer) { this.computerPlayer = computerPlayer; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (computerPlayer ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + reservedSpots;
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
		Player other = (Player) obj;
		if (computerPlayer != other.computerPlayer)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reservedSpots != other.reservedSpots)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", reservedSpots=" + reservedSpots + ", computerPlayer=" + computerPlayer + "]";
	}
	
}
