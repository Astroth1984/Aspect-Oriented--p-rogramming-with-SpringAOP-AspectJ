package com.luv2code.aopdemo;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.luv2code.aopdemo.dao.AccountDAO;
import com.luv2code.aopdemo.dao.MembershipDAO;

public class AfterFinallyDemoApp {

	public static void main(String[] args) {
		
		// Read Spring Config java Class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class);
		
		// Get the bean from java container
		AccountDAO accountDAO = context.getBean("accountDAO", AccountDAO.class);
		
		List<Account> accounts = null;
		
		try {
			// Add a boolean flag to simulate exception
			boolean tripWire = false;
			accounts = accountDAO.findAccounts(tripWire);
			
		} catch(Exception exc) {
			System.out.println("\n\n Programm ... Caught exception: " + exc);
		}
				
		
		System.out.println("\n\n Main Program: AfterThrowingDemoApp");
		System.out.println("---");
		
		System.out.println(accounts);
		
		System.out.println("\n");
		
		// Close the context
		context.close();
	}

}
