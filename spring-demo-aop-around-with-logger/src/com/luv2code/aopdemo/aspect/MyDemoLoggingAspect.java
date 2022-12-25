package com.luv2code.aopdemo.aspect;

import java.util.List;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
	
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	@Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
	public Object aroundGetFortuneAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		// Print method we're advising on
		String method = proceedingJoinPoint.getSignature().toShortString();
		myLogger.info("\n ===>>> Executing @Around on method: " + method);
		
		// Begin timestamp
		long begin = System.currentTimeMillis();
		
		// Execute Method from FortuneService
		Object result = proceedingJoinPoint.proceed();
		
		// End timestamp
		long end = System.currentTimeMillis();
		
		// Compute duration and display it
		long duration = end - begin;
		myLogger.info("\nDuration ===>>>> " + duration / 1000.0 + "seconds");
		
		return result;
	}
	
	@After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
	public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {
		// prints out which advice we're advising on
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("\n ===>>> Executing @After (finally) on method: " + method);
		
	}
	
	@AfterThrowing(pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))", throwing="theExc")
	public void afterThrowingFindAccountsAdvice(JoinPoint joinPoint, Throwable theExc) {
		// prints out which advice we're advising on
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("\n ===>>> Executing @AfterThrowing on method: " + method);
		
		//Log the exception
		myLogger.info("\n ===>>> The exception is: " + theExc);
	}
	
	
	// Add new Advice for @AfterReturning on the findAccounts Methods
	@AfterReturning(pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))", returning="result")
	public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, List<Account> result) {
		// prints out which advice we're advising on
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("\n ===>>> Executing @AfterReturning on method: " + method);
		
		// prints out the results of the method call 
		myLogger.info("\n ===>>> Result is: " + result);
		
		// Post process the data (result) / Modify it
		// And return it back to the calling program
		convertAccountNamesToUppercase(result);
		myLogger.info("\n ===>>> Result After convert: " + result);
	}
	
	private void convertAccountNamesToUppercase(List<Account> result) {
		
		// Loop through the Accounts
		for(Account tempAccount: result) {
			// Get uppercase version of name
			// Update the name of the account
			tempAccount.setName(tempAccount.getName().toUpperCase());
		}	
	}

	@Before("com.luv2code.aopdemo.aspect.PointcutAopExpressions.forDaoPackageNoGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
		myLogger.info("\n====>>> Executing @Before advice on addAccount()");
		
		// Display the method Signature
		MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
		
		myLogger.info("Method :" + methodSig);
		
		// Display Methods Arguments
		// Get Args
		Object[] args = theJoinPoint.getArgs();
		
		// Loop throu args
		for(Object temp : args) {
			myLogger.info(temp.toString());
			
			if(temp instanceof Account) {
				Account theAccount = (Account) temp;
				myLogger.info("account name :" + theAccount.getName());
				myLogger.info("account level :" + theAccount.getLevel());
			}
		}

	}
}
