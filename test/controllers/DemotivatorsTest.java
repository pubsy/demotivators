package controllers;

import models.Demotivator;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;

public class DemotivatorsTest extends AutenticatedControllerTest {
	
	@Before
	public void deleteModels() {
		Fixtures.deleteAllModels();
	}

    
    @Test
    public void testRedirectAfterDeletion(){
    	Fixtures.loadModels("data/two_demos.yml");
    	
    	//Authenticating
    	authenticate("martin.fowler@gmail.com", "123");
    	
    	Demotivator first = Demotivator.find("order by date desc").first();
    	assertFalse(first.isDeleted());
    	
    	Response response = GET("/delete/" + first.id);
    	
    	first.refresh();
    	assertTrue(first.isDeleted());

    	assertEquals("/next/" + first.id, response.getHeader("Location"));
    }
    
    @Test
    public void testDeletionIsImposibleIfNotAuthenticated(){
    	Fixtures.loadModels("data/two_demos.yml");
    	
    	Demotivator first = Demotivator.find("order by date desc").first();
    	
    	Response response = GET("/delete/" + first.id);
    	
    	assertStatus(403, response);
    }
    
    @Test
    public void testDeletionIsImposibleIfNotOwner(){
    	Fixtures.loadModels("data/two_demos.yml");
    	
    	//Authenticating with different user
    	authenticate("frank.sinatra@gmail.com", "123");
    	
    	Demotivator first = Demotivator.find("order by date desc").first();
    	
    	Response response = GET("/delete/" + first.id);
    	
    	assertStatus(403, response);
    }
    
    @Test
    public void testDeletedDemotivatorIsNoLongerAvailable(){
    	Fixtures.loadModels("data/two_demos.yml");
    	
    	//Authenticating
    	authenticate("martin.fowler@gmail.com", "123");
    	
    	Demotivator demo = Demotivator.find("order by date desc").first();

    	Response response = GET("/single/" + demo.id);
    	assertStatus(200, response);//Page is available
    	
    	GET("/delete/" + demo.id);

    	response = GET("/single/" + demo.id);
    	assertStatus(302, response);//Page is NOT anymore available
    }
}
