package controllers;

import java.util.Date;

import models.Comment;
import models.Demotivator;
import models.User;

import org.apache.commons.lang.StringUtils;

import play.i18n.Messages;
import utils.SharedConstants;
import controllers.Secure.Security;

public class Comments extends BaseController {
	
	public static void addComment(long demoId, String text){
		
		User user = BaseSecurity.currentUser();		
		if(user == null){
			error(403, Messages.get(SharedConstants.PLEASE_LOGIN));
		}
		
		Demotivator demo = Demotivator.find("id = ? and deleted = 0", demoId).first();
		if (demo == null) {
			error(404, "Demotivator with id: " + demoId + " not found");
		}
		
		if (StringUtils.isBlank(text)) {
			error(400, "Comment text can not be empty");
		}
		
		Comment comment = new Comment();
		comment.setDate(new Date());
		comment.setDemotivator(demo);
		comment.setUser(user);
		comment.setText(text);
		comment.save();
		
		ok();
	}

}
