package controllers;

import java.util.List;

import models.Demotivator;
import play.i18n.Lang;
import play.mvc.Controller;
import play.mvc.Http.Header;

/**
 * Main controller renders latest 10 Demotivators on index page.
 * Displays single demotivator.
 * Swithces locale.
 * @author vitaliikravets
 */
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