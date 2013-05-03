package controllers;

import java.io.File;

import models.Demotivator;
import play.i18n.Lang;
import play.modules.paginate.ModelPaginator;
import play.mvc.Http.Header;
import services.DemotivatorCreator;

/**
 * Main controller renders latest 10 Demotivators on index page.
 * Displays single demotivator.
 * Switches locale.
 * @author vitaliikravets
 */
public class Application extends DemotivatorsController {

	public static void index() {
		ModelPaginator<Demotivator> paginator = new ModelPaginator(Demotivator.class, "domain=?", request.current().domain)
			.orderBy("date desc");
		paginator.setPageSize(10);
	    render(paginator);
	}
	
	public static void single(long id) {
		Demotivator demotivator = Demotivator.findById(id);
		
		if(demotivator == null){
			index();
		}
		
		render(demotivator);
	}
	
	public static void next(long id) {
		Demotivator demotivator = Demotivator.find("id < ? order by date desc", id).first();
		
		if(demotivator == null){
			index();
		}
		
		single(demotivator.id);
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
	
	public static void image(String fileName) {
		String imgDirPath = DemotivatorCreator.IMAGE_DIR_PATH;
		File image = new File(imgDirPath + fileName);
	    renderBinary(image);
	}
}