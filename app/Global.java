import models.User;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application arg0) {
		User.save(new User(0,"admin","admin"));
	}
	
}
