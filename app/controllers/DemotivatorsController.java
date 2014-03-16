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

public class DemotivatorsController extends Controller{

	@Before
	static void interception(String url){
		String domain = request.domain;
		
		if (domain.startsWith("www.")){
			request.domain = domain.substring(4);
			if(url == null){
				redirect(request.url);
			}else{
				redirect(url);
			}
		}
		
		String domainLocale = Play.configuration.getProperty(domain);
		if(StringUtils.isNotBlank(domainLocale) && !domainLocale.equals(Lang.get())){
			Lang.change(domainLocale);
		}
	}
}
