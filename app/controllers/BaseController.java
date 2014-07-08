package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import models.Domain;
import play.Play;
import play.db.jpa.JPABase;
import play.i18n.Lang;
import play.mvc.Before;
import play.mvc.Controller;

public class BaseController extends Controller{

	@Before
	static void interception(String url){
		String domainLocale = Play.configuration.getProperty(request.domain);
		if(StringUtils.isNotBlank(domainLocale) && !domainLocale.equals(Lang.get())){
			Lang.change(domainLocale);
		}
	}
}
