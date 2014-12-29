package controllers;

import java.util.HashMap;
import java.util.Map;

import models.Comment;
import models.Demotivator;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class CommentsTest extends AutenticatedControllerTest {
	
	@Before
	public void deleteModels() {
		Fixtures.deleteAllModels();
	}
	
    @Test
    public void testAddCommentRequiresAuthentication(){
    	String url = "/comments/save/1";
    	
    	Response response = POST(url);
        
        assertNotNull(response);
        assertStatus(403, response);
    }
	
    @Test
    public void testAddComment() {
    	
    	Fixtures.loadModels("data/single.yml");
    	authenticate();

    	long count = Comment.count();
    	
    	Demotivator demo = Demotivator.find("byFileName", "ugly.jpg").first();
    	
		String url = "/comments/save/" + demo.getId();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("text", "A very new test comment text.");
		
		Response response = POST(url, parameters);
        
        assertNotNull(response);
        assertIsOk(response);
        
        assertEquals(count + 1, Comment.count());
    }
    
    @Test
    public void testWrongDemoId() {
    	
    	Fixtures.loadModels("data/single.yml");
    	authenticate();

    	long randomWrongId;
    	Demotivator demo; 
    	do{
    		randomWrongId = (long) (Math.random() * 1000);
    		demo = Demotivator.findById(randomWrongId);
    	} while (demo != null);
    	
    	
		String url = "/comments/save/" + randomWrongId;
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("text", "A very new test comment text.");
		
		Response response = POST(url, parameters);
        
        assertNotNull(response);
        assertStatus(404, response);
    }
    
    @Test
    public void testEmptyText() {
    	
    	Fixtures.loadModels("data/single.yml");
    	authenticate();

    	Demotivator demo = Demotivator.find("byFileName", "ugly.jpg").first();
    	
		String url = "/comments/save/" + demo.getId();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("text", "");
		
		Response response = POST(url, parameters);
        
		assertNotNull(response);
        assertStatus(400, response);
        assertContentEquals("Comment text can not be empty", response);
    }
    
    @Test
    public void testNoCommentText() {
    	
    	Fixtures.loadModels("data/single.yml");
    	authenticate();

    	Demotivator demo = Demotivator.find("byFileName", "ugly.jpg").first();
    	
		String url = "/comments/save/" + demo.getId();
		Map<String, String> parameters = new HashMap<String, String>();
		
		Response response = POST(url, parameters);
        
		assertNotNull(response);
        assertStatus(400, response);
        assertContentEquals("Comment text can not be empty", response);
    }
}
