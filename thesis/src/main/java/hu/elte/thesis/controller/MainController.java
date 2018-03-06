package hu.elte.thesis.controller;

import java.util.List;

import hu.elte.thesis.model.Position;
import hu.elte.thesis.view.MainWindow;

public abstract class MainController {

	private MainWindow mainWindow;
	private int tableSize;
	
	public MainController() {
		
	}
	
	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public int getTableSize() {
		return this.tableSize;
	}

	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
		System.out.println("in main controller");
	}

	public boolean step1(int i, int j) {
		
		return false;
	}

	public List<Position> getCleavageFields() {
		
		return null;
	}

}
