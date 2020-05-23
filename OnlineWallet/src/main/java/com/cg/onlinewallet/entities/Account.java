package com.cg.onlinewallet.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "userAccount")
public class Account implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
	private Integer accountID;
	@Column(name = "balance", precision = 2)
	private Double accountBalance;

	public enum status {
		active, non_active
	};

	@Enumerated(EnumType.STRING)
	@Column(name = "user_status")
	private status userStatus = status.non_active;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Transactions> transactionList;

	public Integer getAccountID() {
		return accountID;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Account(Double accountBalance, List<Transactions> transactionList, status userStatus) {
		super();

		this.accountBalance = accountBalance;
		this.transactionList = transactionList;
		this.userStatus = userStatus;
	}

	public status getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(status userStatus) {
		this.userStatus = userStatus;
	}

	public List<Transactions> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transactions> transactionList) {
		this.transactionList = transactionList;
	}

	public Account() {
		// TODO Auto-generated constructor stub
	}

}
