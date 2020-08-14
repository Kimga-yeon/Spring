package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.vo.Member;

@Service // Serrvice 레이어, 비즈니스 로직 처리를 하는 클래스임을 명시 + Bean 등록 
public class MemberServiceImpl implements MemberService{

	@Autowired // 등록된 MemeberDAO bean을 이용해 의존성 주입(DI) 진행 
	private MemberDAO memberDAO;
	
	@Override
	public Member login(Member member) throws Exception {

		System.out.println("service의 login() 메소드 호출됨");

		
		
		return memberDAO.login(member);
	}
	
}
