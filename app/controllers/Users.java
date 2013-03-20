package controllers;

import models.User;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.Required;
import play.mvc.Controller;

public class Users extends Controller {

	public static void register(){
		render();
	}
	
	public static void registerNewUser(
			@Required String displayname,
			@Required @Email String email,
			@Required String password,
			@Required @Equals("password") String confirmpassword){
		
		User user = new User(email, password, displayname);
		user.save();
		
		Application.index();
	}
}
