package controllers;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import play.test.FunctionalTest;

public class AutenticatedControllerTest extends FunctionalTest {

	@Test
    public void authenticate() {
    	Map<String, String> loginUserParams = new HashMap<String, String>();
    	loginUserParams.put("username", "frank.sinatra@gmail.com");
    	loginUserParams.put("password", "123");
    	POST("/secure/authenticate", loginUserParams);
    }
}
