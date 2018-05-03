package hu.elte.thesis.view.dto;

/**
 * Data transfer object for {@link RootChildDto}.
 * 
 * @author kelecs08
 */
public class RootChildDto {

	private PositionDto root;
	private PositionDto child;
	
	public RootChildDto() {}

	public PositionDto getRoot() {
		return root;
	}

	public void setRoot(PositionDto root) {
		this.root = root;
	}

	public PositionDto getChild() {
		return child;
	}

	public void setChild(PositionDto child) {
		this.child = child;
	}
	
}
