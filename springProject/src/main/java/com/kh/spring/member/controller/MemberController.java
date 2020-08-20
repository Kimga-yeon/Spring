package com.kh.spring.member.controller;

import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;


//@Component // 객체(Bean) 등록을 해야할 클래스 파일을 의미
		   // xml 방식의 <bean> 태그와 같은 의미
		   
@SessionAttributes({"loginMember"})
// Model에 추가된 key값 중 "loginMember" 라는 key를 가지는 데이터를 Session scope로 변경해라 
// + Model.getAttribute("loginMember") 
// 		-> Session 에서 "loginMember"라는 key를 가지는 속성의 값을 얻어와라

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
		   public String loginAction(Member member,Model model, RedirectAttributes rdAttr,
				   					 String saveId, HttpServletResponse response) {
			
			// saveId : checkbox가 체크가 되어진 경우 별도의 value 속성이 없으면 "on"이라는 문자열이 전달됨.
			// 			checkbox가 체크가 안 되어진 경우는 null
			
			
			// Model : 전달하고자 하는 데이터를 맵형식(K,V) 형태로 담아 전달하는 객체
			// 기본적으로 scope는 request임.
			// 만약 scope를 session으로 변경하고자 하는 경우 
			// Controller 클래스명 위에
			// @SessionAttributes() 어노테이션을 작성해야 함.
			
		     // System.out.println(member.getMemberId() + " / " + member.getMemberPwd());
		    	  
		    	  Member loginMember = memberService.login(member);
		    	  System.out.println(loginMember+"dd");
		    	  
		    	  
		    	  if(loginMember == null) {
		    		  rdAttr.addFlashAttribute("status", "error");
		    		  rdAttr.addFlashAttribute("msg", "로그인 실패");
		    		  rdAttr.addFlashAttribute("text", "아이디 또는 비밀번호를 확인해주세요.");
		    	  }else {
		    		  model.addAttribute("loginMember", loginMember);
		    		  // request scope로 "loginMember"라는 key를 추가하고 
		    		  // value로 loginMember 객체를 지정
		    		  
		    		  // 쿠키 객체 생성
		    		  Cookie cookie = new Cookie("saveId", member.getMemberId());
		    		  
		    		  if(saveId != null) { // 아이디 저장이 체크된 경우
		    			  // 쿠키 생성 
		    			  // response를 사용하여 쿠키 생성 
		    			  cookie.setMaxAge(60 * 60 * 24 * 7); // 쿠키를 1주일 유지
		    			  
		    		  }else {
		    			  cookie.setMaxAge(0); // 쿠키 삭제
		    		  }
		    		  response.addCookie(cookie);
		    	  }
		    	  
		    	  System.out.println(loginMember);
		    	  
		      
		      
		      return "redirect:/"; 
		   }
		
		// 로그아웃
		@RequestMapping("logout")
		public String logout(SessionStatus status) {
			// SessionStatus : 세션 상태를 관리할 수 있는 객체
			// @SessionAttribute()를 사용한 경우
			// Session을 만료 시키기 위해서는
			// SessionStatus.setComplate() 메소드를 사용해야 함.
			
			// @SessionAttribute() 로 추가된 session 은 
			// removeAttribute()를 이용한 제거가 불가능함. 
			
			status.setComplete();
			return "redirect:/"; 
			
		}
		
		// 회원 가입 페이지 이동 
		@RequestMapping(value="signUp",method=RequestMethod.GET)
		public String signUpView() {
			return "member/signUpView";
			
		}
		
		// 회원가입
		@RequestMapping(value="signUpAction", method=RequestMethod.POST)
		public String signUpAction(Member signUpMember, RedirectAttributes rdAttr) {
			// RedirectAttributes : 리다이렉트 시 데이터를 전달할 수 있는 객체 
			// addAttribute() : 리다이렉트하는 url에 쿼리스트링으로 값 전달 
			// addFlashAttribute() : 
			
			// 응답 전 scope : request
			// redirect scope : session
			// 응답 페이지 scope : request
			
			System.out.println(signUpMember);
			
			try {
				int result = memberService.signUp(signUpMember);
				
				String status = null;
				String msg = null;
				if(result > 0) {
					status = "success";
					msg = "가입 성공!";
				}else {
					status = "error";
					msg = "가입 실패";
				}
				
				rdAttr.addFlashAttribute("status", status);
				rdAttr.addFlashAttribute("msg",msg);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return "redirect:/";
			
		}
		
		// 아이디 중복 체크
		// @ResponseBody
		// 메소드에서 리턴되는 값을 View Resolver로 전달하지 않고
		// 비동기로 요청된 곳으로 리턴값을 가지고 돌아가게 하는 역할 
		
		// HTTP Response객체의 Body 부분에 리턴값을 담게하여 
		// jsp로 포워드 되지 않고 기존 요청 페이지로 데이터를 전달하게 함.
		
		@ResponseBody
		@RequestMapping("idDupCheck")
		// ("idDupCheck") = get, post 따지지 않음
		public String idDupCheck(String memberId) {
			int result = memberService.idDupCheck(memberId);
			
			return result+"";
		}
		
		
		@RequestMapping("mypage")
		public String myPage() {
			return "member/mypage";
		}
		

		
		@RequestMapping("updateAction")
		public String updateAction(Member upMember, Model model,
								RedirectAttributes rdAttr, 
								HttpServletRequest request) {
			
			// session scope에 있는 로그인 회언 정보를 얻어와
			// id, name, grade 추출 -> upMember에 세팅 
			Member loginMember = (Member)model.getAttribute("loginMember");
			
			upMember.setMemberNo(loginMember.getMemberNo());
			upMember.setMemberEnrollDate(loginMember.getMemberEnrollDate());
			upMember.setMemberId(loginMember.getMemberId());
			upMember.setMemberName(loginMember.getMemberName());
			upMember.setMemberGrade(loginMember.getMemberGrade());
			
			// 회원 정보 수정 Service 호출 
			int result = memberService.updateMember(upMember);
			
			String status = null;
			String msg = null;
			
			if(result > 0 ) {
				status = "success";
				msg = "회원 정보 수정 성공";
				
				// update 완료 후 수정된 회원 정보를 다시 Session에 올림
				 model.addAttribute("loginMember", upMember);
				// @SessionAttributes("loginMember")
						 
			}else {
				status = "error";
				msg = "회원 정보 수정 실패";
			}
			
			rdAttr.addFlashAttribute("status", status);
			rdAttr.addFlashAttribute("msg", msg);
			
			// select -> forward
			// select를 하는 경우 DB에서 조회한 데이터를 이용해 응답 화면을 만들어야 함.
			// -> 응답화면을 쉽게 만들기 위해 사용하는 게 JSP 
			// -> JSP로 요청을 위임하기 위해서는 forward를 해야함.
			// 단, 로그인 같이 조회 데이터를 Session에 세팅하는 경우는 redirect를 진행함.
			
			// DML -> redirect
			// DML 수행 시 DB데이터가 변환되고 이를 다시 select해서 보여주는 상황이 많음
			// -> select 결과를 보여주는 작업은 forward를 통해서 진행 해야함. 
			// -> forward를 진행하는 주소를 다시 요청함(redirect)
			
			return "redirect:" + request.getHeader("referer");
			// 이전 요청주소
			
		}
		
		
		
		
		
		/* 스프링에서 예외를 처리하는 방법
		 * 1) try-catch, throws를 이용한 예외 처리 방법
		 *  -> 메소드 레벨 
		 *  
		 * 2) @ExceptionHandler -> 클래스(컨트롤러) 레벨 
		 * 	  (해당 컨트롤러에서 발생하는 모든 예외를 처리하는 메소드)
		 * 
		 * 3) @ControllerAdvice -> 전역 레벨 
		 * 	  (해당 프로젝트에 발생하는 모든 예외를 처리하는 하나의 클래스)
		 * 
		 * */
	
		// DB 관련 예외가 발생할 경우 처리하는 메소드
		@ExceptionHandler({SQLException.class, BadSqlGrammarException.class}) 
		public String dbException(Exception e, Model model) {
			e.printStackTrace();
			
			model.addAttribute("errorMsg", "데이터베이스 관련 예외 발생");
			
			return "common/errorPage";
			
		}
		
		// 비밀번호 변경 화면 이동 
		@RequestMapping("changePwd")
		public String changePwd() {
			return "member/changePwd";
		}
		
		
		// 비밀번호 변경
		@RequestMapping("updatePwd")
		public String updatePwd(String memberPwd, String newPwd1, Model model,
						RedirectAttributes rdAttr) {
			
			// Session에서 로그인한 회원 정보를 얻어옴
			Member loginMember = (Member)model.getAttribute("loginMember");
								// Object타입이 되서 형변환 필요
			
			// loginMember 객체에 입력받은 현재 비밀번호 세팅
			loginMember.setMemberPwd(memberPwd);
			
			// 비밀번호 변경 Service 호출
			int result = memberService.updatePwd(loginMember, newPwd1);
			
			String status = null;
			String msg = null;
			String text = null;
			if(result > 0) {
				status = "success";
				msg = "비밀번호 변경 성공";
				
			}else {
				status = "error";
				msg = "비밀번호 변경 실패";
				text = "현재 비밀번호를 확인해 주세요";
			}
		
			rdAttr.addFlashAttribute("status", status);
			rdAttr.addFlashAttribute("msg", msg);
			rdAttr.addFlashAttribute("text", text);
			
			return "redirect:changePwd";
		}
		
		// 회원탈퇴 화면 이동
		@RequestMapping("secession")
		public String SessionAttributes(){
			return "member/secession";
		}
		
		
		// 회원 탈퇴
		@RequestMapping("deleteMember")
		public String deleteMember(int memberNo, Model model,String memberPwd,
									RedirectAttributes rdAttr) {
			
			Member loginMember = (Member)model.getAttribute("loginMember");
			int result = memberService.deleteMember(loginMember, memberPwd);
			loginMember.setMemberPwd(memberPwd);
			

			String status = null;
			String msg = null;
			String text = null;
			if(result > 0) {
				status = "success";
				msg = "회원 탈퇴 성공";
				
			}else {
				status = "error";
				msg = "회원 탈퇴 실패";
				text = "현재 비밀번호를 확인해 주세요";
			}
		
			rdAttr.addFlashAttribute("status", status);
			rdAttr.addFlashAttribute("msg", msg);
			rdAttr.addFlashAttribute("text", text);
			
			return "redirect:deletePwd";


		}
		
		
		
		@ExceptionHandler(Exception.class) 
		public String etcException(Exception e, Model model, HttpServletRequest request) {
			e.printStackTrace();
			
			System.out.println(request.getRequestURI()); // 예외가 발생한 요청 주소
			System.out.println(e.getStackTrace()[0]); // 예외 내용의 첫 줄 
			System.out.println(e.getMessage()); // 예외 관련 메세지 출력
			
			model.addAttribute("errorMsg", "기타 예외 발생");
			return "common/errorPage";
			
		}
}
