package hu.elte.thesis;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.controller.MainControllerInterface;
import hu.elte.thesis.view.MainWindow;

/**
 * Entry point of the application.
 * 
 * @author kelecs08
 */
public class App {

    public static void main( String[] args ) {

    	MainWindow mainWindow = new MainWindow();
    	MainControllerInterface mainController = new MainController();
    	
    	mainWindow.setMainController(mainController);
    	mainController.setMainWindow(mainWindow);

    	mainWindow.createCustomMenuBar();
    	
    	mainWindow.createDefaultTableBoard();
    	mainController.fillDefaultTableBoard();
    }
}
