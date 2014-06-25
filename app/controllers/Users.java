package controllers;

import models.User;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.data.validation.Error;

/**
 * This controller renders register page.
 * Handles register requests, including validation.
 * @author vitaliikravets
 */
public class Users extends BaseController {

	public static void register(){
		render();
	}
	
	public static void registerNewUser(
			@Required String displayname,
			@Required @Email String email,
			@Required String loginname,
			@Required String password,
			@Required @Equals("password") String confirmpassword) throws Throwable{
		
		validateParameters();
		
		User user = new User(email, loginname, password, displayname, false);
		user.save();
		
		Secure.login();
	}

	private static void validateParameters() {
		if (Validation.hasErrors()) {
			for(Error e: Validation.errors()){
				System.out.println(e.message());
			}
			params.flash();			//Keeping values entered in form fields
			Validation.keep();		//Keeping validation errors
			register();				//Redirecting back to Add Demotivator page
		}
	}
}
