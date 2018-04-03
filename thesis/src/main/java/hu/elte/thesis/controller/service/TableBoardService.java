package hu.elte.thesis.controller.service;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;

public class TableBoardService {

	public void fillDefaultTableBoard(int tableSize, Position[][] tableBoardPositions, Player playerOne, Player playerTwo) {
		int additionalTableSize = tableSize + MainController.ADDITIONAL_FIELDS;
		for(int i = 0; i < 2; i++) { 												//first two row, column
			for(int j = 0; j < additionalTableSize; j++) {
				tableBoardPositions[i][j] = new Position(i, j, null, false);
				tableBoardPositions[j][i] = new Position(j, i, null, false);
			}
		}
		for(int i = additionalTableSize-2; i < additionalTableSize; i++) { 			//last two row, column
			for(int j = 0; j < additionalTableSize; j++) {
				tableBoardPositions[i][j] = new Position(i, j, null, false);
				tableBoardPositions[j][i] = new Position(j, i, null, false);
			}
		}		
		for(int i = 2; i < additionalTableSize - 2; i++) {
			for(int j = 2; j < additionalTableSize - 2; j++) {
				tableBoardPositions[i][j] = new Position(i, j, null, true); 		//table center
			}
		}
																					//initial player fields
		tableBoardPositions[2][2] = new Position(2, 2, playerOne, true);
		tableBoardPositions[additionalTableSize-3][2] = new Position(additionalTableSize-3, 2, playerOne, true);
		tableBoardPositions[2][additionalTableSize-3] = new Position(2, additionalTableSize-3, playerTwo, true);
		tableBoardPositions[additionalTableSize-3][additionalTableSize-3] = new Position(additionalTableSize-3, additionalTableSize-3, playerTwo, true);
	}
	
	public List<Position> getFirstLevel(Position position, Position[][] tableBoardPositions) {
		List<Position> firstLevelPositions = new ArrayList<>();
		int row = position.getRow();
		int column = position.getColumn();
		for(int i = row-1; i <= row+1; i++) {
			for(int j = column-1; j <= column+1; j++) {
				Position p = tableBoardPositions[i][j];
				if(p.isValidSpace() && p != position) {
					firstLevelPositions.add(p);
				}
			}
		}
		return firstLevelPositions;
	}
	
	public List<Position> getSecondLevel(Position position, Position[][] tableBoardPositions) {
		List<Position> firstLevelPositions = getFirstLevel(position, tableBoardPositions);
		List<Position> secondLevelPositions = new ArrayList<>();
		int row = position.getRow();
		int column = position.getColumn();
		for(int i = row-2; i <= row+2; i++) {
			for(int j = column-2; j <= column+2; j++) {
				Position p = tableBoardPositions[i][j];
				if(p.isValidSpace() && p != position) secondLevelPositions.add(p);
			}
		}
		secondLevelPositions.removeAll(firstLevelPositions);
		return secondLevelPositions;
	}
	
	public boolean checkStepsAvailableForAllPlayerField(List<Position> positions, Position[][] tableBoardPositions) {
		for(Position p : positions) {
			if(checkStepsAvailable(p, tableBoardPositions)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkStepsAvailable(Position position, Position[][] tableBoardPositions) {
		List<Position> firstLevel = getFirstLevel(position, tableBoardPositions);
		List<Position> secondLevel = getSecondLevel(position, tableBoardPositions);
		for(Position p1: firstLevel)
			if(p1.isValidSpace() && p1.getPlayer() == null) return true;
		for(Position p2: secondLevel)
			if(p2.isValidSpace() && p2.getPlayer() == null)	return true;
		return false;
	}
	
	public void tableBoardPositionsToString(Position[][] tableBoardPositions, MainController mainController) {
		for(int i = 0; i < mainController.getTableSize()+4; i++) {
			for(int  j = 0;j < mainController.getTableSize()+4; j++) {
				System.out.println(mainController.getTableBoardPositions()[i][j].toString());
			}
		}
	}
}
