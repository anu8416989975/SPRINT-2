package com.cg.onlinewallet.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.onlinewallet.dao.*;
import com.cg.onlinewallet.entities.*;
import com.cg.onlinewallet.entities.Account.status;
import com.cg.onlinewallet.entities.User.type;
import com.cg.onlinewallet.exceptions.*;

@Transactional
@Service
public class OnlineWalletServiceImp implements OnlineWalletService
{

	public OnlineWalletServiceImp() 
	{
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private OnlineWalletDao onlineWalletDao;
	
	/******************************************************************************************************
	 * Method:				Admin login
	 
	 * Description:			Map the request of admin for login
	 
	 * Created By:			Abhishek Sharma
	 ******************************************************************************************************/
	
	@Override
	public Integer loginAdmin(String email, String password) 
	{
		if (!onlineWalletDao.checkUserByEmail(email))
		{
			throw new UnauthorizedAccessException(
					"No Admin exist for this email address. Kindly ask administration to get yourself registered");
		}
		
		User user = onlineWalletDao.getUserByEmail(email);
		
		if (user.getUserType() == type.user)
		{
			throw new UnauthorizedAccessException("You are not authorized to login from here");
		}
		if (!user.getPassword().equals(password))
		{
			throw new ValidationException("The LoginName and password Combination does not match");
		}
		return user.getUserID();
	}
	
	/*******************************************************************************************************
	 * Method:				User list
	 
	 * Description:			To map the request of admin to get the list of wallet users

	 * Created By:			Abhishek Sharma
	 *******************************************************************************************************/
	
	@Override
	public List<String> getUserList(Integer adminId, String userStatus)
	{
		User admin = onlineWalletDao.getUser(adminId);
		
		if (admin.getUserType() == type.user)
		{
			throw new UnauthorizedAccessException("You are not authorized or you are not an Admin");
		}
		if (userStatus.equalsIgnoreCase(new String("non_active")))
		{
			return onlineWalletDao.getNonActiveUserList();
		}
		else if (userStatus.equalsIgnoreCase(new String("active")))
		{
			return onlineWalletDao.getActiveUserList();
		}
		
		throw new WrongValueException("Not a criteria to fetch user details");
	}

	/******************************************************************************************************
	 * Method:								ChangeUserStatus 
	 
	 * Description: 						Changes the status of account of user
	 										from active to non-active and vice-versa
	  
	 * @param adminId: 						Admin's userId
	  
	 * @param email:     					User's email whose status has to be changed
	  
	 * @param userstatus:					user status
	  
	 * @throws UnauthorizedAccessException: If the user associated with adminId is
	 										not a admin type
	  
	 * @throws InvalidException: 			It is raised if there is no user associated with
	 										the login name provided
	  
	 * @throws UnauthorizedAccessException: It is raised if the user associated with
	  										the login name is admin type
	  
	 * @throws WrongValueException: 		It is raised if the variable userStatus is other
	  										then values
	  										
	 * Created By:							Anurag Kumar
	 ******************************************************************************************************/
	
	@Override
	public String changeUserStatus(Integer adminId, String email, String userStatus) 
	{
		
		User admin = onlineWalletDao.getUser(adminId);
		
		if (admin.getUserType() == type.user)
		{
			throw new UnauthorizedAccessException("You are Authorized to perform this task");
		}
		
		if (onlineWalletDao.checkUserByEmail(email) == false)
		{
			throw new InvalidException("There is no user with this LoginName. Please Enter a valid LoginName");
		}
		
		User user = onlineWalletDao.getUserByEmail(email);
		
		if (user.getUserType() == type.admin)
		{
			throw new UnauthorizedAccessException("Can't perform Task/Unauthorized access");
		}
		
		if (userStatus.equals(new String("non_active")))
		{
			user.getAccountDetail().setUserStatus(status.non_active);
		}
		
		else if (userStatus.equals(new String("active"))) 
		{
			user.getAccountDetail().setUserStatus(status.active);
		} 
		
		else
		{
			throw new WrongValueException("The Status code does not exist");
		}
		
		return user.getEmail();
		
	}

}
