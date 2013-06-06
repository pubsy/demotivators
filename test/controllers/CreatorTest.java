package controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Demotivator;

import org.junit.Test;

import play.mvc.Http.Cookie;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class CreatorTest extends FunctionalTest{
	
    private static final String THIRTY_CHARS_TITLE = "012345678901234567890123456789";
    private static final String EIGHTY_CHARS_TEXT = "01234567890123456789012345678901234567890123456789012345678901234567890123456789";
    
	/**
     * Add Demotivator page controller test. Check redirect to login page.
     */
    @Test
    public void testAddDemotivatorPageRequiresAuthentication(){
    	Response response = GET("/add");
        
        assertNotNull(response);
        assertStatus(302, response);
        
        assertHeaderEquals("Location", "/secure/login", response);
    }
    
    /**
     * Check add Demotivator page accessible if Authenticated 
     */
    @Test
    public void testAddDemotivatorPageAccessibleIFAuthorised(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
		//Authenticating
    	authenticate();

        //Going to add Demotivator page
    	Response response = GET("/add");
        
        assertNotNull(response);
        assertStatus(200, response);
    }
    
	/**
	 * Check Demotivator created. The title and text are max in size.
	 * @throws IOException
	 */
    @Test
    public void testDemotivatorCreateSuccess() throws IOException{
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", THIRTY_CHARS_TITLE);
    	createDemoParams.put("text", EIGHTY_CHARS_TEXT);
    	createDemoParams.put("mode", "create");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(200, response);
    	assertContentType("application/json; charset=utf-8", response);
    	
    	List<Demotivator> demos = Demotivator.findAll();
    	assertEquals(1, demos.size());
    	assertEquals(THIRTY_CHARS_TITLE, demos.get(0).getTitle());
    	assertEquals(EIGHTY_CHARS_TEXT, demos.get(0).getText());
    	assertEquals("localhost", demos.get(0).getDomain().getName());
    	long id = demos.get(0).getId().longValue();
    	
    	assertContentEquals("{\"fileName\":\"/image/thumb.test.file.name\",\"id\":\"" + id + "\",\"status\":\"success\"}", response);
    }
    
    @Test
    public void testDemotivatorPreviewSuccess() throws IOException{
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", THIRTY_CHARS_TITLE);
    	createDemoParams.put("text", EIGHTY_CHARS_TEXT);
    	createDemoParams.put("mode", "preview");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(200, response);
    	assertContentType("application/json; charset=utf-8", response);
    	
    	List<Demotivator> demos = Demotivator.findAll();
    	assertEquals(0, demos.size());
    	
    	assertContentEquals("{\"fileName\":\"/image/thumb.test.file.name\",\"status\":\"success\"}", response);
    }
    
    @Test
    public void testTitileCantBeEmpty(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", "");
    	createDemoParams.put("text", "ordinary text");
    	createDemoParams.put("mode", "create");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(200, response);
    	assertContentType("application/json; charset=utf-8", response);
    	assertContentEquals("{\"title_error\":\"Required\"}", response);
    }
    
    @Test
    public void testTitleCantBeLongerThenThirtyWords(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", THIRTY_CHARS_TITLE + "a");
    	createDemoParams.put("text", "ordinary text");
    	createDemoParams.put("mode", "create");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(200, response);
    	assertContentType("application/json; charset=utf-8", response);
    	assertContentEquals("{\"title_error\":\"Title can\\u0027t be longer than 30 characters.\"}", response);
    }
    
    @Test
    public void testTextCantBeLongerThenEightyWords(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", "ordinary title");
    	createDemoParams.put("text", EIGHTY_CHARS_TEXT + "a");
    	createDemoParams.put("mode", "create");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(200, response);
    	assertContentType("application/json; charset=utf-8", response);
    	assertContentEquals("{\"text_error\":\"Text can\\u0027t be longer than 80 characters.\"}", response);
    }
    
    @Test
    public void testNoFileWasSent(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", "ordinary title");
    	createDemoParams.put("text", "ordinary text");
    	createDemoParams.put("mode", "create");
    	
    	Response response = POST("/create", createDemoParams);

    	assertStatus(200, response);
    	assertContentType("application/json; charset=utf-8", response);
    	assertContentEquals("{\"image_error\":\"Please select an image.\"}", response);
    }
    
    @Test
    public void testBadFileSent(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", "ordinary title");
    	createDemoParams.put("text", "ordinary text");
    	createDemoParams.put("mode", "create");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/bad.file");
    	fileParams.put("image", file);
    	
    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(200, response);
    	assertContentType("application/json; charset=utf-8", response);
    	assertContentEquals("{\"image_error\":\"Bad image file\"}", response);
    }
    
    @Test
    public void testUserNotAuthenticatedSaveAction(){
    	Fixtures.deleteAllModels();
    	GET("/logout");
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", THIRTY_CHARS_TITLE);
    	createDemoParams.put("text", EIGHTY_CHARS_TEXT);
    	createDemoParams.put("mode", "create");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);
    	
    	assertStatus(403, response);
    	assertContentType("text/html; charset=utf-8", response);
    	assertContentEquals("<h1>Please login</h1>", response);
    }    
    
    private void authenticate() {
    	Map<String, String> loginUserParams = new HashMap<String, String>();
    	loginUserParams.put("username", "frank.sinatra@gmail.com");
    	loginUserParams.put("password", "123");
    	POST("/secure/authenticate", loginUserParams);
    }
}
