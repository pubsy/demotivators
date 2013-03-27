package guice;

import play.Play;
import services.DemotivatorCreator;
import services.DemotivatorCreatorImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Google Guice module. 
 * Helps keep classes loosely coupled and with integration testing.
 * @author vitaliikravets
 */
public class GuiceConfig extends AbstractModule {
	@Override
    protected void configure() {
		
		Class clazz = DemotivatorCreatorImpl.class;
		
		if(Play.id.equals("test")){
			try {
				clazz = Class.forName("mocks.DemotivatorCreatorMock");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		bind(DemotivatorCreator.class).to(clazz).in(Singleton.class);
    }
}
