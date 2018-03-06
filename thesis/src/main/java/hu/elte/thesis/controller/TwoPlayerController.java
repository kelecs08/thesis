package hu.elte.thesis.controller;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.model.Position;


public class TwoPlayerController extends MainController {
	
	private Position clickedPosition;
	
	public TwoPlayerController() {}
	


/*	public void savePlayers(String player1, String player2) {
		this.player1 = new Player("Player1", true);
		if(!player1.isEmpty()) this.player1.setName(player1);
		this.player2 = new Player("Player2", false);
		if(!player2.isEmpty()) this.player2.setName(player2);
	}*/
	
	

	public boolean isClickedPosition() {
		return this.clickedPosition != null;
	}
	
	
	public List<Position> getCleavageFields() {
		List<Position> positions = new ArrayList<>();
		for(int i = this.clickedPosition.getRow() - 1; i <= this.clickedPosition.getRow() + 1; i++) {
			for(int j = this.clickedPosition.getColumn() - 1; j <= this.clickedPosition.getColumn() + 1; j++) {
				//not edge field
				if(i > 1 && i < this.tableSize - 2 && j > 1 && j < this.tableSize - 2) {
					//not the clickedPOsition
					if(i != this.clickedPosition.getRow() && j != this.clickedPosition.getColumn()) {
						//can be addded to list
						positions.add(this.tableBoardPositions[i][j]);
						System.out.println(this.tableBoardPositions[i][j].toString());
					}
				}
			}
		}
		return positions;
	}

/*	@Override
	public boolean step1(int i, int j) {
		if(this.tableBoardPositions[i][j].getPlayer() != null && this.clickedPosition == null)
			this.clickedPosition = this.tableBoardPositions[i][j];
		return this.clickedPosition != null;
	}*/
	
/*	public Player step2(int i, int j) {
		if(this.actualPlayer == Player.M) return stepM(i, j);
		else return stepB(i, j);
	}*/
	
}
