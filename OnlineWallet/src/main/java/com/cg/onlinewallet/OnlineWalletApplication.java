package com.cg.onlinewallet;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cg.onlinewallet.entities.*;
import com.cg.onlinewallet.entities.Account.status;
import com.cg.onlinewallet.entities.User.type;

@Transactional
@SpringBootApplication
public class OnlineWalletApplication implements CommandLineRunner {
	@Autowired
	EntityManager em;

	public static void main(String[] args) {
		SpringApplication.run(OnlineWalletApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		List<Transactions> list1 = new ArrayList<Transactions>();
		List<Transactions> list2 = new ArrayList<Transactions>();

		Account wa1 = new Account(1000.00, list1, status.active);
		Account wa2 = new Account(1000.00, list2, status.non_active);
		Account wa3 = new Account(0.0, new ArrayList<Transactions>(), status.active);
		Account wa4 = new Account(0.0, new ArrayList<Transactions>(), status.non_active);
		Account wa5 = new Account(0.0, new ArrayList<Transactions>(), status.active);

		User wu1 = new User("abc", "abc@123", "1234567890", "abc123@gmail.com",type.user, wa1);
		User wu2 = new User("xyz", "xyz@123", "1023456789", "xyz123@gmail.com",type.user, wa2);
		User wu3 = new User("Anurag", "Anurag@123", "1234567890", "Anurag@capg.com", type.admin, wa3);
		User wu4 = new User("User", "User@123", "4567890321", "User@gmail.com", type.user, wa4);
		User wu5 = new User("User0", "User0@123", "1020345682", "User0gmail.com", type.user, wa5);

		em.persist(wu1);
		em.persist(wu2);
		em.persist(wu3);
		em.persist(wu4);
		em.persist(wu5);

		em.persist(wa1);
		em.persist(wa2);
		em.persist(wa3);
		em.persist(wa4);
		em.persist(wa5);

	}
}
