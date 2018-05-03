package hu.elte.thesis.model;

/**
 * Enum representing the difficulty of the game.
 * 
 * @author kelecs08
 */
public enum ComputerPlayerDifficulty {

	EASY("easy"),
	MEDIUM("medium"),
	HARD("hard");
	
	private String difficultyString;
	
	private ComputerPlayerDifficulty(String difficultyString) {
		this.difficultyString = difficultyString;
	}

	public String getDifficultyString() {
		return difficultyString;
	}
	
}
