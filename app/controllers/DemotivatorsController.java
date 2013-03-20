package controllers;

import play.mvc.Before;
import play.mvc.Controller;
import security.SecureController;

public class DemotivatorsController extends Controller{
	
	/**
	 * If action is annotated with @SecureController, check whether user is
	 * logged in.
	 */
	@Before
	public static void securityCheck() {
		if (getActionAnnotation(SecureController.class) != null) {
			try {
				Secure.checkAccess();
			} catch (Throwable e) {
				e.printStackTrace();
				error(e.getMessage());
			}
		}
	}

}
