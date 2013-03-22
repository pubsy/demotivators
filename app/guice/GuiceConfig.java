package guice;

import org.junit.Test;

import play.Play;
import play.modules.guice.GuiceSupport;
import services.DemotivatorCreator;
import services.DemotivatorCreatorImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

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
