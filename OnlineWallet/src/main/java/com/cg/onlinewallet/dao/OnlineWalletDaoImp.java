package com.cg.onlinewallet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;

import com.cg.onlinewallet.entities.*;
import com.cg.onlinewallet.entities.Account.status;
import com.cg.onlinewallet.exceptions.UnauthorizedAccessException;

@Repository
public class OnlineWalletDaoImp implements OnlineWalletDao
{
	@PersistenceContext
	private EntityManager entityManager;

	public OnlineWalletDaoImp()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveUser(User user) 
	{
		// TODO Auto-generated method stub
		entityManager.persist(user);
	}


	@Override
	public boolean checkUserByEmail(String email)
	{
		String Qstr = "SELECT user.email FROM WalletUser user WHERE user.email= :email";
		
		TypedQuery<String> query = entityManager.createQuery(Qstr, String.class).setParameter("email", email);
		try
		{
			query.getSingleResult();
		}
		catch (Exception ex)
		{
			return false;
		}
		return true;
	}
	
	/*********************************************************************************************************************
	* Method:				GetUserByEmail
	
	* Description:			To access the user with the given email
	
	* @param email:			User's email
	
	* @returns user:		It will return the user
	
	* Created By:			Vinay Kumar Singh
	***********************************************************************************************************************/
	@Override
	public User getUserByEmail(String email) 
	{
		String Qstr = "SELECT user FROM WalletUser user WHERE user.email= :email";
		
		TypedQuery<User> query = entityManager.createQuery(Qstr, User.class).setParameter("email",
				email);
		return query.getSingleResult();
	}

	/*********************************************************************************************************************
	* Method:				GetActiveUserList
	
	* Description:			To access the list of users whose account is active
	
	* @returns userList:	It will return the list users whose accounts are active
	
	* Created By:			Abhishek Sharma
	***********************************************************************************************************************/
	
	@Override
	public List<String> getActiveUserList() 
	{
		String Qstr = "SELECT user.email FROM WalletUser user JOIN user.accountDetail account WHERE account.userStatus= :userStatus";
		TypedQuery<String> query = entityManager.createQuery(Qstr, String.class).setParameter("userStatus",
				status.active);
		List<String> userList;
		try 
		{
			userList = query.getResultList();
		} 
		catch (Exception exception)
		{
			throw new UnauthorizedAccessException("No user Exist for given criteria");
		}
		return userList;
	}

	/*********************************************************************************************************************
	  * Method:			GetNonActiveUserList
	 
	  * Description:		To access the list of users whose account is inactive
	  
	  * @returns userList: It will return the users whose accounts are inactive
	  
	  * Created By:		Abhishek Sharma
	   **********************************************************************************************************************/
	
	@Override
	public List<String> getNonActiveUserList()
	{
		String Qstr = "SELECT user.email FROM WalletUser user JOIN user.accountDetail account WHERE account.userStatus= :userStatus";
		TypedQuery<String> query = entityManager.createQuery(Qstr, String.class).setParameter("userStatus",
				status.non_active);
		List<String> userList;
		try 
		{
			userList = query.getResultList();
		}
		catch (Exception exception)
		{
			throw new UnauthorizedAccessException("No user Exist for given criteria");
		}
		return userList;
	}
	
	@Override
	public User getUser(Integer userId)
	{
		User user = entityManager.find(User.class, userId);
		return user;
	}
}