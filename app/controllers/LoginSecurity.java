package controllers;

import models.User;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class LoginSecurity extends Controller {

	public static class Login {
		public String username;
		public String password;
	}
	
	public static Result login() {
		return ok(login.render());
	}
	
	public static Result authenticate(){
		Form<Login> form = Form.form(Login.class).bindFromRequest();
		Login login = form.get();
		
		if (form.hasErrors()) {
			return redirect(routes.LoginSecurity.login());
		}
		
		User user = User.autenthicate(login.username, login.password);
		if (user == null) {

			return redirect(routes.LoginSecurity.login());
		}
		session().clear();
		session("username", login.username);
		
		return redirect(routes.Application.index());	
	}
	
	public static Result logout(){
		session().clear();
		return redirect(routes.LoginSecurity.login());
	}
}
