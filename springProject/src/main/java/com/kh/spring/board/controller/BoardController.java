package com.kh.spring.board.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.member.model.vo.Member;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// @PathVariable:URL 경로에 있는 값을 파라미터로 사용할 수 있게 하는 어노테이션
	@RequestMapping("list/{type}")
	public String boardList(@PathVariable int type,
					@RequestParam(value="cp", required = false, defaultValue = "1")int cp,
								Model model) {
		
		System.out.println(type);
		
		// 1) Pagination 처리에 사용될 클래스 PageInfo 작성 후 bean 등록
		
		// 2) PageInfo 초기세팅 (한 화면에 몇개)
			//  -> 전체 게시글 수 조회 + 현재 페이지(current Page)
			//  --> start, end, max 계산 
		
		PageInfo pInfo = boardService.pagination(type, cp);
		
		System.out.println(pInfo);
		
		
		// 3) 게시글 목록 조회
		List<Board> boardList = boardService.selectList(pInfo);
		
		/*
		for(Board b : boardList) {
			System.out.println(b);
		}
		*/
		
		// 4) 썸네일 목록 조회
		if(!boardList.isEmpty()) { // 게시글 목록 조회 결과가 있을 경우 
			// 마이바티스 List 조회 시 조회 결과가 없어도 비어 있는 List[] 가 반환이 됨.
			List<Attachment> thList = boardService.selectThumbnailList(boardList);
			
			for(Attachment at : thList) {
				System.out.println(at);
			}
			
			model.addAttribute("thList",thList);
			
		}
			// 썸네일 목록을 응답페이지 전달 
			model.addAttribute("boardList",boardList);
			model.addAttribute("pInfo",pInfo);
			
		
		return "board/boardList";
	}
	
	// 게시글 상세조회
	// spring/board/1/500?cp=3
	@RequestMapping("{type}/{boardNo}")
	public String boardView(@PathVariable int type, @PathVariable int boardNo,
						Model model,RedirectAttributes rdAttr, HttpServletRequest request) {
		System.out.println("type :" + type);
		System.out.println("boardNo :" + boardNo);
		
		// 게시글 하나 조회 Service 호출
		Board board = boardService.selectBoard(boardNo);
		
		System.out.println(board);
		
		String url = null;
		if(board != null) { // 게시글 조회 성공 시
			
			// DB에서 해당 글에 작성된 이미지파일 모두 조회하기 
			List<Attachment> files = boardService.selectFiles(boardNo);
			
			if(!files.isEmpty()) {
				model.addAttribute("files",files);
				
			}
			
			
			model.addAttribute("board",board);
			url = "board/boardView";
			
		}else {
			// "존재하지 않는 게시글입니다 " 출력 후 이전 요청 주소로 리다이렉트
			rdAttr.addFlashAttribute("status","error");
			rdAttr.addFlashAttribute("msg","해당 게시글이 존재하지 않습니다.");
			//url = "redirect:" + request.getHeader("referer");
			
			url = "redirect:/board/list/"+ type;

		}
		return url;
	}
	
	// 게시글 등록 화면 이동
	@RequestMapping("{type}/insert")
	public String insertView() {
		return "board/boardInsert";
	}
	
	// 게시글 등록
	@RequestMapping(value="{type}/insertAction",method=RequestMethod.POST)
	public String insertAction(@PathVariable int type, Board board,
							   Model model, RedirectAttributes rdAttr,
							   @RequestParam(value="thumbnail", required=false) MultipartFile thumbnail,
							   @RequestParam(value="images", required=false) List<MultipartFile> images,
							   HttpServletRequest request) {
											// 업로드 되는 이미지 파일이 필수 파라미터가 아니게 설정 
		// 게시글 등록에 필요한 내용
		// 카테고리 , 제목, 내용, 게시판 타입, 작성자(회원번호)
		
		// Session에서 회원 정보 얻어오기(@SessionAttributes 확인)
		Member loginMember = (Member)model.getAttribute("loginMember");
		
		// 타입, 회원번호 board에 세팅
		board.setBoardType(type);
		board.setBoardWriter(loginMember.getMemberNo()+"");
		
		
		// 파일 업로드 , 게시글 입력값 확인
		System.out.println("썸네일 : " + thumbnail.getOriginalFilename());
		for(int i=0; i<images.size(); i++) {
			System.out.println("images[" + i + "] : " + images.get(i).getOriginalFilename());
		}
		
		System.out.println(board);
		
		
		// 썸네일 이미지 정보를 images 리스트 제일 앞에 추가 
		images.add(0, thumbnail);
		
		/// 파일을 저장할 서버 컴퓨터의 로컬 경로
		String savePath = request.getSession().getServletContext().getRealPath("resources/uploadImages");
		
		int result  = boardService.insertBoard(board, images, savePath);
		
		String status;
		String msg;
		String path;
		if (result > 0) {
			status = "success";
			msg = "게시글 삽입 성공";
			path = "redirect:"+board.getBoardNo()+"?cp=1";
		} else {
			status = "error";
			msg = "게시글 삽입 실패";
			path = "insert";
		}
		rdAttr.addFlashAttribute("status",status);
		rdAttr.addFlashAttribute("msg",msg);
		
		return path;
	}
	

	// 게시글 삭제
	// spring/board/1/515/delete
	@GetMapping("{type}/{boardNo}/delete")
	public String deleteBoard(@PathVariable int type, @PathVariable int boardNo, RedirectAttributes rdAttr, HttpServletRequest request) {
		int result = boardService.deleteBoard(boardNo);
		
		String status;
		String msg;
		String path;
		if (result > 0) {
			status = "success";
			msg = "게시글 삭제 성공";
//			path = request.getContextPath()+"/board/list/"+type;d
			
			// 리다이렉트시 제일 앞 "/" 기호는 contextPath를 의미함.
			path = "/board/list/"+type;
		} else {
			status = "error";
			msg = "게시글 삭제 실패";
			path = request.getHeader("referer");
		}
		
		rdAttr.addFlashAttribute("status", status);
		rdAttr.addFlashAttribute("msg",msg);
		return "redirect:"+path;
		
	}	
	
	/* ModelAndView
	 * 
	 * Model : 응답페이지에 값을 전달할 때 Map 형태로 저장하여 전달하는 객체 
	 * View : 이동할 페이지 정보를 담는 객체 (view라는 객체가 별도로 존재하지는 않음)
	 * 
	 * 단순히 응답페이지에 데이터 전달, viewName설정 시 사용하는 객체
	 * */
	
	  // 게시글 수정
	@RequestMapping("{type}/{boardNo}/update")
	public ModelAndView updateView(@PathVariable int boardNo, ModelAndView mv) {
		
	         // 1) 기존 게시글 정보를 얻어와 update 화면에 출력해 이전 작성 내용을 보여주어야 함.
	         //       --> 상세조회 사용
			 Board board = boardService.selectBoard(boardNo);
	         
	         //--------------------------------------------------------
	         // 기존 게시글 이미지 조회 및 전달
			 if (board != null) {
					List<Attachment> files = boardService.selectFiles(boardNo);
					mv.addObject("files",files);
				}
	         //--------------------------------------------------------
	         
			 mv.addObject("board", board);
			 mv.setViewName("board/boardUpdate");
				
			return mv;
	      }
	
	// 게시글 수정
		
		@RequestMapping("{type}/{boardNo}/updateAction")
		public ModelAndView updateAction(@PathVariable int type, @PathVariable int boardNo, ModelAndView mv, 
										Board upBoard, int cp, boolean[] deleteImages, 
										RedirectAttributes rdAttr, HttpServletRequest request,
										@RequestParam(value="thumbnail", required = false) MultipartFile thumbnail,
										@RequestParam(value="images", required = false) List<MultipartFile> images) {
			
			upBoard.setBoardNo(boardNo);
			
			
			
			// 이미지 수정
			// ---------------------------------------------------
			
			// 업로드된 파일 이름 확인 
			// -> 파일 이름이 출력된 경우 == 이미지가 수정된 경우
			System.out.println("thumbnail : " + thumbnail.getOriginalFilename());
			for(int i=0; i<images.size(); i++) {
				System.out.println("images[" + i + "] : " + images.get(i).getOriginalFilename());
				
			}
			// 썸네일 이미지를 images 리스트 0qjs 인덱스에 추가
			images.add(0,thumbnail);
			
			// 파일 저장 경로 지정
			String savePath = request.getSession().getServletContext().getRealPath("resources/uploadImages");
			
			
			
			int result = boardService.updateBoard(upBoard, savePath,images,deleteImages);
			
			String status = null;
			String msg = null;
			String url = null;
			
			
			// 수정 성공 시 상세조회 화면으로 
			if(result > 0) {
				status = "success";
				msg = "게시글 수정 성공.";
				
				// 현재 : {type}/{boardNo}/updateAction?cp=1
				// 상세 : {type}/{boardNo}?cp=1
				url = "../" + boardNo + "?cp=" + cp;
				
			}else {
				// 실패 시 이전 요청주소(수정화면)
				status = "error";
				msg = "게시글 수정 실패";
				url = request.getHeader("referer");
			}
			
			
			rdAttr.addFlashAttribute("status", status);
			rdAttr.addFlashAttribute("msg",msg);
			
//			mv.addObject("board", upBoard);
			mv.setViewName("redirect:" + url);
			
			return mv;
			
		}
	
	
	
}
