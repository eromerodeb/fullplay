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
		String error = flash().get("error");
		return ok(login.render(error));
	}
	
	public static Result authenticate(){
		Form<Login> form = Form.form(Login.class).bindFromRequest();
		Login login = form.get();
		
		if (form.hasErrors()) {
			flash().put("error", "Invalid Credentials");
			return redirect(routes.LoginSecurity.login());
		}
		
		User user = User.autenthicate(login.username, login.password);
		if (user == null) {
			flash().put("error", "Invalid Credentials");
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
