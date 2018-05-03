package hu.elte.thesis.view.dto;

import hu.elte.thesis.model.Player;

/**
 * Data transfer object for {@link Player}.
 * 
 * @author kelecs08
 */
public class PlayerDto {

	private String name;
	
	public PlayerDto() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
