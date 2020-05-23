package com.cg.onlinewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinewallet.service.*;

@CrossOrigin(origins = "*")
@RestController
public class OnlineWalletController {

	@Autowired
	private OnlineWalletService onlineWalletService;

	public OnlineWalletController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/")
	public String check()
	{
		return "WORKING";
	}
	
	/******************************************************************************************************
	 * Method:				Admin login
	 
	 * Description:			Map the request of admin for login
	 
	 * Created By:			Abhishek Sharma
	 ******************************************************************************************************/
	
	@GetMapping("/admin")
	
	public ResponseEntity<Integer> loginAdmin(String email, String password)
	{
		Integer userId = onlineWalletService.loginAdmin(email, password);
		
		return new ResponseEntity<Integer>(userId, HttpStatus.OK);
	}
	
	/*******************************************************************************************************
	 * Method:				User list
	 
	 * Description:			To map the request of admin to get the list of wallet users

	 * Created By:			Abhishek Sharma
	 *******************************************************************************************************/
	
	@GetMapping("/admin/{adminId}/userlist")
	
	public ResponseEntity<List<String>> getUserList(@PathVariable("adminId") Integer userId, String userStatus)
	{
		List<String> userList = onlineWalletService.getUserList(userId, userStatus);
		
		return new ResponseEntity<List<String>>(userList, HttpStatus.OK);
	}

   	/*******************************************************************************************************
	 * Method:					ChangeUserStatus
	 * Description:				Map the request of Admin to change the
	  							status of user's account to active or inactive
	 
	 * @param userId:			Admin's Id
	  
	 * @param email:			User's   email
	  
	 * @param userStatus:       User's account status that is needed to be changed
	  
	 * @returns ResponseEntity: It will change the user's account status and return that 
	 							to the frontend
	 							
	 * Created By:				Anurag Kumar
	 ********************************************************************************************************/

	@GetMapping("/admin/{adminId}/changestatus")
	
	public ResponseEntity<String> changeUserStatus(@PathVariable("adminId") Integer adminId, String email,
			String userStatus)
	{
		String returnEmail = onlineWalletService.changeUserStatus(adminId, email, userStatus);
		
		return new ResponseEntity<String>(returnEmail, HttpStatus.OK);
	}
}
