package utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import play.libs.Images;
import services.DemotivatorCreator;

/**
 * Image transformation utility class.
 * Doing all the dirty of creating a Demotivator image.
 * @author vitaliikravets
 */
public class ImageUtilsImpl implements ImageUtils{
	
	@Override
	public BufferedImage drawTitleAndText(BufferedImage image, String title, String text, int textAreaSpace) {
		Graphics2D graphics2D = image.createGraphics();
		int height = image.getHeight();
		int width = image.getWidth();
		
		Font font = calculateTextFont(graphics2D, 30, width - 50, title);
		graphics2D.setFont(font);

		int wdh = getTextWidth(graphics2D, font, title);
		int start = (width - wdh) / 2;
		
		int verticalGap = 80;
		//write title
		graphics2D.drawString(title, start, height - textAreaSpace + 80);
		
		
		//------------------
		verticalGap += 30;

		font = calculateTextFont(graphics2D, 20, width - 50, text);
		graphics2D.setFont(font);
			
		wdh = getTextWidth(graphics2D,font, text);
		start = (width - wdh) / 2;
			
		//write text
		graphics2D.drawString(text, start, height - textAreaSpace + verticalGap);

		return image;
	}

	@Override
	public BufferedImage addBorderAndTextSpace(BufferedImage image, BufferedImage biggerImage, int borderSpace, int textAreaSpace) {
		int scaledHeight = image.getHeight();
		int scaledWidth = image.getWidth();

		Graphics2D graphics2D = biggerImage.createGraphics();
		// Use of BILNEAR filtering to enable smooth scaling
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// Setting black background
		graphics2D.setPaint(Color.BLACK);
		//Filling bigger image with black background
		graphics2D.fill(new Rectangle2D.Double(0, 0, scaledWidth + borderSpace, scaledHeight + textAreaSpace));
		
		//Setting border outline color and thickness
		graphics2D.setPaint(Color.WHITE);
		graphics2D.setStroke(new BasicStroke(3.0f));
		
		int borderGap = borderSpace / 2 - 10;
		//Drawing border ouline
		graphics2D.drawRect(borderGap, borderGap, scaledWidth + borderGap + 6, scaledHeight + borderGap + 6);
		
		// Drawing original image over bigger image
		graphics2D.drawImage(image, borderSpace / 2, borderSpace / 2, scaledWidth, scaledHeight, null);
		return biggerImage;
	}

	@Override
	public Dimension getScaledSize(BufferedImage image, int max_width, int max_height) throws IOException {
		
		int height = image.getHeight();
		int width = image.getWidth();

		if (height >= width) {
			width = width * max_height / height ;
			height = max_height;
		} else {
			height = height * max_width / width ;
			width = max_width;
		}
		
		return new Dimension(width, height);
	}
	
	@Override
	public String getImageFormatName(File image) throws IOException{
		String format = null;
		
		ImageInputStream iis = ImageIO.createImageInputStream(image);

		Iterator<ImageReader> i = ImageIO.getImageReaders(iis);
		while (i.hasNext() ) {
			ImageReader reader = i.next();
			format = reader.getFormatName();
		}
		
		iis.close();
		
		if(format == null){
			throw new IOException("Bad image");
		}
		
		return format;
	}
	
	@Override
	public BufferedImage readFile(File imageFile) throws IOException {
		BufferedImage image = ImageIO.read(imageFile);
		
		if(image == null){
			throw new IOException();
		}
		return image;
	}
	
	@Override
	public void writeImage(BufferedImage image, String formatName, File outputfile) throws IOException {
		ImageIO.write(image, formatName, outputfile);
	}
	
	private Font calculateTextFont(Graphics2D g, int maxFontSize, int width, String text){
		Font font = new Font("Dante", 1, maxFontSize);
		
		int textWidth = getTextWidth(g, font, text);
		
		while(textWidth >= width){
			maxFontSize -= 1;
			font = new Font("Dante", 1, maxFontSize);
			textWidth = getTextWidth(g, font, text);
		}
		
		return font;
	}
	
	private int getTextWidth(Graphics2D g, Font font, String text){
		FontMetrics metrics = g.getFontMetrics(font);
		return metrics.stringWidth(text);
	}
}
