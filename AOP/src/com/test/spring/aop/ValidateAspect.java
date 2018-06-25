package com.test.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 使用 @Order 注解来指定切面的优先级
 */
@Aspect
@Component
@Order(1)
public class ValidateAspect {

	@Before("execution(* com.test.spring.aop.*.*(..))")
	public void validate(JoinPoint joinPoint) {
		System.out.println("validate-->" + joinPoint.getSignature().getName());
	}
}
