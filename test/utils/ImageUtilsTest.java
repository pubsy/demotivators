package utils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.mchange.util.AssertException;

import play.test.UnitTest;

public class ImageUtilsTest extends UnitTest{
	
	private ImageUtils unit = new ImageUtilsImpl();

	@Test
	public void testGettingScaledImageSizeForHorizontalImage() throws IOException{

		BufferedImage image = mock(BufferedImage.class);
		Image scaledImage = mock(Image.class);
		
		when(image.getWidth()).thenReturn(1000);
		when(image.getHeight()).thenReturn(500);
		when(image.getScaledInstance(710, 355, Image.SCALE_SMOOTH)).thenReturn(scaledImage);
		
		Dimension size = unit.getScaledSize(image, 710, 570);
		assertNotNull(size);
		assertEquals(710, size.width);
		assertEquals(355, size.height);
		
	}
	
	@Test
	public void testGettingScaledImageSizeForVerticalImage() throws IOException{

		BufferedImage image = mock(BufferedImage.class);
		Image scaledImage = mock(Image.class);
		
		when(image.getWidth()).thenReturn(500);
		when(image.getHeight()).thenReturn(1000);
		when(image.getScaledInstance(285, 570, Image.SCALE_SMOOTH)).thenReturn(scaledImage);
		
		Dimension size = unit.getScaledSize(image, 710, 570);
		assertNotNull(size);
		assertEquals(285, size.width);
		assertEquals(570, size.height);
		
	}
	
	@Test
	public void testAddBorderAndTextSpace(){
		BufferedImage image = mock(BufferedImage.class);
		BufferedImage biggerImage = mock(BufferedImage.class);
		Graphics2D graphics2D = mock(Graphics2D.class);
			
		
		when(image.getWidth()).thenReturn(710);
		when(image.getHeight()).thenReturn(355);
		when(biggerImage.createGraphics()).thenReturn(graphics2D);
		
		BufferedImage result = unit.addBorderAndTextSpace(image, biggerImage, 50, 130);
		
		assertNotNull(result);
		
		verify(graphics2D).fill(new Rectangle2D.Double(0, 0, 760, 485));
		verify(graphics2D).drawRect(15, 15, 731, 376);
		verify(graphics2D).drawImage(image, 25, 25, 710, 355, null);
	}
	
	@Test
	public void testDrawTitleAndText(){
		BufferedImage image = mock(BufferedImage.class);
		Graphics2D graphics2D = mock(Graphics2D.class); 
		FontMetrics metrics = mock(FontMetrics.class);
		Font titleFont = new Font("Dante", 1, 30);
		Font textFont = new Font("Dante", 1, 20);
		
		when(image.getWidth()).thenReturn(760);
		when(image.getHeight()).thenReturn(485);
		when(image.createGraphics()).thenReturn(graphics2D);
		when(graphics2D.getFontMetrics(titleFont)).thenReturn(metrics);
		when(graphics2D.getFontMetrics(textFont)).thenReturn(metrics);
		when(metrics.stringWidth("Some title")).thenReturn(50);
		when(metrics.stringWidth("Some text")).thenReturn(30);
		
		BufferedImage result = unit.drawTitleAndText(image, "Some title", "Some text", 130);
		
		assertNotNull(result);
		
		verify(graphics2D).drawString("Some title", 355, 435);
		verify(graphics2D).drawString("Some text", 365, 465);
	}
	
	@Test
	public void testGetImageFormatName() throws IOException{
		String result = unit.getImageFormatName(new File("test/data/image.jpg"));

		assertNotNull(result);
		assertEquals("JPEG", result);
	}
	
	@Test
	public void testGetImageFormatNameForABadImage(){
		try{
			unit.getImageFormatName(new File("test/data/bad.file"));
			fail("should never come here");
		}catch(IOException ex){}

	}
}