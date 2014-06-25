package controllers;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

import play.mvc.Before;
import models.User;
import security.BCrypt;

/**
 * This class is a part of Secure module API.
 * It handles authentication.
 */
public class BaseSecurity extends Secure.Security{
	
	static boolean authenticate(String username, String password) {
		User user = User.find("byLoginName", username).first();

        if(user == null){
        	return false;
        }
        if(user.getEncryption() == User.Encryption.MD5){
        	return DigestUtils.md5Hex(password).equals(user.getPassword());
        }
        return BCrypt.checkpw(password, user.getPassword());
    }
	
	static User currentUser() {
		String loginName = connected();
		User user = User.find("byLoginName", loginName).first();
		return user;
	}
	
	static void onAuthenticated(){
		BaseController.interception("/secure/login");
	}

}
