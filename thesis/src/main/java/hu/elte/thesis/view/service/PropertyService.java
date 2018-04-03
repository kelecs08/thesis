package hu.elte.thesis.view.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
	
	public Properties loadMainWindowSettingProperties(String size) {
		Properties mainWindowSettingProperties = new Properties();
		try(InputStream inputStream = classLoader.getResourceAsStream(propertyFiles.getProperty(size))) {
			mainWindowSettingProperties.load(inputStream);
		} catch (IOException e) { System.out.println("IOException occured during the read of a property file."); }
		return mainWindowSettingProperties;
	}

	public Properties loadIconProperties() {
		Properties iconProperties = new Properties();
		try(InputStream inputStream = classLoader.getResourceAsStream(propertyFiles.getProperty("icons"))) {
			iconProperties.load(inputStream);
		} catch (IOException e) { System.out.println("IOException occured during the read of a property file."); }
		return iconProperties;
	}
	
	public Properties loadPictureProperties() {
		Properties pictureProperties = new Properties();
		try(InputStream inputStream = classLoader.getResourceAsStream(propertyFiles.getProperty("pictures"))) {
			pictureProperties.load(inputStream);
		} catch (IOException e) { System.out.println("IOException occured during the read of a property file."); }
		return pictureProperties;
	}
}
