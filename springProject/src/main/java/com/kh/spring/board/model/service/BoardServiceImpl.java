package com.kh.spring.board.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dao.BoardDAO;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService{

		@Autowired
		private BoardDAO boardDAO;

		@Autowired
		private PageInfo pInfo;
		
		// 페이징 처리를 위한 Service구현
		@Override
		public PageInfo pagination(int type, int cp) {

			// 1) 전체 게시글 수 조회
			int listCount = boardDAO.getlistCount(type);
			
			// 2) setPageInfo 호출
			pInfo.setPageInfo(cp, listCount, type);
			return pInfo;
		}
		
		// 게시글 목록 조회 Service 구현
		@Override
		public List<Board> selectList(PageInfo pInfo) {
			return boardDAO.selectList(pInfo);
		}

		// 게시글 상세 조회 Service 구현
		@Transactional(rollbackFor = Exception.class)
		@Override
		public Board selectBoard(int boardNo) {
			
			// 조회글 조회
			Board board = boardDAO.selectBoard(boardNo);
			
			// 조회 성공 시 조회 수 증가
			if(board != null) {
				int result = boardDAO.increaseCount(boardNo);
				
				// 조회된 게시글(board)의 조회수를 1 증가 시킴(그래야 DB와 일치)
				if(result > 0) {
					board.setReadCount(board.getReadCount() + 1);
				}
			}
			
			return board;
		}

		
		// 게시글 등록 Service 구현
		@Transactional(rollbackFor = Exception.class)
		@Override
		public int insertBoard(Board board, List<MultipartFile> images,String savePath) {
			int result = 0;
			
			// 1) 다음 SEQ_BNO를 얻어옴.
			int boardNo = boardDAO.selectNextNo();
			
			if(boardNo > 0) { // 다음 번호를 정상적으로 얻어 왔을 때 
				// 다음 번호 board 객체에 세팅
				board.setBoardNo(boardNo);
				
				// 크로스 사이트 스크립트 방지 처리
				board.setBoardContent(replaceParameter(board.getBoardContent()));
				
				// 2) 게시글 (board)DB 삽입
				result = boardDAO.insertBoard(board);
				
				
				// 3) 파일 정보 DB 저장 
				List<Attachment> files = new ArrayList<Attachment>();
				Attachment at = null;
				String filePath = "/resources/uploadImages";				
				
				
				for(int i=0; i<images.size(); i++) {
					
					if(!images.get(i).getOriginalFilename().equals("")) {
						// 업로드된 이미지가 있다면 
						
						// 파일명 변경 (cos.jar는 별도의 RenamePolicy가 존재하지만 스프링은 없음)
						// -> 별도의 메소드를 제작하여 변경 하기
						String changeName = rename(images.get(i).getOriginalFilename());
						
						// Attachment 객체 생성
						at = new Attachment(boardNo, images.get(i).getOriginalFilename(), 
											changeName, filePath, i);
						
						result = boardDAO.insertAttachment(at);
						
					}
					files.add(at);
				}
				
				// 4) 파일을 서버에 저장
				if(result > 0) {
					for(int i=0; i<images.size(); i++) {
						if(!images.get(i).getOriginalFilename().equals("")) {
							// 이미지 업로드가 되었다면 
							
							// transferTo(경로) : 지정한 경로에 업로드된 바이트상태의 파일을 실제 파일로 변환해서 저장해라.
							try {
								images.get(i).transferTo(new File(savePath + "/" + files.get(i).getFileChangeName()));
							} catch (Exception e) {
								e.printStackTrace();
								
								// 서버에 파일 저장 중 문제가 발생할 경우 
								// 이미 DB에 삽입되어 있는 파일 정보를 삭제하는 DAO를 호출 
								
								boardDAO.deleteAttachment(boardNo);
							} 
						}
					}
				}
			}
			
			return result;
		}
		
		
		
		// 게시글 삭제 Service구현
		@Transactional(rollbackFor = Exception.class)
		@Override
		public int deleteBoard(int boardNo) {
			return boardDAO.deleteBoard(boardNo);
		}
		
		
	    // 게시글 수정 Service 구현
	    @Transactional(rollbackFor = Exception.class)
	   @Override
	   public int updateBoard(Board upBoard, String savePath, List<MultipartFile> images, boolean[] deleteImages) {
	       // images : 수정된 파일 리스트
	       
	       // 크로스 사이트 스크립트 방지
	       upBoard.setBoardContent(replaceParameter(upBoard.getBoardContent()));
	       upBoard.setBoardTitle(replaceParameter(upBoard.getBoardTitle())); 
	       int result = boardDAO.updateBoard(upBoard); // 게시글만 수정
	       
	       if(result > 0) {
	          // 1) 이전 업로드 파일 목록 조회
	    	   List<Attachment> oldFiles = boardDAO.selectFiles(upBoard.getBoardNo());
	           
	           oldFiles.stream().forEach(s -> System.out.println("old files : " + s));
	          
	          // DB에 저장할 웹상에서의 이미지 접근 경로
	          String filePath = "/resources/uploadImages";
	          
	          List<Attachment> files = new ArrayList<Attachment>(); // 파일 정보를 모아 놓은 List
	          List<Attachment> removeFiles = new ArrayList<>();
	          Attachment at = null; // 임시 사용할 참조변수
	          for(int i = 0; i < images.size(); i++) {
	             if(!images.get(i).getOriginalFilename().equals("")) {
	                // 현재 접근중인 imgaes 요소의 실제 파일명이 빈 문자열이 아닐 경우
	                // == 수정된 이미지가 업로드 된 경우
	                
	                // 파일명 변경(rename 작업)
	                 String changeFileName = rename(images.get(i).getOriginalFilename());
	                
	                // 수정된 이미지 파일 정보를 저장할 Attachment 객체 생성
	                at = new Attachment(upBoard.getBoardNo(), images.get(i).getOriginalFilename(), 
	                        changeFileName, filePath, i);
	                
	                // 기존 이미지가 존재하는 경우 -> update
	                // 기존 이미지가 존재하지 않는 경우 -> insert
	                
	                boolean flag = false; // 기존 이미지가 존재하는 경우 true, 아니면 false;
	                
	                for(Attachment old : oldFiles) {   // 이전 파일 목록에 반복 접근
	                   if(old.getFileLevel() == i) {
	                      // 현재 접근한 이전 파일의 레벨이
	                      // 새롭게 업로드된(수정된) 파일의 레벨과 같을 경우
	                      // == 이전 파일이 새로운 파일로 수정이 된 경우
	                	   
	                      flag = true;
	                      removeFiles.add(old); // 서버 파일 제거 List에 수정 예정인 이전 파일 정보를 저장.
	                      
	                      at.setFileNo(old.getFileNo());
	                      // 이전 파일의 번호를 얻어와 DB상에서 데이터 수정할 수 있게
	                      // 새로운 파일에 번호 세팅
	                      break;
	                   }
	                }
	                
	                if(flag) {
	                    // update
	                    result = boardDAO.updateAttachment(at);
	                 }else {
	                    // insert
	                    result = boardDAO.insertAttachment(at);
	                 }
	                 
	              } else { // if end
	            	 // 업로드(수정)된 이미지가 없을 경우
	            	 if(deleteImages[i]) { // 삭제 버튼이 눌러진 인덱스인 경우 
	            		 for (Attachment old : oldFiles) {
	            			 // 이전 이미지 파일 목록에 반복 접근하여
	            			 // 삭제 버튼이 눌러진 인덱스(== 파일레벨)와
	            			 // 이전 이미지 중 같은 파일레벨을 가지고 있는 DB정보를 삭제

	            			 if (old.getFileLevel() == i) {
	            				 result = boardDAO.deleteAttachment2(old.getFileNo());
	            				 
	            				 // 서버측 파일 삭제 목록에 해당 파일 정보를 추가
	            				 removeFiles.add(old);
	            			 }
	            		 }
	            		 
	            	 }
	             }
	             
	             files.add(at);
	          }   
	       // 수정된 이미지를 서버에 저장
	          if(result > 0) {
	             for(int i = 0; i < images.size(); i++) {
	                if(!images.get(i).getOriginalFilename().equals("")) {
	                   // 새로운 업로드 된 파일이 존재한다면
	                   try {
	                      // transferTo() : 지정된 경로에 업로드된 파일정보를 실제 파일로 변환하는 메소드
	                     images.get(i).transferTo(new File(savePath + "/" + files.get(i).getFileChangeName()));
	                  } catch (Exception e) {
	                     // 서버에 파일 저장 중 오류 발생 시
	                     // -> DB에 있는 파일 정보도 삭제
	                     boardDAO.deleteAttachment(upBoard.getBoardNo());
	                  
	                  }
	                }
	             }
	          } // end if
	          
	          // 제거 목록에 있는 파일 삭제
	          for(Attachment re : removeFiles) {
	        	  File rm = new File(savePath + "/" + re.getFileChangeName());
	        	  rm.delete(); // 파일 삭제
	          }
	          
	       }
	       
	      return result;
	   }
		
		// 크로스 사이트 스크립트 방지 메소드
	    private String replaceParameter(String param) {
	        String result = param;
	        if(param != null) {
	            result = result.replaceAll("&", "&amp;");
	            result = result.replaceAll("<", "&lt;");
	            result = result.replaceAll(">", "&gt;");
	            result = result.replaceAll("\"", "&quot;");
	        }

	        return result;
	    }
	    
	    
	    // 게시글 이미지 조회
	    @Override
	    public List<Attachment> selectFiles(int boardNo) {
			return boardDAO.selectFiles(boardNo);
		}

		
	 // 파일명 변경
		public String rename(String originFileName) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			String date = sdf.format(new java.util.Date(System.currentTimeMillis()));

			int ranNum = (int)(Math.random()*100000); // 5자리 랜덤 숫자 생성

			String str = "" + String.format("%05d", ranNum);
			//String.format : 문자열을 지정된 패턴의 형식으로 변경하는 메소드
			// %05d : 오른쪽 정렬된 십진 정수(d) 5자리(5)형태로 변경. 빈자리는 0으로 채움(0)

			String ext = originFileName.substring(originFileName.lastIndexOf("."));

			return date + "" + str + ext;
		}

	    // 게시글 썸네일 목록 조회 Service 구현 
		@Override
		public List<Attachment> selectThumbnailList(List<Board> boardList) {
			return boardDAO.selectThumbnailList(boardList);
		}

		
}
