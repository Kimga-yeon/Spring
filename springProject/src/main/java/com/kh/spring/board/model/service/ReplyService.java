package com.kh.spring.board.model.service;

import java.util.List;

import com.kh.spring.board.model.vo.Reply;

public interface ReplyService {

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return reply List
	 */
	List<Reply> selectList(int boardNo);

	/** 댓글 삽입
	 * @param reply
	 * @return int
	 */
	int insertReply(Reply reply);

	/** 답글 삽입
	 * @param reply
	 * @return int
	 */
	int insertReply2(Reply reply);

}