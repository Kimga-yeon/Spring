package com.kh.spring.common.aop;

import org.aspectj.lang.annotation.Pointcut;

// 공통적으로 사용될 Pointcut을 모아두는 클래스 
public class CommonPointcut {
	
	@Pointcut("execution (* com.kh.spring..*Controller.*(..))")
	public void ctrlPc() {}
	
	@Pointcut("execution(* com.kh.spring..*Impl.*(..))")
	public void implPc() {}
	

}
