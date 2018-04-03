package hu.elte.thesis.view.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageResizingService {

	private static final int ICON_WIDTH = 20;
	private static final int ICON_HEIGHT = 20;
	
	public ImageIcon resizeImage(String imagePath, int width, int height, boolean flip) {
		BufferedImage originalImage;
		BufferedImage resizedImage = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try(InputStream inputStream = classLoader.getResourceAsStream(imagePath)) {
			originalImage = ImageIO.read(inputStream);
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			resizedImage = new BufferedImage(width, height, type);
			Graphics2D graphics = resizedImage.createGraphics();
			if(flip)
				graphics.drawImage(originalImage, 0+width, 0, -width, height, null);
			else
				graphics.drawImage(originalImage, 0, 0, width, height, null);
			graphics.dispose();
		} catch (IOException e) {
			System.out.println("IOException occured during the load of the icon image.");
		}
		return new ImageIcon(resizedImage);
	}
	
	public ImageIcon resizeImage(String imagePath, boolean flip) {
		return resizeImage(imagePath, ICON_WIDTH, ICON_HEIGHT, flip);
	}
}
