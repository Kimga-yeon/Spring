package com.kh.spring.common.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kh.spring.board.model.service.BoardService;

@Component // 일정 또는 지정 시간마다 스프링 컨테이너가 코드를 제어할 수 있게 bean등록
public class SchedulingTest {
	
	// @Scheduled
	// Spring에서 제공하는 스케줄러 (시간에 따른 특정 작업 순서를 지정하는 방법) 
	
	// servlet-context.xml -> NameSpaces 탭
	// -> task 체크 후 저장 
	// -> source 탭 이동 -> <task:annotation-driven/>작성
	
	private Logger logger = LoggerFactory.getLogger(SchedulingTest.class);
	
	
	/* @Scheduled 속성
     *  - fixedDelay : 이전 작업이 끝난 시점으로 부터 고정된 시간(ms)을 설정.
     *  - fixedRate : 이전 작업이 수행되기 시작한 시점으로 부터 고정된 시간(ms)을 설정.
     * 
     * * cron 속성 : UNIX계열 잡 스케쥴러 표현식으로 작성 - cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT) 
     * ex) 2019년 9월 16일 월요일 10시 30분 20초 cron="20 30 10 16 9 2" // 연도 생략 가능
     * 
     * - 특수문자 * : 모든 수. 
     * - : 두 수 사이의 값. ex) 10-15 -> 10이상 15이하 
     * , : 특정 값 지정. ex) 3,4,7 -> 3,4,7 지정 
     * / : 값의 증가. ex) 0/5 -> 0부터 시작하여 5마다 
     * ? : 특별한 값이 없음. (월, 요일만 해당) 
     * L : 마지막. (월, 요일만 해당)
     * 
     * * 주의사항 - @Scheduled 어노테이션은 매개변수가 없는 메소드에만 적용 가능.
     * 
     */
	
	//@Autowired
	//private BoardService boardService;
	
	// @Scheduled(fixedDelay = 10000) //이전 동작 완료 후 10초 뒤에 다시 수행
	//@Scheduled(cron = "0 * * * * *") // 매 분이 시작될 때 마다
	public void test() {
		logger.debug("--------------스케쥴링 테스트-------------");
		//bpardService.deleteBoard(boardNo);
	}
	

}
