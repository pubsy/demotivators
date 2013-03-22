package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import models.Demotivator;
import models.User;
import net.sf.oval.constraint.MaxLength;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.i18n.Lang;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Header;
import security.SecureController;
import services.DemotivatorCreator;


public class Application extends Controller {

	public static void index() {
		List<Demotivator> demotivators = Demotivator.find("order by date desc").fetch(10);
		
		render(demotivators);
	}
	
	public static void single(long id) {
		Demotivator demotivator = Demotivator.findById(id);
		
		if(demotivator == null){
			index();
		}
		
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
}