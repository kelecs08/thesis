package hu.elte.thesis.model.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.model.RootChild;
import hu.elte.thesis.view.dto.PlayerDto;
import hu.elte.thesis.view.dto.PositionDto;
import hu.elte.thesis.view.dto.RootChildDto;

/**
 * Test class for {@link ModelTransformer}.
 * 
 * @author kelecs08
 */
public class ModelTransformerTest {
	
	private static ModelTransformer underTest;
	
	@BeforeAll
	static void setUp() {
		underTest = new ModelTransformer();
	}

	@Test
	void testTransformPlayerToPlayerDtoReturnsPlayerDto() {
		// GIVEN
		Player player = new Player();
		player.setName("Anna");
		// WHEN
		PlayerDto result = underTest.transformPlayerToPlayerDto(player);
		// THEN
		Assertions.assertEquals("Anna", result.getName());
	}
	
	@Test
	void testTransformPlayerToPlayerDtoReturnsNull() {
		// GIVEN
		// WHEN
		PlayerDto result = underTest.transformPlayerToPlayerDto(null);
		// THEN
		Assertions.assertEquals(null, result);
	}
	
	@Test
	void testTransformPositionToPositionDto() {
		// GIVEN
		Position position = new Position(5, 5, null, true);
		// WHEN
		PositionDto result = underTest.transformPositionToPositionDto(position);
		// THEN
		Assertions.assertAll("result",
				() -> assertEquals(5, result.getRow()),
				() -> assertEquals(5, result.getColumn())
		);
	}
	
	@Test
	void testTransformRootChildToRootChildDto() {
		// GIVEN
		Position root = new Position(10, 11, null, true);
		Position child = new Position(9, 10, null, true);
		RootChild rootChild = new RootChild(root, child, 0);
		// WHEN
		RootChildDto result = underTest.transformRootChildToRootChildDto(rootChild);
		// THEN
		Assertions.assertAll("result",
				() -> assertEquals(10, result.getRoot().getRow()),
				() -> assertEquals(11, result.getRoot().getColumn()),
				() -> assertEquals(9, result.getChild().getRow()),
				() -> assertEquals(10, result.getChild().getColumn())
		);
	}
}
