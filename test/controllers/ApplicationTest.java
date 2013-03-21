package controllers;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.*;
import play.test.*;
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
    	Fixtures.loadModels("data/application.yml");
    	
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

        Response response = GET("/single/1");
        
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
    	Fixtures.loadModels("data/application.yml");
    	
    	GET("/single/1");
    	
    	Demotivator demotivator = (Demotivator)renderArgs("demotivator");
        assertNotNull(demotivator);
        assertEquals("A pretty demotivator", demotivator.getTitle());
        assertEquals("George Washington", demotivator.getAuthor().getDisplayName());
        assertEquals("five.jpg", demotivator.getFileName());
        
    }
    
}