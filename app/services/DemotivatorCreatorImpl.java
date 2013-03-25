package services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.ImageUtils;
import utils.ImageUtilsImpl;

public class DemotivatorCreatorImpl implements DemotivatorCreator{
	private ImageUtils utils;
	
	public DemotivatorCreatorImpl(){
		this.utils = new ImageUtilsImpl();
	}

	public DemotivatorCreatorImpl(ImageUtils utils){
		this.utils = utils;
	}
	
	@Override
	public String createDemotivator(File imageFile, String title, String text) throws IOException {
		createImageFolderIfNeeded();
		
		long timestamp = System.currentTimeMillis();

		//Create demotivator
		BufferedImage image; 
		image = utils.readFile(imageFile);
		image = utils.scale(image, MAX_WIDTH, MAX_HEIGHT);
		image = utils.addBorderAndTextSpace(image);
        image = utils.drawTitleAndText(image, title, text);
        
        String formatName = utils.getImageFormatName(imageFile);
        File outputfile = new File(IMAGE_DIR_PATH + File.separator + timestamp + "." + formatName);
        utils.writeImage(image, formatName, outputfile);
        
        //Create thumbnail
        BufferedImage thumb = utils.scale(image, MAX_THUMB_WIDTH, MAX_THUMB_HEIGHT);
        
        File thumbfile = new File(IMAGE_DIR_PATH + File.separator + "thumb." + timestamp + "." + formatName);
        utils.writeImage(thumb, formatName, thumbfile);
        
        return outputfile.getName();
	}
	
	private void createImageFolderIfNeeded() {

		File imageDir = new File(IMAGE_DIR_PATH);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
	}

}
