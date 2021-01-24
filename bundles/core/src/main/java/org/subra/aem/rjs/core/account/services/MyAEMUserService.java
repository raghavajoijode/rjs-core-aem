package org.subra.aem.rjs.core.account.services;

public interface MyAEMUserService {

	String createUser(String userName, String password, String groupName);

	String createGroup(String groupName);

	boolean isExistingUserName(String userName);

}
