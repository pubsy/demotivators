package services;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.mockito.Mockito;

import play.libs.Mail.Mock;
import play.test.UnitTest;
import utils.ImageUtils;

import static org.mockito.Mockito.*;

public class DemotivatorCreatorTest extends UnitTest{

	@Test
	public void createDEmotivatorTest() throws IOException{
		
		ImageUtils utils = mock(ImageUtils.class);
		DemotivatorCreator creator = new DemotivatorCreatorImpl(utils);
		
		File imageFile = new File("test/data/image.jpg");
		String title = "Title";
		String text = "text";
		
		BufferedImage image = null;
		BufferedImage biggerImage = null;
		when(utils.readFile(imageFile)).thenReturn(image);
		when(utils.getScaledSize(biggerImage, 710, 570)).thenReturn(new Dimension(710, 355));
		when(utils.getScaledSize(image, 300, 300)).thenReturn(new Dimension(150, 110));
		when(utils.addBorderAndTextSpace(image, biggerImage, 50, 130)).thenReturn(image);
		when(utils.drawTitleAndText(image, title, text, 130)).thenReturn(image);
		when(utils.getImageFormatName(imageFile)).thenReturn("JPG");
		
		String result = null;
		try {	
			result = creator.createDemotivator(imageFile, title, text, "create", "testdomain");
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue(result.endsWith(".JPG"));
		
		verify(utils, atLeastOnce()).readFile(any(File.class));
		verify(utils, times(1)).getScaledSize(any(BufferedImage.class), eq(710), eq(570));
		verify(utils, times(1)).getScaledSize(any(BufferedImage.class), eq(300), eq(300));
		verify(utils, times(1)).addBorderAndTextSpace(any(BufferedImage.class), any(BufferedImage.class), eq(50), eq(130));
		verify(utils, times(1)).drawTitleAndText(any(BufferedImage.class), eq("Title"), eq("text"), anyInt());
		verify(utils, times(1)).addDomainSignature(any(BufferedImage.class), eq("testdomain"));
		verify(utils, atLeastOnce()).getImageFormatName(imageFile);
	}
}
