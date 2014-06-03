package model;
import javax.persistence.PersistenceException;

import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;
import security.BCrypt;

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
	
    @Test(expected = PersistenceException.class)
    public void testDisplayNameIsUnique(){
    	User user = new User("email1@test.com", "password", "TestName", true);
    	user.save();
    	
    	User otherUserWithSameDisplayName = new User("email2@test.com", "password", "TestName", true);
    	otherUserWithSameDisplayName.save();
    }
    
    @Test(expected = PersistenceException.class)
    public void testDEmailIsUnique(){
    	User user = new User("email@test.com", "password", "TestName!", true);
    	user.save();
    	
    	User otherUserWithSameEmail = new User("email@test.com", "password", "TestName@", true);
    	otherUserWithSameEmail.save();
    }
    

}
