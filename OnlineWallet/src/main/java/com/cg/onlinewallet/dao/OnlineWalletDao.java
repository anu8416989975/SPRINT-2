package com.cg.onlinewallet.dao;

import java.util.List;

import com.cg.onlinewallet.entities.User;

public interface OnlineWalletDao {
	void saveUser(User user);
	
	User getUser(Integer userId);
	
	User getUserByEmail(String email);

	boolean checkUserByEmail(String email);

	List<String> getActiveUserList();

	List<String> getNonActiveUserList();

}
