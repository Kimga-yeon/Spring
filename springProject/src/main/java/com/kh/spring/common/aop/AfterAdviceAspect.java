package com.kh.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterAdviceAspect {
	
	// ServiceImpl 호출 되기 전 호출되는 메소드명, 파라미터log 찍어보기
	
	// Logger 객체 생성
	// Logger : 로그를 작성할 수 있게 해주는 객체 
	private Logger logger = LoggerFactory.getLogger(AfterAdviceAspect.class);
	
	// @After("CommonPointcut.implPc()")
	public void afterLog(JoinPoint jp) {
		// JoinPoint : AOP의 부가기능을 지닌 코드가 적용될 수 있는 모든 시점(지점, 관점, 메소드)
		// JoinPoint Interface
		// -> 부가기능이 적용되는 대상 객체, 메소드, 파라미터 정보등을 얻을 수 있는 메소드를 제공하는 인터페이스
		// * 어드바이스 메소드의 첫 번째 매개변수로 선언되어야 함
		
		// jp.getTarget() : 대상 객체 정보 반환 
		String className = jp.getTarget().getClass().getSimpleName();
		// 대상 객체의 간단한 클래스명(패키지명)
		
		// jp.getSignature() : 대상 메소드 정보 반환 
		String methodName = jp.getSignature().getName(); // 메소드명 반환 
		

		// logger의 레벨이 debug레벨인지를 검사하여 debug 레벨의 log만 출력 가능하게 함.
		// -> 프로젝트 성능 저하를 막음
		if(logger.isDebugEnabled()) {
			logger.debug("[End] : " + className + " - " + methodName + "()");
						// [End] : MemberService - login()
			logger.debug("---------------------------------------------------------------------------");
			
		}
		
		
	}
}
