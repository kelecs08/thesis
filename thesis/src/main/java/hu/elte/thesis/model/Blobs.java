package hu.elte.thesis.model;

public enum Blobs {
	
	BLUE("blue"),
	BROWN("brown"),
	DARK_BLUE("darkBlue"),
	DARK_GREEN("darkGreen"),
	GRAY("gray"),
	GREEN("green"),
	ORANGE("orange"),
	PURPLE("purple"),
	RED("red"),
	YELLOW("yellow");
	
	private String blobColorString; 
	
	Blobs(String blobColorString) { this.blobColorString = blobColorString; }
	
	public String getBlobColorString() { return blobColorString; }
	
	
}
