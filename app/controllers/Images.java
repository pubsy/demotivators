package controllers;

import java.io.File;

import play.mvc.Controller;
import services.DemotivatorCreator;

public class Images extends Controller{

	public static void image(String fileName) {
		String imgDirPath = DemotivatorCreator.IMAGE_DIR_PATH;
		File image = new File(imgDirPath + fileName);
	    renderBinary(image);
	}

}
