package controllers;

import models.User;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class LoginSecurity extends Controller {

	public static class Login {
		public String email;
		public String password;
	}
	
	public static Result login() {
		return ok(login.render(Form.form(Login.class)));
	}
	
	public static Result authenticate(){
		Form<Login> form = Form.form(Login.class).bindFromRequest();
		Login login = form.get();
		
		if (form.hasErrors()) {
			return redirect(routes.LoginSecurity.login());
		}
		
		User user = User.autenthicate(login.email, login.password);
		if (user == null) {
			form.error("Invalid Credentials.");
//			return forbidden("Invalid Credentials.");
			return redirect(routes.LoginSecurity.login());
		}
		session().clear();
		session("email", login.email);
		
		return redirect(routes.Application.index());	
	}
	
	public static Result logout(){
		session().clear();
		return redirect(routes.LoginSecurity.login());
	}
}
