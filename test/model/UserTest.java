package model;
import org.junit.*;

import java.util.*;
import play.test.*;
import security.BCrypt;
import models.*;

public class UserTest extends UnitTest {
	
	@Before
	public void deleteModels() {
		Fixtures.deleteAllModels();
	}

	/**
	 * Creating a new user. And retrieving it by email.
	 */
	@Test
	public void saveAndGetUser() {
		Fixtures.loadModels("data/user.yml");
		
	    User user = User.find("byEmail", "frank.sinatra@gmail.com").first();

	    assertNotNull(user);
	    assertEquals("Frank Sinatra", user.getDisplayName());
	    assertEquals("frank.sinatra@gmail.com", user.getEmail());
	    assertTrue(BCrypt.checkpw("123", user.getPassword()));
	}
	
	/**
	 * Trying to retrieve not existing user must return null
	 */
	@Test
	public void getNotExistingUser() {
	    User user = User.find("byEmail", "wrong@email.com").first();
	    
	    assertNull(user);
	}

}
