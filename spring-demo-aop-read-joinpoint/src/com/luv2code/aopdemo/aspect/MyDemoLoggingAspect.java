package com.luv2code.aopdemo.aspect;

import org.aspectj.lang.JoinPoint;
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
