package hu.elte.thesis.view.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Service class for the projects' property files  stored in src/main/resources/properties directory.
 * 
 * @author kelecs08
 */
public class PropertyService {
	
	private Properties propertyFiles;
	private ClassLoader classLoader;
	
	public PropertyService() {
		classLoader = Thread.currentThread().getContextClassLoader();
		loadPropertyFilesProperties();
	}
	
	private void loadPropertyFilesProperties() {
		propertyFiles = new Properties();
		try(InputStream inputStream = classLoader.getResourceAsStream("properties/property_files.properties")) {
			propertyFiles.load(inputStream);
		} catch (IOException e) { System.out.println("IOException occured during the read of a property file."); }
	}
	
	/**
	 * Loads MainWindow class' property file depending on the size of the game.
	 * @param size
	 * 		the size of the table board
	 * @return the appropriate property file
	 */
	public Properties loadMainWindowSettingProperties(String size) {
		Properties mainWindowSettingProperties = new Properties();
		try(InputStream inputStream = classLoader.getResourceAsStream(propertyFiles.getProperty(size))) {
			mainWindowSettingProperties.load(inputStream);
		} catch (IOException e) { System.out.println("IOException occured during the read of a property file."); }
		return mainWindowSettingProperties;
	}

	/**
	 * Loads the property file associated with the icons.
	 * @return icons' property file, which contains the images' path
	 */
	public Properties loadIconProperties() {
		Properties iconProperties = new Properties();
		try(InputStream inputStream = classLoader.getResourceAsStream(propertyFiles.getProperty("icons"))) {
			iconProperties.load(inputStream);
		} catch (IOException e) { System.out.println("IOException occured during the read of a property file."); }
		return iconProperties;
	}
	
	/**
	 * Loads the property file associated with texts.
	 * @return texts' property file, which contains the neccessary texts for the application
	 */
	public Properties loadTextProperties() {
		Properties textsProperties = new Properties();
		try(InputStream inputStream = classLoader.getResourceAsStream(propertyFiles.getProperty("texts"))) {
			textsProperties.load(inputStream);
		} catch (IOException e) { System.out.println("IOException occured during the read of a property file."); }
		return textsProperties;		
	}
}
