package services;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import play.libs.Images;

import utils.ImageUtils;
import utils.ImageUtilsImpl;

/**
 * Demotivator creation service.
 * Produces a Demotivator image and a thumbnail.
 * @author vitaliikravets
 */
public class DemotivatorCreatorImpl implements DemotivatorCreator{
	private ImageUtils utils;
	
	public DemotivatorCreatorImpl(){
		this.utils = new ImageUtilsImpl();
	}

	public DemotivatorCreatorImpl(ImageUtils utils){
		this.utils = utils;
	}
	
	@Override
	public String createDemotivator(File imageFile, String title, String text, String mode) throws IOException {
		createImageFolderIfNeeded();
		
		long timestamp = System.currentTimeMillis();

		//Create demotivator
		String formatName = utils.getImageFormatName(imageFile);
		File outputFile = new File(IMAGE_DIR_PATH + File.separator + timestamp + "." + formatName);
		BufferedImage image = utils.readFile(imageFile);
		Dimension newSize = utils.getScaledSize(image, MAX_WIDTH, MAX_HEIGHT);
		
		Images.resize(imageFile, outputFile, newSize.width, newSize.height);
		
		image = utils.readFile(outputFile);
		
		//Creating a bigger image
		BufferedImage biggerImage = new BufferedImage(newSize.width + BORDER_SPACE, newSize.height + TEXT_AREA_SPACE, BufferedImage.TYPE_INT_RGB);
		image = utils.addBorderAndTextSpace(image, biggerImage, BORDER_SPACE, TEXT_AREA_SPACE);
        image = utils.drawTitleAndText(image, title, text, TEXT_AREA_SPACE);
        
        utils.writeImage(image, formatName, outputFile);
        
        //Create thumbnail
        File thumbfile = new File(IMAGE_DIR_PATH + File.separator + "thumb." + timestamp + "." + formatName);
        image = utils.readFile(outputFile);
        newSize = utils.getScaledSize(image, MAX_THUMB_WIDTH, MAX_THUMB_HEIGHT);
        Images.resize(outputFile, thumbfile, newSize.width, newSize.height);
        
        if("preview".equals(mode)){
        	boolean deleted = outputFile.delete();
        	if(!deleted){
            	throw new IOException("Big demo file not deleted!");
            }
        }
        
        return outputFile.getName();
	}
	
	private void createImageFolderIfNeeded() {

		File imageDir = new File(IMAGE_DIR_PATH);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
	}

}
