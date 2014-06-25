package controllers;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class BaseSecurityTest extends FunctionalTest{

	@Before
	public void before(){
		Fixtures.deleteAllModels();
	}
	
	@Test
	public void testUserConNotLoginWithHisEmail(){
    	Fixtures.loadModels("data/user.yml");
		
		Map<String, String> loginUserParams = new HashMap<String, String>();
    	loginUserParams.put("username", "frank.sinatra@gmail.com");
    	loginUserParams.put("password", "123");
    	Response response = POST("/secure/authenticate", loginUserParams);
    	
    	//Check that user was redirected to root path
    	assertHeaderEquals("Location", "/secure/login", response);

	}
	
	@Test
	public void UserLoginFailsWithWrongEmail(){
		Fixtures.loadModels("data/user.yml");
		
		Map<String, String> loginUserParams = new HashMap<String, String>();
    	loginUserParams.put("username", "wrong@email.com");
    	loginUserParams.put("password", "123");
    	Response response = POST("/secure/authenticate", loginUserParams);
    	
    	//Check that user was redirected back to login page
    	assertHeaderEquals("Location", "/secure/login", response);
	}
	
	@Test
	public void UserLoginFailsWithWrongPass(){
		Fixtures.loadModels("data/user.yml");
		
		Map<String, String> loginUserParams = new HashMap<String, String>();
    	loginUserParams.put("username", "franky");
    	loginUserParams.put("password", "wrong_pass");
    	Response response = POST("/secure/authenticate", loginUserParams);
    	
    	//Check that user was redirected back to login page
    	assertHeaderEquals("Location", "/secure/login", response);
	}
	
	@Test
	public void UserLoginFailsWhenUserDoesntExist(){
		Map<String, String> loginUserParams = new HashMap<String, String>();
    	loginUserParams.put("username", "franky");
    	loginUserParams.put("password", "123");
    	Response response = POST("/secure/authenticate", loginUserParams);
    	
    	//Check that user was redirected back to login page
    	assertHeaderEquals("Location", "/secure/login", response);
	}
	
    @Test
    public void testReuestToWWWGetsRedirectedOnAuth(){
    	Fixtures.loadModels("data/user.yml");
		
		Map<String, String[]> loginUserParams = new HashMap<String, String[]>();
    	loginUserParams.put("username", new String[]{"franky"});
    	loginUserParams.put("password", new String[]{"123"});

    	Request r = newRequest();
    	r.domain = "www.test.com";
    	r.params.data = loginUserParams;
    	Response response = POST(r, "/secure/authenticate");
    	
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "http://test.com/secure/login", response);
    }
    
    @Test
    public void testLoginWithLoginName(){
    	Fixtures.loadModels("data/user.yml");
		
		Map<String, String> loginUserParams = new HashMap<String, String>();
    	loginUserParams.put("username", "franky");
    	loginUserParams.put("password", "123");
    	Response response = POST("/secure/authenticate", loginUserParams);
    	
    	//Check that user was redirected to root path
    	assertHeaderEquals("Location", "/", response);
    }

}
