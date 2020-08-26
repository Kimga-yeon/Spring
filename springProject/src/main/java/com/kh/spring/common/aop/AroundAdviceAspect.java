package com.kh.spring.common.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)   //advice 실행 순서를 지정. 숫자가 낮을수록 먼저 수행
			// @Order 어노테이션이 없으면 우선순위가 가장 낮음
			// @Around가 Aspect의 @Order값이 가장 큰 경우
			// Before advice가 가장 먼저, After Advice가 가장 마지막에 수행됨.

public class AroundAdviceAspect {
	
	private Logger logger = LoggerFactory.getLogger(AroundAdviceAspect.class);
	
	// @Around
	// - 전처리(@Before), 후처리(@After)를 한번에 처리할 수 있는 Advice로 작성할 수 있는 어노테이션
	// ** @Around로 지정된 Advice는 매개변수로 Joinpoint를 가질 수 없음
	// -> JoinPoint 하위 타입인 ProceedingJoinPoint 사용 가능
	// Proceed : 발화, 발하다, 발생하다 / Proceedion : 진행, 처리
	// ProceedingJoinPoint.proceed() 메소드
	// proceed() 메소드 호출 전 : @Before advice에 해당 되는 코드
	// proceed() 메소드 호출 후 : @After advice에 해당 되는 코드
	// 마지막에 proceed()의 반환값을 return 해야함.
	
	
	@Around("CommonPointcut.implPc()")
	public Object aroundLogs(ProceedingJoinPoint pp) throws Throwable {
		// @Before 
		// 클래스명
				String className = pp.getTarget().getClass().getSimpleName();
				
				// 메소드명
				String methodName = pp.getSignature().getName();
				
				
				if(logger.isDebugEnabled()) {
					logger.debug("----------------------------------------------------");
					logger.debug("[start] : " + className + " - " + methodName + "()");
					logger.debug("[Parameter] : " + Arrays.toString(pp.getArgs()));
					// jp.getArgs() : 대상 메소드의 매개변수를 배열로 반환
					
				}
				
				long startMs = System.currentTimeMillis();
				// Sytem.currentTimeMillis() : 기준시간 (한국 OS경우 1970년 1월 1일 오전 9시)으로 부터
				// 							    현재시간(호출 시점)까지의 시간을 ms단위(1/1000초) 단위로 계산한 값을 반환
				
		
		// proceed() 메소드 호출 
		// 타겟으로 삼은 오브젝트의 메소드가 실행되고있는 것 
				// 서비스임플안에 포인트 컷 있는 메소드들이 실행될때가 이 메소드 실행시점
				// 이메소드를 전후로 비포, 에프터
				// 리턴값이 매개변수로 들어옴 
				// pp.proceed () = 타겟 메소드 
		Object obj = pp.proceed();  // 서비스 시작 시 ms값
		
		long endMs = System.currentTimeMillis(); // 서비스 종료시 ms값
		
		
		// @After
		// logger의 레벨이 debug레벨인지를 검사하여 debug 레벨의 log만 출력 가능하게 함.
				// -> 프로젝트 성능 저하를 막음
				if(logger.isDebugEnabled()) {
					logger.debug("[Running Time : ] "+ (endMs - startMs) + "ms" );
					logger.debug("[End] : " + className + " - " + methodName + "()");
								// [End] : MemberService - login()
					logger.debug("---------------------------------------------------------------------------");
				}
		
		// Around Advice의 반환값은
		// Target method의 결과값으로 대체가 됨.
				
		return obj; // ojb 가공없이 리턴 == 기존 target method결과를 그대로 반환함.
		
		// Around Advice는 반환형이 void 인 경우 자동으로 null값을 반환하게 함.
		
		
	}
	
}
