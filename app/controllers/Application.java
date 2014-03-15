package controllers;

import java.io.File;

import models.Demotivator;
import models.Domain;
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
		Domain domain = Domain.getOrCreate(request.domain);
		ModelPaginator<Demotivator> paginator = new ModelPaginator(Demotivator.class, "domain = ?", domain).orderBy("date desc");
		paginator.setPageSize(15);
	    render(paginator);
	}
	
	public static void single(long id) {
		Demotivator demotivator = Demotivator.findById(id);
		
		if(demotivator == null){
			index();
		}
		
		if(DemotivatorsSecurity.isConnected()){
			flash.put("userDisplayName", DemotivatorsSecurity.currentUser().getDisplayName());
		}
		
		//Additional text in page title
		String pageTitle = demotivator.getTitle();
		
		render(demotivator, pageTitle );
	}
	
	public static void next(long id) {
		Domain domain = Domain.getOrCreate(request.domain);
		Demotivator demotivator = Demotivator.find("id < ? and domain = ? order by date desc", id, domain).first();
		
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