package io.chenys.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOP {
	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("/springAopDemo.xml");
		ServiceInterface userService = (ServiceInterface)appContext.getBean("userService");
		userService.add();
	}
}
