package controllers;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import models.Demotivator;
import models.User;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;
import services.DemotivatorCreator;

@With(Secure.class)
public class Creator extends Controller{
	
	private static final String TEXT_CANT_MESSAGE_KEY = "text.cant.be.longer";
	private static final String TITLE_CANT_MESSAGE_KEY = "title.cant.be.longer";
	private static final String PLEASE_SELECT_MESSAGE_KEY = "please.select.an.image";
	private static final String BAD_FILE_MESSAGE_KEY = "bad.file.error";
	
	/**
	 * We don't really need IOC in here. 
	 * It's injected just for the sake of it.
	 */
	@Inject
	static DemotivatorCreator creator;
	
	public static void add(){		
		render();
	}

	public static void create(@Required String title, String text, File[] image) {

		validateParameters(title, text, image);

		String fileName = null;
		try {
			fileName = creator.createDemotivator(image[0], title, text);
		} catch (IOException e) {
			validation.addError("image", BAD_FILE_MESSAGE_KEY);
			sendBackWithValidationErrors();
		}

		Demotivator demo = new Demotivator(title, fileName, currentUser());
		demo.save();
		
		Application.single(demo.getId());
	}

	private static User currentUser() {
		String email = Secure.Security.connected();
		return User.find("byEmail", email).first();
	}

	private static void validateParameters(String title, String text, File[] image) {
		if(title.length() > 30){
			validation.addError("title", TITLE_CANT_MESSAGE_KEY);
		}
		if(text.length() > 120){
			validation.addError("text", TEXT_CANT_MESSAGE_KEY);
		}
		
		boolean isNullFile = false;
		if(image == null || image[0] == null){
			isNullFile = true;
		}
		
		if (Validation.hasErrors() || isNullFile) {
			//can not populate <input type="file"> with value, so user
			//has to select a file every time he makes a mistake filling the form.
			validation.addError("image", PLEASE_SELECT_MESSAGE_KEY);
			
			sendBackWithValidationErrors();
		}
	}

	private static void sendBackWithValidationErrors() {
		params.flash();			//Keeping values entered in form fields
		Validation.keep();		//Keeping validation errors
		add();					//Redirecting back to Add Demotivator page
	}
}
