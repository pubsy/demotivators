package controllers;

import models.User;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.Required;
import play.data.validation.Validation;

/**
 * This controller renders register page.
 * Handles register requests, including validation.
 * @author vitaliikravets
 */
public class Users extends DemotivatorsController {

	public static void register(){
		render();
	}
	
	public static void registerNewUser(
			@Required String displayname,
			@Required @Email String email,
			@Required String password,
			@Required @Equals("password") String confirmpassword) throws Throwable{
		
		validateParameters();
		
		User user = new User(email, password, displayname, false);
		user.save();
		
		Secure.login();
	}

	private static void validateParameters() {
		if (Validation.hasErrors()) {
			params.flash();			//Keeping values entered in form fields
			Validation.keep();		//Keeping validation errors
			register();				//Redirecting back to Add Demotivator page
		}
	}
}
