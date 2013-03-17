package services;

import java.io.File;
import java.io.IOException;

public interface DemotivatorCreator {
	
	final int MAX_WIDTH = 710;
	final int MAX_HEIGHT = 550;
	
	final int MAX_THUMB_WIDTH = 150;
	final int MAX_THUMB_HEIGHT = 150;
	
	String IMAGE_DIR_PATH = "public" + File.separator + "images" + File.separator +  "demotivators";

	String createDemotivator(File file, String title, String text) throws IOException;

}
