package hu.elte.thesis.controller.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hu.elte.thesis.controller.service.TableBoardService;
import hu.elte.thesis.model.Position;

public class TableBoardServiceTest {

	private final TableBoardService underTest = new TableBoardService();
	private Position[][] tableBoardPositions;

	@Before
	public void setup() {
		tableBoardPositions = new Position[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				tableBoardPositions[i][j] = new Position(i, j, null, true);
			}
		}
	}

	@Test
	public void getFirstLevelWithMiddlePosition() {
		// GIVEN
		// WHEN
		List<Position> actualFirstLevel = underTest.getFirstLevel(tableBoardPositions[2][2], tableBoardPositions);
		// THEN
		assertEquals(8, actualFirstLevel.size());
	}

	@Test
	public void getSecondLevelWithMiddlePosition() {
		// GIVEN
		// WHEN
		List<Position> actualSecondLevel = underTest.getSecondLevel(tableBoardPositions[2][2], tableBoardPositions);
		// THEN
		assertEquals(16, actualSecondLevel.size());
	}

	@Test
	public void checkStepAvailableTrue() {
		// GIVEN
		// WHEN
		boolean stepAvailable = underTest.checkStepAvailable(tableBoardPositions[2][2], tableBoardPositions);
		// THEN
		assertTrue(stepAvailable);
	}

	@Test
	public void checkStepsAreAvailableTrue() {
		// GIVEN
		List<Position> positions = new ArrayList<>();
		positions.add(tableBoardPositions[2][2]);
		// WHEN
		boolean stepAvailable = underTest.checkStepsAvailableForAllPlayerField(positions, tableBoardPositions);
		// THEN
		assertTrue(stepAvailable);
	}
}
