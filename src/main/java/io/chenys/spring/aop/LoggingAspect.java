package io.chenys.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
 
	@Before("execution(* io.chenys.spring.aop.*.*(..))")
	public void logBefore(JoinPoint point) {
		System.out.println("Before \""+point.getSignature().getDeclaringTypeName()
				+ "." +point.getSignature().getName() + "\"");
	}
	
	@After("log()")
	public void logAfter(JoinPoint point) {
		System.out.println("After \""+point.getSignature().getDeclaringTypeName()
				+ "." +point.getSignature().getName() + "\"");
	}
	
	@Pointcut("execution(* io.chenys.spring.aop.*.*(..))")
	public void log() { }
 
}