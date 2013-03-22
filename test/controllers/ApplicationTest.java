package controllers;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.*;
import org.junit.Before;

import play.test.*;
import play.cache.Cache;
import play.jobs.Job;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {

	/**
	 * Going to index page. Sanity test. Verifying response content type and encoding.
	 */
    @Test
    public void testIndexPage() {
    	
        Response response = GET("/");
        
        assertNotNull(response);
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
        
    }
    
    /**
     * Verifying index controller is rendering ten latest ordered Demotivators.
     */
    @Test
    public void testIndexPageRenderingCorrectData() {
    	
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/index.yml");

    	GET("/");
    	
    	List<Demotivator> demotivators = (List<Demotivator>)renderArgs("demotivators");
        assertNotNull(demotivators);
        assertEquals(10, demotivators.size());
        assertEquals("A pretty demotivator", demotivators.get(0).getTitle());
        assertEquals("George Washington", demotivators.get(1).getAuthor().getDisplayName());
        assertEquals("five.jpg", demotivators.get(9).getFileName());
    }
    
  
    /**
	 * Going to single Demotivator page. Sanity test.
	 */
	@Test
	public void testSinglePage() {
		
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/single.yml");

		Demotivator demo = Demotivator.find("order by date desc").first();

		Response response = GET("/single/" + demo.id);

		assertNotNull(response);
		assertIsOk(response);
		assertContentType("text/html", response);
		assertCharset(play.Play.defaultWebEncoding, response);
	}
    
    /**
     * Verifying single controller is rendering Demotivator passed by id.
     */
    @Test
    public void testSinglePageRenderingCorrectData() {
    	
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/single.yml");
    	
    	Demotivator demo = Demotivator.find("order by date desc").first();
    	
    	GET("/single/" + demo.id);

    	Demotivator demotivator = (Demotivator)renderArgs("demotivator");
        assertNotNull(demotivator);
        assertEquals("An ugly demotivator", demotivator.getTitle());
        assertEquals("Frank Sinatra", demotivator.getAuthor().getDisplayName());
        assertEquals("ugly.jpg", demotivator.getFileName());
        
    }
}