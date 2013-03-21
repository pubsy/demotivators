package model;
import org.junit.*;
import java.util.*;
import play.test.*;
import security.BCrypt;
import models.*;

public class UserTest extends UnitTest {

	/**
	 * Creating a new user. And retrieving it by email.
	 */
	@Test
	public void saveAndGetUser() {
	    new User("a@a.com", "pass", "Me").save();
	    User user = User.find("byEmail", "a@a.com").first();

	    assertNotNull(user);
	    assertEquals("Me", user.getDisplayName());
	    assertEquals("a@a.com", user.getEmail());
	    assertTrue(BCrypt.checkpw("pass", user.getPassword()));
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
