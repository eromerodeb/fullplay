package controllers;

import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

@Security.Authenticated(Secured.class)
public class Application extends Controller {
	
	public static Result index() {
		return ok(index.render(""));
    }
}
