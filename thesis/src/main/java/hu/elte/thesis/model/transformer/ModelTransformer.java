package hu.elte.thesis.model.transformer;

import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.model.RootChild;
import hu.elte.thesis.view.dto.PlayerDto;
import hu.elte.thesis.view.dto.PositionDto;
import hu.elte.thesis.view.dto.RootChildDto;

/**
 * Transformer class for model layer.
 * 
 * @author kelecs08
 */
public class ModelTransformer {

	public PlayerDto transformPlayerToPlayerDto(Player player) {
		if(player == null) {
			return null;
		}
		PlayerDto playerDto = new PlayerDto();
		playerDto.setName(player.getName());
		return playerDto;
	}

	public PositionDto transformPositionToPositionDto(Position position) {
		PositionDto positionDto = new PositionDto();
		positionDto.setRow(position.getRow());
		positionDto.setColumn(position.getColumn());
		return positionDto;
	}
	
	public RootChildDto transformRootChildToRootChildDto(RootChild rootChild) {
		RootChildDto rootChildDto = new RootChildDto();
		rootChildDto.setRoot(transformPositionToPositionDto(rootChild.getRootPosition()));
		rootChildDto.setChild(transformPositionToPositionDto(rootChild.getBestChildPosition()));
		return rootChildDto;
	}
	
}
