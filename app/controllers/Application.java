package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import models.Demotivator;
import models.User;
import net.sf.oval.constraint.MaxLength;
import play.data.validation.Required;
import play.mvc.Controller;
import services.DemotivatorCreator;
import services.DemotivatorCreatorImpl;
import utils.ImageUtils;
import utils.ImageUtils;

public class Application extends Controller {

	public static void index() {
		List<Demotivator> demotivators = Demotivator.find("order by id desc").fetch(10);
		
		render(demotivators);
	}
	
	public static void single(long id) {
		Demotivator demotivator = Demotivator.findById(id);
		
		render(demotivator);
	}

	public static void add() {
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
		
		index();
	}
}