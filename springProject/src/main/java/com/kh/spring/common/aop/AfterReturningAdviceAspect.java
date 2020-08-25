package com.kh.spring.common.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kh.spring.member.model.vo.Member;

@Aspect
@Component
public class AfterReturningAdviceAspect {
	private Logger logger = LoggerFactory.getLogger(AfterReturningAdviceAspect.class);
	
	// @AfterReturning 
	// 타겟 메소드가 정상 실행을 마친 후에 호출되는 advice로
	// 리턴값을 참조할 수 있음.
	
	@AfterReturning(pointcut = "CommonPointcut.implPc()", returning = "returnObj")
	// returning = "속성값" -> 매개변수 중 리턴값을 저장할 매개변수명을 속성값에 작성
	public void returnValueLog(JoinPoint jp, Object returnObj) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("[Return Value] : " + returnObj.toString());
		}
	}
	
	
	// 로그인 성공 시 
	// 접속 IP와 회원 ID를 log에 출력 
	@AfterReturning(pointcut="execution(* com.kh.spring..MemberServiceImpl.login(*))", 
					returning="returnObj")
	public void loginLog(JoinPoint jp, Object returnObj) {
		if(returnObj instanceof Member) {
			Member loginMember = (Member)returnObj;
			
			String loginMsg = "login ID : "+ loginMember.getMemberId();
			
			if(loginMember.getMemberGrade().equals("A")) {
				// 현재 로그인한 회원의 등급이 관리자인 경우
				loginMsg += " (관리자)";
			}else {
				loginMsg += " (일반회원)";
			}
		
			// 접속자 IP 얻어오기
			HttpServletRequest request
				= ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			
			String ip = request.getRemoteAddr();
			
			if(logger.isDebugEnabled()) {
				logger.debug(loginMsg);
				logger.debug("[IP Address] : " + ip);
				// localserver 컴퓨터에서 접속한 경우 0:0:0:0:0:0:0:1 형식으로 출력됨
				
				// ipconfig에서 조회한 IPv4 주소
				// ex) 192.168.10.78:8060/spring
			}
		}
		
		
	}
}
