package controllers;

import models.Demotivator;
import play.i18n.Messages;
import play.mvc.Http.StatusCode;
import utils.SharedConstants;
import controllers.Secure.Security;

public class Demotivators extends BaseController {

	
	public static void delete(long id) {
		String userName = Security.connected();	
		if(userName == null){
			error(StatusCode.FORBIDDEN, Messages.get(SharedConstants.PLEASE_LOGIN));
		}
		
		Demotivator demo = Demotivator.findById(id);
		if(!demo.getAuthor().getEmail().equals(BaseSecurity.connected())){
			error(StatusCode.FORBIDDEN, Messages.get(SharedConstants.ACCESS_VIOLATION));
		}

		demo.setDeleted(true);
		demo.save();
		Application.next(id);
	}
}
