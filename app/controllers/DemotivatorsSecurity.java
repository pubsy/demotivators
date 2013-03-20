package controllers;

import security.BCrypt;
import models.User;
import controllers.Secure;

/**
 * Don't like having this class in controllers package,
 * but I'm using Secure.Security.connected() here and it
 * has default access modifier. 
 * @author vitaliikravets
 *
 */
public class DemotivatorsSecurity extends Secure.Security{

	static boolean authenticate(String username, String password) {
        User user = User.find("byEmail", username).first();

        return user != null && BCrypt.checkpw(password, user.getPassword());
    }

	public static User connectedUser() {
		String username = Secure.Security.connected();
        User user = User.find("byEmail", username).first();
		return user;
	}
}
