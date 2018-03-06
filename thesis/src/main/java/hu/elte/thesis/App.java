package hu.elte.thesis;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.controller.TwoPlayerController;
import hu.elte.thesis.view.MainWindow;

public class App {
	
    public static void main( String[] args ) {

    	MainWindow mainWindow = new MainWindow();
    	MainController mainController = new TwoPlayerController();
    	
    	mainWindow.setMainController(mainController);
    	mainController.setMainWindow(mainWindow);
    	
    	mainWindow.createCustomMenuBar();
    	mainWindow.createDefaultTableBoard();

    }
}
