package controllers;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http.Response;
import play.mvc.Util;
import play.test.FunctionalTest;

public abstract class AutenticatedControllerTest extends FunctionalTest {

	@Util
    public void authenticate() {
    	authenticate("frank.sinatra@gmail.com", "123");
    }
	
	@Util
	public void authenticate(String username, String password) {
		Map<String, String> loginUserParams = new HashMap<String, String>();
    	loginUserParams.put("username", username);
    	loginUserParams.put("password", password);
    	Response response = POST("/secure/authenticate", loginUserParams);
    	assertStatus(302, response);
    	String sessionCookieValue = response.cookies.get("PLAY_SESSION").value;
    	assertThat(sessionCookieValue, not(isEmptyString()));
	}
}
