package utils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageUtils {
	
	public BufferedImage drawTitleAndText(BufferedImage image, String title, String text, int textAreaSpace);
	public BufferedImage addBorderAndTextSpace(BufferedImage bufImage, BufferedImage biggerImage, int borderSpace, int textAreaSpace);
	public Dimension getScaledSize(BufferedImage image, int max_width, int max_height) throws IOException;
	public String getImageFormatName(File image) throws IOException;
	public BufferedImage readFile(File imageFile) throws IOException;
	public void writeImage(BufferedImage image, String formatName, File outputfile) throws IOException;

}
