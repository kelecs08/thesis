package hu.elte.thesis.model;

/**
 * Model class representing a player of the game.
 * 
 * @author kelecs08
 */
public class Player {

	private String name;
	private int reservedSpots = 2;
	private boolean computerPlayer;
	
	public Player() {}
	
	public Player(String name, boolean computerPlayer) {
		this.name = name;
		this.computerPlayer = computerPlayer;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getReservedSpots() {
		return reservedSpots;
	}
	
	public void setReservedSpots(int reservedSpots) {
		this.reservedSpots = reservedSpots;
	}
	
	public void modifyReservedSpotsNumber(int number) {
		this.reservedSpots += number;
	}
	
	public boolean isComputerPlayer( ) {
		return this.computerPlayer;
	}
	
	public void setComputerPlayer(boolean computerPlayer) {
		this.computerPlayer = computerPlayer;
	}

	@Override
	public int hashCode() {
		return reservedSpots + name.length();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(obj.getClass().equals(this.getClass())) {
			Player object = (Player) obj;
			if(object.getName().equals(this.name) && object.getReservedSpots() == this.getReservedSpots()) return true;
		}
		return false;
	}
	
}
