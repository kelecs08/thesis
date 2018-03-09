package hu.elte.thesis.controller;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.model.GameType;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.view.dto.SimplePosition;


public class TwoPlayerController extends MainController {
	
	private Position clickedPosition;
	
	public TwoPlayerController() {
		super();
		this.gameType = GameType.TWO_PLAYER;
	}
	
	
	
	
}
