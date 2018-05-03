package hu.elte.thesis.controller.service;

import java.util.ArrayList;
import java.util.List;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;

/**
 * Service class for table board's positions.
 * 
 * @author kelecs08
 */
public class TableBoardService {

	/**
	 * Fills up the given tableBoardPositions with default values.
	 * @param tableSize
	 * @param tableBoardPositions
	 * @param playerOne
	 * @param playerTwo
	 */
	public void fillDefaultTableBoard(int tableSize, Position[][] tableBoardPositions, Player playerOne, Player playerTwo) {
		int additionalTableSize = tableSize + MainController.ADDITIONAL_FIELDS;
		for (int i = 0; i < 2; i++) { // first two row, column
			for (int j = 0; j < additionalTableSize; j++) {
				tableBoardPositions[i][j] = new Position(i, j, null, false);
				tableBoardPositions[j][i] = new Position(j, i, null, false);
			}
		}
		for (int i = additionalTableSize - 2; i < additionalTableSize; i++) { // last two row, column
			for (int j = 0; j < additionalTableSize; j++) {
				tableBoardPositions[i][j] = new Position(i, j, null, false);
				tableBoardPositions[j][i] = new Position(j, i, null, false);
			}
		}
		for (int i = 2; i < additionalTableSize - 2; i++) {
			for (int j = 2; j < additionalTableSize - 2; j++) {
				tableBoardPositions[i][j] = new Position(i, j, null, true); // table center
			}
		}
		// initial player fields
		tableBoardPositions[2][2] = new Position(2, 2, playerOne, true);
		tableBoardPositions[additionalTableSize - 3][2] = new Position(additionalTableSize - 3, 2, playerOne, true);
		tableBoardPositions[2][additionalTableSize - 3] = new Position(2, additionalTableSize - 3, playerTwo, true);
		tableBoardPositions[additionalTableSize - 3][additionalTableSize - 3] = new Position(additionalTableSize - 3, additionalTableSize - 3, playerTwo, true);
	}

	/**
	 * Returns the first level positions of the given position.
	 * @param position
	 * @param tableBoardPositions
	 * @return list of {@link Position}
	 */
	public List<Position> getFirstLevel(Position position, Position[][] tableBoardPositions) {
		List<Position> firstLevelPositions = new ArrayList<>();
		int row = position.getRow();
		int column = position.getColumn();
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				Position p = tableBoardPositions[i][j];
				if (p.isValidSpace() && p != position) {
					firstLevelPositions.add(p);
				}
			}
		}
		return firstLevelPositions;
	}

	/**
	 * Returns the empty first level positions of the given position.
	 * @param position
	 * @param tableBoardPositions
	 * @return list of {@link Position}
	 */
	public List<Position> getEmptyFirstLevelPositions(Position position, Position[][] tableBoardPositions) {
		List<Position> firstLevelPositions = new ArrayList<>();
		int row = position.getRow();
		int column = position.getColumn();
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				Position p = tableBoardPositions[i][j];
				if (p.isValidSpace() && p != position && p.getPlayer() == null) {
					firstLevelPositions.add(p);
				}
			}
		}
		return firstLevelPositions;
	}

	/**
	 * Returns the second level positions of the given position.
	 * @param position
	 * @param tableBoardPositions
	 * @return list of {@link Position}
	 */
	public List<Position> getSecondLevel(Position position, Position[][] tableBoardPositions) {
		List<Position> firstLevelPositions = getFirstLevel(position, tableBoardPositions);
		List<Position> secondLevelPositions = new ArrayList<>();
		int row = position.getRow();
		int column = position.getColumn();
		for (int i = row - 2; i <= row + 2; i++) {
			for (int j = column - 2; j <= column + 2; j++) {
				Position p = tableBoardPositions[i][j];
				if (p.isValidSpace() && p != position) {
					secondLevelPositions.add(p);
				}
			}
		}
		secondLevelPositions.removeAll(firstLevelPositions);
		return secondLevelPositions;
	}

	/**
	 * Returns the empty second level positions of the given position.
	 * @param position
	 * @param tableBoardPositions
	 * @return list of {@link Position}
	 */
	public List<Position> getEmptySecondLevelPositions(Position position, Position[][] tableBoardPositions) {
		List<Position> firstLevelPositions = getEmptyFirstLevelPositions(position, tableBoardPositions);
		List<Position> secondLevelPositions = new ArrayList<>();
		int row = position.getRow();
		int column = position.getColumn();
		for (int i = row - 2; i <= row + 2; i++) {
			for (int j = column - 2; j <= column + 2; j++) {
				Position p = tableBoardPositions[i][j];
				if (p.isValidSpace() && p != position && p.getPlayer() == null) {
					secondLevelPositions.add(p);
				}
			}
		}
		secondLevelPositions.removeAll(firstLevelPositions);
		return secondLevelPositions;
	}

	/**
	 * Checks whether steps are available for the given positions.
	 * @param positions
	 * @param tableBoardPositions
	 * @return boolean
	 */
	public boolean checkStepsAvailableForAllPlayerField(List<Position> positions, Position[][] tableBoardPositions) {
		for (Position p : positions) {
			if (checkStepAvailable(p, tableBoardPositions)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether steps are available for the given position.
	 * @param position
	 * @param tableBoardPositions
	 * @return boolean
	 */
	public boolean checkStepAvailable(Position position, Position[][] tableBoardPositions) {
		List<Position> firstLevel = getFirstLevel(position, tableBoardPositions);
		List<Position> secondLevel = getSecondLevel(position, tableBoardPositions);
		for (Position p1 : firstLevel) {
			if (p1.isValidSpace() && p1.getPlayer() == null) {
				return true;
			}
		}
		for (Position p2 : secondLevel) {
			if (p2.isValidSpace() && p2.getPlayer() == null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the positions in which the given player has blobs.
	 * @param tableBoardPositions
	 * @param player
	 * @return list of {@link Position}
	 */
	public List<Position> getPositionsForGivenPlayer(Position[][] tableBoardPositions, Player player) {
		List<Position> rootPositions = new ArrayList<>();
		for (int i = 2; i < tableBoardPositions.length - 2; i++) {
			for (int j = 2; j < tableBoardPositions.length - 2; j++) {
				Player tmp = tableBoardPositions[i][j].getPlayer();
				if (tmp != null && tmp.equals(player)) {
					rootPositions.add(tableBoardPositions[i][j]);
				}
			}
		}
		return rootPositions;
	}
	
	/**
	 * Checks whether the given child is a first level position of the given root.
	 * @param tableBoardPositions
	 * @param root
	 * @param child
	 * @return
	 */
	public boolean isFirstLevelPositionOfTheGivenPosition(Position[][] tableBoardPositions, Position root, Position child) {
		List<Position> firstLevel = getEmptyFirstLevelPositions(root, tableBoardPositions);
		if(firstLevel.contains(child)) return true;
		return false;
	}
	
	/**
	 * Checks whether the given child is a second level position of the given root.
	 * @param tableBoardPositions
	 * @param root
	 * @param child
	 * @return
	 */
	public boolean isSecondLevelPositionOfTheGivenPosition(Position[][] tableBoardPositions, Position root, Position child) {
		List<Position> secondLevel = getEmptySecondLevelPositions(root, tableBoardPositions);
		if(secondLevel.contains(child)) return true;
		return false;
	}
}
