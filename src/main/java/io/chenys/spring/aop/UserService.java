package io.chenys.spring.aop;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements ServiceInterface{
	
	public void add() {
		System.out.println("UserService add user ...");
	}
}
