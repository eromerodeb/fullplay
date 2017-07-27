package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class User extends Model {
	
	private static final Finder<Integer, User> FIND = new Finder<>(Integer.class, User.class);
	
	@Id
	private Integer ID;

	@Required
	@Column(unique=true)
	private String username;
	
	@Required
	private String password;
	
	public User(Integer _id, String _username, String _password) {
		ID = _id;
		username = _username;
		password = _password;
	}
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static User autenthicate(String _username, String _password) {
		return FIND.where().eq("username", _username).eq("password", _password).findUnique();
	}
	
	public static void save(User _user) {
		_user.save();
	}
}
