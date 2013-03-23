package services;

import java.io.File;
import java.io.IOException;

import play.Play;

public interface DemotivatorCreator {
	
	final int MAX_WIDTH = 710;
	final int MAX_HEIGHT = 550;
	
	final int MAX_THUMB_WIDTH = 150;
	final int MAX_THUMB_HEIGHT = 150;
	
	final String IMAGE_DIR_PATH = Play.configuration.getProperty("image.dir.path");

	String createDemotivator(File file, String title, String text) throws IOException;

}
