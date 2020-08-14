package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;


//@Component // 객체(Bean) 등록을 해야할 클래스 파일을 의미
		   // xml 방식의 <bean> 태그와 같은 의미
		   
@SessionAttributes({"loginMember"})
// Model에 추가된 key값 중 "loginMember" 라는 key를 가지는 데이터를 Session scope로 변경해라 
@Controller // controller라는 의미 명시 (구체화된 어노테이션)
@RequestMapping("/member/*") // value, method가 명시되지 않은 경우 
						     // 작성된 값은 value로 인식, method는 자동 판별 
public class MemberController {
	
	// 등록된 bean 중 
	// @Autowired 구문 밑에 있는 자료형과 일치하거나 
	// 해당 자료형을 상속/구현한 클래스가 있을 경우 
	// 자동으로 연결하여 의존성 주입(DI)를 진행함. 
	
	@Autowired
	private MemberService memberService;
	
	// 로그인 화면 전환 메소드 
	@RequestMapping("login") //클래스 레벨 이후 나머지 값 
	public String loginView() {
		return "member/login";
		
	}
	
	// 로그인 동작 
	
	// 1. 기존 Servlet 방식 
	// HttpServletRequest 객체를 Servlet이 아닌 파일인데 사용할 수 있는 이유
	// Controller 메소드 매개변수에 원하는 타입의 매개변수를 작성하면 
	// 알맞은 객체 또는 새로운 객체를 생성해서 의존 주입을 함. 
	
	
	/*
	 * @RequestMapping("loginAction") public String loginAction(HttpServletRequest
	 * request) { String memberId = request.getParameter("memberId"); String
	 * memberPwd = request.getParameter("memberPwd");
	 * 
	 * System.out.println(memberId + "/" + memberPwd);
	 * 
	 * return null; }
	 */
	
	
	// 2. @RequestParam 어노테이션 사용 
	// @RequestParam : 파라미터를 전달 받는 역할을 하는 어노테이션 
	/*
	 * [사용법 1] : 속성 없이 사용하는 방법 
	 * @RequestParam("input태그 name 속성값") 자료형 변수명 
	 * 
	 * -> 만약 input 태그에 값(value)이 비어있다면 ""(빈 문자열)이 넘어옴
	 * 
	 *  단, 파라미터 중 일치하는 name 속성값이 없을 경우 
	 *  HTTP Status 400 - Bad Request 발생 
	 *  
	 *  [사용법 2] : 속성 추가 
	 *  value : 전달 받은 input 태그의 name 속성 값 
	 *  required : 파라미터 필수 여부 지정(true(기본값)/false) 
	 *  defaultValue : 
	 *  		(선행 조건 : required 속성 값이 false)
	 *  		전달받은 파라미터가 존재하지 않을 때 기본값을 지정 
	 *  
	 *  
	 *  ** 파라미터의 자료형은 기본적으로 String 타입이지만 
	 *     Spring에서 지원해주는 자동 형변환기가 존재하고 있어
	 *     전달되는 파라미터를 매개변수에 지정된 자료형으로 자동 형변환 해줌 
	 *     -> 데이터 타입만 일치한다면 원하는 자료형으로 변환할 수 있다.
	 *  
	 * */
	/*@RequestMapping("loginAction")
	public String loginAction( @RequestParam("memberId") String memberId,
								@RequestParam("memberPwd") String memberPwd,
								@RequestParam(value="test",required=false, defaultValue="10") int test
								) {
		
		System.out.println(memberId + "/" + memberPwd + "/" + test);
		
		System.out.println(test + 100); //10100
		return "redirect:/"; //메인페이지 재요청 
	}
	*/
	
	
	   // 3. @RequestParam 생략
	   // 생략 조건  : 매개변수명이 파라미터의 name 속성값과 같아야 함.
	  /* @RequestMapping("loginAction")
	   public String loginAction(String memberId, String memberPwd) {
	      
	      System.out.println(memberId + " / " + memberPwd);
	      
	      return "redirect:/";
	   }
	   */
	
		// 4. @ModelAttribute 어노테이션 사용
		// 요청 페이지에서 전달된 파라미터가 다수 존재하는 경우 
		// 이를 하나의 객체 타입으로 전달 받게 하는 어노테이션 
	
		// (주의 사항) 전달 받을 객체 타입(VO) 내부에 
		// - 반드시 기본생성자 , setter가 생성되어 있어야 함. 
		// - 멤버 변수명이 name 속성값과 같아야 함. 
	
		// --> 커맨드 객체 
		/*@RequestMapping("loginAction")
		   public String loginAction(@ModelAttribute Member member) {
		      System.out.println(member.getMemberId() + " / " + member.getMemberPwd());

//		      Member member = new Member();
//		      member.setMemberId(memberId);
//		      member.setMemberPwd(memberPwd);
		      
		      return "redirect:/";
		   }*/
	
	
	
		// 5. @ModelAttribute 어노테이션 생략 
		// 별도의 생략 조건 없이 어노테이션이 생략되도
		// 스프링 알아서 커맨드 객체로 매핑 시킴. 
	
		// 어노테이션은 필요에 따라 작성하면 되지만
		// 충돌 또는 가독성을 생각하여 생략을 할지 말지를 잘 정해야 함.
	
		@RequestMapping("loginAction")
		   public String loginAction(Member member,Model model) {
			
			// Model : 전달하고자 하는 데이터를 맵형식(K,V) 형태로 담아 전달하는 객체
			// 기본적으로 scope는 request임.
			// 만약 scope를 session으로 변경하고자 하는 경우 
			// Controller 클래스명 위에
			// @SessionAttributes() 어노테이션을 작성해야 함.
			
		      System.out.println(member.getMemberId() + " / " + member.getMemberPwd());

		      try {
		    	  
		    	  Member loginMember = memberService.login(member);
		    	  
		    	  model.addAttribute("loginMember" ,loginMember);
		    	  // request scope로 "loginMember"라는 key를 추가하고 
		    	  // value로 loginMember 객체를 지정
		    	  
		    	  System.out.println(loginMember);
		    	  
		      } catch(Exception e) {
		    	  e.printStackTrace();
		      }
		      
		      
		      return "redirect:/"; 
		   }
		
		// 로그아웃
		@RequestMapping("logout")
		public String logout(SessionStatus status) {
			// SessionStatus : 세션 상태를 관리할 수 있는 객체
			// @SessionAttribute()를 사용한 경우
			// Session을 만료 시키기 위해서는
			// SessionStatus.setComplate() 메소드를 사용해야 함.
			status.setComplete();
			return "redirect:/"; 
			
		
		}
		
		
	
	
}
