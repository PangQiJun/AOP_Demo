package com.test.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Calculator calculator = (Calculator) context.getBean("calculator");
		
		int result = calculator.div(10, 2);
		System.out.println(result);
	}
}
