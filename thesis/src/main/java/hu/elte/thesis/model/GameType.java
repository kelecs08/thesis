package hu.elte.thesis.model;

public enum GameType {

	ONE_PLAYER("ONE PLAYER"), TWO_PLAYER("TWO PLAYER");
	
	private String title;
	
	GameType(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
}
