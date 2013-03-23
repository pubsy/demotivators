package model;

import java.util.List;

import models.Demotivator;
import models.User;
import net.sf.oval.guard.Post;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class DemotivatorTest extends UnitTest{
	
	@Before
	public void deleteModels(){
		Fixtures.deleteAllModels();
	}

	@Test
	public void createDemotivator() {
	    // Create a new user and save it
	    User martin = new User("martin@fowler.com", "secret", "Martin").save();
	    
	    // Create a new demotivator
	    new Demotivator("title", "file.name", martin).save();
	    
	    // Test that the demotivator has been created
	    assertEquals(1, Demotivator.count());
	    
	    // Retrieve all demotivators created by bob
	    List<Demotivator> martinDemos = Demotivator.find("byAuthor", martin).fetch();
	    
	    // Tests
	    assertEquals(1, martinDemos.size());
	    Demotivator demo = martinDemos.get(0);
	    assertNotNull(demo);
	    assertEquals(martin, demo.getAuthor());
	    assertEquals("title", demo.getTitle());
	    assertEquals("file.name", demo.getFileName());
	    assertNotNull(demo.getDate());
	}
}
