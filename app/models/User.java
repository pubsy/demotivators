package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.codec.digest.DigestUtils;

import play.db.jpa.Model;
import security.BCrypt;

@Entity
@Table(name="Users")
public class User extends Model{
	
	@Column(unique=true)
	private String email;
	
	@Column(unique=true)
	private String loginName;
	
	private String password;
	
	@Column(unique=true)
	private String displayName;
	
	private Date date;
	
	private boolean activated;
	
	@Enumerated(EnumType.STRING)
	private Encryption encryption = Encryption.BCRYPT;
	
	public enum Encryption{
		BCRYPT, MD5
	}
	
	public User(String email, String loginName, String password, String displayName, boolean activated) {
		this.email = email;
		this.loginName = loginName;
		setPassword(password);
		this.displayName = displayName;
		this.date = new Date();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		String hashed = null;
		if(encryption == Encryption.BCRYPT){
			hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		} else{
			hashed = DigestUtils.md5Hex(password);
		}
		this.password = hashed;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public Encryption getEncryption() {
		return encryption;
	}

	public void setEncryption(Encryption encryption) {
		this.encryption = encryption;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
