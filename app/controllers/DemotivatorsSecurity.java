package controllers;

import play.mvc.Before;
import models.User;
import security.BCrypt;

/**
 * This class is a part of Secure module API.
 * It handles authentication.
 */
public class DemotivatorsSecurity extends Secure.Security{
	
	static boolean authenticate(String username, String password) {
        User user = User.find("byEmail", username).first();

        return user != null && BCrypt.checkpw(password, user.getPassword());
    }
	
	static User currentUser() {
		String email = connected();
		User user = User.find("byEmail", email).first();
		return user;
	}
	
	static void onAuthenticated(){
		DemotivatorsController.interception("/secure/login");
	}

}
