package hu.elte.thesis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import hu.elte.thesis.controller.MainControllerInterface;
import hu.elte.thesis.view.MainWindow;

/**
 * Test class for {@link Main}.
 * 
 * @author kelecs08
 *
public class MainTest {
	
	@Mock private MainWindow mainWindow;
	@Mock private MainControllerInterface mainController;
	
	@InjectMocks private static Main underTest = new Main();
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void testMain() {
		// GIVEN
		
		// WHEN
		underTest.main(null);
		// THEN
		Mockito.verify(mainWindow).createCustomMenuBar();
		//Mockito.verify(mainController).setMainWindow(mainWindow);
	}
}
*/
