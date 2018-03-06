package hu.elte.thesis.model;

public class Player {

	private String name;
	private int reservedSpots = 2;
	private boolean actualPlayer;
	
	public Player() {}
	public Player(String name, boolean actualPlayer) {
		this.name = name;
		this.actualPlayer = actualPlayer;
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

	public void modifyReservedSpotsNumber(int number) {
		this.reservedSpots += number;
	}
	
	public boolean isActualPlayer() {
		return actualPlayer;
	}
	
	public void setActualPlayer(boolean actualPlayer) {
		this.actualPlayer = actualPlayer;
	}
	
	@Override
	public String toString() {
		return "Player [name=" + name + ", reservedSpots=" + reservedSpots + "]";
	}
	
	
}
