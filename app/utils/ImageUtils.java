package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageUtils {
	
	public BufferedImage drawTitleAndText(BufferedImage image, String title, String text);
	public BufferedImage addBorderAndTextSpace(BufferedImage bufImage);
	public BufferedImage scale(BufferedImage bufImage, int max_width, int max_height);
	public String getImageFormatName(File image) throws IOException;
	public BufferedImage readFile(File imageFile) throws IOException;
	public void writeImage(BufferedImage image, String formatName, File outputfile) throws IOException;

}
