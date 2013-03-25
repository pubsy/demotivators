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
    private static final String HUNDRER_TWENTY_CHARS_TEXT = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
    
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
	 * Check Demotivator created and browser redirected to new single
	 * Demotivator page. The title and text are max in size.
	 * @throws IOException
	 */
    @Test
    public void testDemotivatorCreateSuccess() throws IOException{
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", THIRTY_CHARS_TITLE);
    	createDemoParams.put("text", HUNDRER_TWENTY_CHARS_TEXT);
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(302, response);
    	
    	List<Demotivator> demos = Demotivator.findAll();
    	assertEquals(1, demos.size());
    	assertEquals(THIRTY_CHARS_TITLE, demos.get(0).getTitle());
    	long id = demos.get(0).getId().longValue();
    	
    	assertHeaderEquals("Location", "/single/" + id, response);
    }
    
    @Test
    public void testTitileCantBeEmpty(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", "");
    	createDemoParams.put("text", "ordinary text");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/add", response);
    }
    
    @Test
    public void testTitleCantBeLongerThenThirtyWords(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", THIRTY_CHARS_TITLE + "a");
    	createDemoParams.put("text", "ordinary text");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/add", response);
    }
    
    @Test
    public void testTextCantBeLongerThenHudnredTwentyWords(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", "ordinary title");
    	createDemoParams.put("text", HUNDRER_TWENTY_CHARS_TEXT + "a");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/image.jpg");
    	fileParams.put("image", file);

    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/add", response);
    }
    
    @Test
    public void testNoFileWasSent(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", "ordinary title");
    	createDemoParams.put("text", "ordinary text");
    	
    	Response response = POST("/create", createDemoParams);

    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/add", response);
    }
    
    @Test
    public void testBadFileSent(){
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data/user.yml");
    	
    	authenticate();
    	
    	Map<String, String> createDemoParams = new HashMap<String, String>();
    	createDemoParams.put("title", "ordinary title");
    	createDemoParams.put("text", "ordinary text");
    	Map<String, File> fileParams = new HashMap<String, File>();
    	File file = new File("test/data/bad.file");
    	fileParams.put("image", file);
    	
    	Response response = POST("/create", createDemoParams, fileParams);

    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/add", response);
    }
    
    private void authenticate() {
    	Map<String, String> loginUserParams = new HashMap<String, String>();
    	loginUserParams.put("username", "frank.sinatra@gmail.com");
    	loginUserParams.put("password", "123");
    	POST("/secure/authenticate", loginUserParams);
    }
}
