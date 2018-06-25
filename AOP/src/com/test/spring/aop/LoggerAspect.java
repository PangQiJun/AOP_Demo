package com.test.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 */
@Aspect
@Component
@Order(0)
public class LoggerAspect {

	/**
	 * 重用切入点表达式，改方法一般情况下都是空的
	 */
	@Pointcut("execution(* com.test.spring.aop.*.*(..))")
	public void loggingOperation() {}
	
	/**
	 * 前置通知：再目标分方法开始之前执行
	 */
	@Before("loggingOperation()")
	public void logBefore(JoinPoint joinPoint) {
		// 方法名
		String methodName = joinPoint.getSignature().getName();
		// 参数
		Object[] args = joinPoint.getArgs();
		
		System.out.println("The method " + methodName + "() begins with " + Arrays.asList(args));
	}
	
	/**
	 * 后置通知：目标方法结束之后执行，包括方法抛出异常，不能获取目标方法的结果
	 */
	@After("loggingOperation()")
	public void logAfter(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method " + methodName + "() ends");
	}
	
	/**
	 * 返回通知：在目标方法正常返回之后执行
	 */
	@AfterReturning(pointcut = "loggingOperation()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method " + methodName + "() ends with " + result);
	}
	
	/**
	 * 异常通知：目标方法抛出异常时执行
	 * 如果只对某种特殊的异常类型感兴趣, 可以将参数声明为其他异常的参数类型.  
	 * 然后通知就只在抛出这个类型及其子类的异常时才被执行.
	 */
	@AfterThrowing(pointcut = "loggingOperation()", throwing = "e") 
	public void logAfterThrowing(JoinPoint joinPoint, ArithmeticException e) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("An excption " + e + " has been throwing in " + methodName + "()");
	}

	/**
	 * 环绕通知
	 * 注意: 环绕通知的方法需要返回目标方法执行之后的结果, 
	 * 即调用 joinPoint.proceed(); 的返回值, 否则会出现空指针异常
	 */
	@Around("loggingOperation()")
	public Object logAround(ProceedingJoinPoint point) throws Throwable {
		Object result = null;
		String methodName = point.getSignature().getName();
		Object[] args = point.getArgs();
		
		try {
			// 前置通知
			System.out.println("The method " + methodName + "() begins with " + Arrays.asList(args));
			result = point.proceed();
			// 返回通知
			System.out.println("The method " + methodName + "() ends with " + result);
		} catch (Exception e) {
			// 异常通知
			System.out.println("An excption " + e + " has been throwing in " + methodName + "()");
			throw e;
		}
		// 后置通知
		System.out.println("The method " + methodName + "() ends");
		return result;
	}
	
}
