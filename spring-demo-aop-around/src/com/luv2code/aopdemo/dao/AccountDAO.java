package com.luv2code.aopdemo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Component
public class AccountDAO {
	
	private String name;
	private String serviceCode;
	
	
	// finAccounts() method
	public List<Account> findAccounts(boolean tripWire) {
		
		// Simulate an exception
		if(tripWire) {
			throw new RuntimeException("Run time Exception Simulation for throwing purposes!");
		}
		List<Account> myAccounts = new ArrayList<>();
		
		Account temp1 = new Account("John", "silver");
		Account temp2 = new Account("Madhu", "platinum");
		Account temp3 = new Account("Amely", "titanium");
		
		myAccounts.add(temp1);
		myAccounts.add(temp2);
		myAccounts.add(temp3);
		
		return myAccounts;
	}
	
	public void addAccount(Account account, boolean vipFlag) {
		System.out.println(getClass() + ": DB Work => Adding Account to DB");
	}
	
	public boolean doWork() {
		System.out.println(getClass() + ": DB Work => Doing work to DB on Account");
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	
}
