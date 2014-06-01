package controllers;

import java.util.List;

import controllers.Secure.Security;
import models.Comment;
import models.Demotivator;
import models.Domain;
import play.db.jpa.JPABase;
import play.i18n.Lang;
import play.i18n.Messages;
import play.modules.paginate.ModelPaginator;
import play.mvc.Http.Header;
import play.mvc.Http.StatusCode;
import utils.SharedConstants;

/**
 * Main controller renders latest 10 Demotivators on index page.
 * Displays single demotivator.
 * Switches locale.
 * @author vitaliikravets
 */
public class Application extends BaseController {

	public static void index() {
		Domain domain = Domain.getOrCreate(request.domain);
		ModelPaginator<Demotivator> paginator = new ModelPaginator(Demotivator.class, "domain = ? and deleted = 0", domain).orderBy("date desc");
		paginator.setPageSize(15);
	    render(paginator);
	}
	
	public static void single(long id) {
		Demotivator demotivator = Demotivator.find("id = ? and deleted = 0", id).first();
		
		if(demotivator == null){
			index();
		}
		
		if(BaseSecurity.isConnected()){
			flash.put("userDisplayName", BaseSecurity.currentUser().getDisplayName());
		}
		
		//Additional text in page title
		String pageTitle = demotivator.getTitle();
		
		render(demotivator, pageTitle );
	}
	
	public static void next(long id) {
		Domain domain = Domain.getOrCreate(request.domain);
		Demotivator demotivator = Demotivator.find("id < ? and domain = ? and deleted = 0 order by date desc", id, domain).first();
		
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

}