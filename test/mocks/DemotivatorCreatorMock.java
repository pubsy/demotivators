package mocks;

import java.io.File;
import java.io.IOException;

import services.DemotivatorCreator;

public class DemotivatorCreatorMock implements DemotivatorCreator{

	@Override
	public String createDemotivator(File file, String title, String text) throws IOException {
		return "test.file.name";
	}

}
