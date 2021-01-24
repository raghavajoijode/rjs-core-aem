package org.subra.aem.rjs.core.account.services;

public interface UserService {

	boolean isExistingUser(String email);
	
	String createUser(String email, String password, String name);
	
	String authenticateUser(String email, String password);

}
