package com.luv2code.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutAopExpressions {

	// Point cut declaration
	@Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..))")
	public void forDaoPackage() {}
	
	// Point cut declaration for getters
	@Pointcut("execution(* com.luv2code.aopdemo.dao.*.get*(..))")
	public void getter() {}
	
	// Point cut declaration for setters
	@Pointcut("execution(* com.luv2code.aopdemo.dao.*.set*(..))")
	public void setter() {}
	
	// Point cut: include package ... exclude getters/setters
	@Pointcut("forDaoPackage() && ! (getter() || setter())")
	public void forDaoPackageNoGetterSetter() {}
	
}
