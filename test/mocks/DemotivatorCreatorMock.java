package mocks;

import java.io.File;
import java.io.IOException;

import services.DemotivatorCreator;

public class DemotivatorCreatorMock implements DemotivatorCreator{

	@Override
	public String createDemotivator(File file, String title, String text, String mode) throws IOException {
		if(file.getName().equals("bad.file")){
			throw new IOException("Bad file");
		}
		return "test.file.name";
	}

}
