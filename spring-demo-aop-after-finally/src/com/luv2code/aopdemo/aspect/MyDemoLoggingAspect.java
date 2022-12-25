package com.luv2code.aopdemo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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
	
	@After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
	public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {
		// prints out which advice we're advising on
		String method = joinPoint.getSignature().toShortString();
		System.out.println("\n ===>>> Executing @After (finally) on method: " + method);
		
	}
	
	@AfterThrowing(pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))", throwing="theExc")
	public void afterThrowingFindAccountsAdvice(JoinPoint joinPoint, Throwable theExc) {
		// prints out which advice we're advising on
		String method = joinPoint.getSignature().toShortString();
		System.out.println("\n ===>>> Executing @AfterThrowing on method: " + method);
		
		//Log the exception
		System.out.println("\n ===>>> The exception is: " + theExc);
	}
	
	
	// Add new Advice for @AfterReturning on the findAccounts Methods
	@AfterReturning(pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))", returning="result")
	public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, List<Account> result) {
		// prints out which advice we're advising on
		String method = joinPoint.getSignature().toShortString();
		System.out.println("\n ===>>> Executing @AfterReturning on method: " + method);
		
		// prints out the results of the method call 
		System.out.println("\n ===>>> Result is: " + result);
		
		// Post process the data (result) / Modify it
		// And return it back to the calling program
		convertAccountNamesToUppercase(result);
		System.out.println("\n ===>>> Result After convert: " + result);
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
		System.out.println("\n====>>> Executing @Before advice on addAccount()");
		
		// Display the method Signature
		MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
		
		System.out.println("Method :" + methodSig);
		
		// Display Methods Arguments
		// Get Args
		Object[] args = theJoinPoint.getArgs();
		
		// Loop throu args
		for(Object temp : args) {
			System.out.println(temp);
			
			if(temp instanceof Account) {
				Account theAccount = (Account) temp;
				System.out.println("account name :" + theAccount.getName());
				System.out.println("account level :" + theAccount.getLevel());
			}
		}

	}
}
