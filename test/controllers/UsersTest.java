package controllers;

import java.util.HashMap;
import java.util.Map;

import models.User;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class UsersTest extends FunctionalTest{

	@Before
	public void deleteModels(){
		Fixtures.deleteAllModels();
	}
	
	/**
	 * Register page sanity test
	 */
	@Test
	public void testRegisterPage(){
		Response response = GET("/register");
		assertIsOk(response);
	}
	
	@Test
	public void testUserRegistrationSuccess(){
		
		User user = User.find("byEmail", "frank.sinatra@gmail.com").first();
		assertNull(user);
		
		Map<String, String> registerUserParams = new HashMap<String, String>();
		registerUserParams.put("email", "frank.sinatra@gmail.com");
		registerUserParams.put("loginName", "franky");
		registerUserParams.put("displayname", "Frank Sinatra");
		registerUserParams.put("password", "123");
		registerUserParams.put("confirmpassword", "123");
		
    	Response response = POST("/registerNewUser", registerUserParams);
    	assertStatus(302, response);
    	
		user = User.find("byEmail", "frank.sinatra@gmail.com").first();
		assertNotNull(user);
		assertEquals("frank.sinatra@gmail.com", user.getEmail());
		assertEquals("Frank Sinatra", user.getDisplayName());
		assertEquals("franky", user.getLoginName());
		
		//Check that password is not saved as plain text
		assertNotSame("123", user.getPassword());
		//Check that timestamp was created
		assertNotNull(user.getDate());
	}
	
	@Test
	public void testUserRegistrationFailsEmailRequired(){
		
		Map<String, String> registerUserParams = new HashMap<String, String>();
		registerUserParams.put("displayname", "Frank Sinatra");
		registerUserParams.put("loginName", "franky");
		registerUserParams.put("password", "123");
		registerUserParams.put("confirmpassword", "123");
		
    	Response response = POST("/registerNewUser", registerUserParams);
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/register", response);
	}
	
	@Test
	public void testUserRegistrationFailsDisplayNameRequired(){
		
		Map<String, String> registerUserParams = new HashMap<String, String>();
		registerUserParams.put("email", "frank.sinatra@gmail.com");
		registerUserParams.put("loginName", "franky");
		registerUserParams.put("password", "123");
		registerUserParams.put("confirmpassword", "123");
		
    	Response response = POST("/registerNewUser", registerUserParams);
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/register", response);
	}
	
	@Test
	public void testUserRegistrationFailsPasswordRequired(){
		
		Map<String, String> registerUserParams = new HashMap<String, String>();
		registerUserParams.put("email", "frank.sinatra@gmail.com");
		registerUserParams.put("loginName", "franky");
		registerUserParams.put("displayname", "Frank Sinatra");
		registerUserParams.put("confirmpassword", "123");
		
    	Response response = POST("/registerNewUser", registerUserParams);
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/register", response);
	}
	
	@Test
	public void testUserRegistrationFailsConfirmPasswordRequired(){
		
		Map<String, String> registerUserParams = new HashMap<String, String>();
		registerUserParams.put("email", "frank.sinatra@gmail.com");
		registerUserParams.put("loginName", "franky");
		registerUserParams.put("displayname", "Frank Sinatra");
		registerUserParams.put("password", "123");
		
    	Response response = POST("/registerNewUser", registerUserParams);
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/register", response);
	}
	
	@Test
	public void testUserRegistrationFailsLoginNameRequired(){
		
		Map<String, String> registerUserParams = new HashMap<String, String>();
		registerUserParams.put("email", "frank.sinatra@gmail.com");
		registerUserParams.put("displayname", "Frank Sinatra");
		registerUserParams.put("password", "123");
		registerUserParams.put("confirmpassword", "123");
		
    	Response response = POST("/registerNewUser", registerUserParams);
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/register", response);
	}
	
	@Test
	public void testUserRegistrationFailsConfirmPasswordHasToMatchPassword(){
		
		Map<String, String> registerUserParams = new HashMap<String, String>();
		registerUserParams.put("email", "frank.sinatra@gmail.com");
		registerUserParams.put("loginName", "franky");
		registerUserParams.put("displayname", "Frank Sinatra");
		registerUserParams.put("password", "123");
		registerUserParams.put("confirmpassword", "321");
		
    	Response response = POST("/registerNewUser", registerUserParams);
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/register", response);
	}
	
	@Test
	public void testUserRegistrationFailsEmailHasToMatchRegex(){
		
		Map<String, String> registerUserParams = new HashMap<String, String>();
		registerUserParams.put("email", "frank.sinatra.gmail.com");
		registerUserParams.put("loginName", "franky");
		registerUserParams.put("displayname", "Frank Sinatra");
		registerUserParams.put("password", "123");
		registerUserParams.put("confirmpassword", "321");
		
    	Response response = POST("/registerNewUser", registerUserParams);
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/register", response);
	}
	
	@Test
	public void testUserRegistrationFailsEmailHasToMatchRegexTwo(){
		
		Map<String, String> registerUserParams = new HashMap<String, String>();
		registerUserParams.put("email", "frank.sinatra@gmail");
		registerUserParams.put("loginName", "franky");
		registerUserParams.put("displayname", "Frank Sinatra");
		registerUserParams.put("password", "123");
		registerUserParams.put("confirmpassword", "321");
		
    	Response response = POST("/registerNewUser", registerUserParams);
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "/register", response);
	}
	
    @Test
    public void testReuestWithoutWWWDoesntGetRedirected(){
    	Request r = newRequest();
    	r.domain = "test.com";
    	Response response = GET(r, "/register");
    	
    	assertStatus(200, response);
    }
	
    @Test
    public void testReuestToWWWGetsRedirectedRegisterPage(){
    	Request r = newRequest();
    	r.domain = "www.test.com";
    	Response response = GET(r, "/register");
    	
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "http://test.com/register", response);
    }
    
}
