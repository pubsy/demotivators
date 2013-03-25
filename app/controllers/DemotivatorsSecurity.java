package controllers;

import models.User;
import security.BCrypt;

public class DemotivatorsSecurity extends Secure.Security{

	/**
	 * This method is a part of Secure module.
	 * It handles login requests.
	 */
	static boolean authenticate(String username, String password) {
        User user = User.find("byEmail", username).first();

        return user != null && BCrypt.checkpw(password, user.getPassword());
    }
}
