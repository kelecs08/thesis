package hu.elte.thesis.model;

/**
 * Enum representing the type of the game.
 * 
 * @author kelecs08
 */
public enum GameType {

	ONE_PLAYER("One player game"), 
	TWO_PLAYER("Two player game");
	
	private String typeString;
	
	private GameType(String typeString) {
		this.typeString = typeString;
	}

	public String getTypeString() {
		return typeString;
	}
	
}
