package controllers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Before;

import models.Demotivator;
import models.User;
import net.sf.oval.constraint.MaxLength;
import play.data.validation.Required;
import play.i18n.Lang;
import play.mvc.Controller;
import play.mvc.Http.Header;
import security.SecureController;
import services.DemotivatorCreator;
import services.DemotivatorCreatorImpl;

public class Application extends Controller {

	public static void index() {
		List<Demotivator> demotivators = Demotivator.find("order by id desc").fetch(25);
		
		render(demotivators);
	}
	
	public static void single(long id) {
		Demotivator demotivator = Demotivator.findById(id);
		
		render(demotivator);
	}
	
	public static void locale(String language){
		Lang.change(language);
		
		Header referer = request.headers.get("referer");
	    if(referer == null){
	        index();
	    }else{
	        redirect(referer.value());
	    }
	}

	@SecureController
	public static void add(){
		try {
			Secure.checkAccess();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		render();
	}

	public static void create(@Required @MaxLength(30) String title, @Required @MaxLength(120) String text, File[] image) {

		validation.required(image[0]); //check that file is not null
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			add(); //go back to create Demotivator page and show errors
		}

		DemotivatorCreator creator = new DemotivatorCreatorImpl();
		String fileName = null;
		try {
			fileName = creator.createDemotivator(image[0], title, text);
		} catch (IOException e) {
			e.printStackTrace();
			error(e.getMessage());
		}
		
		//todo user!!!
		
		User user = new User("a@a.com", "pass", "pubsy");
		user.save();
		
		Demotivator d = new Demotivator(title, fileName, user);
		d.save();
		
		single(d.id);
	}
}