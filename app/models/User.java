package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class User extends Model{
	private String email;
	private String password;
	private String displayName;
	
	public User(String email, String password, String displayName) {
		this.email = email;
		this.password = password;
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
