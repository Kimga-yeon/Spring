package com.kh.spring.board.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;

public interface BoardService {

	/** 페이징 처리를 위한 Service
	 * @param type
	 * @param cp
	 * @return
	 */
	PageInfo pagination(int type, int cp);

	
	
	/** 게시글 목록 조회
	 * @param pInfo
	 * @return boardList
	 */
	List<Board> selectList(PageInfo pInfo);



	/** 게시글 상세 조회 Service
	 * @param boardNo
	 * @return board
	 */
	Board selectBoard(int boardNo);



	/** 게시글 등록  Service
	 * @param board
	 * @param savePath 
	 * @param images 
	 * @return result
	 */
	int insertBoard(Board board, List<MultipartFile> images, String savePath);



	/** 게시글 삭제 Service
	 * @param boardNo
	 * @return result
	 */
	int deleteBoard(int boardNo);



	/** 게시글 수정 Service
	 * @param upBoard
	 * @return result
	 */
	int updateBoard(Board upBoard);



	/** 게시글 이미지 조회 Service
	 * @param boardNo
	 * @return files
	 */
	List<Attachment> selectFiles(int boardNo);

}
