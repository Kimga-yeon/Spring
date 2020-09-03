package com.kh.spring.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.board.model.dao.ReplyDAO;
import com.kh.spring.board.model.vo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyDAO replyDAO;

	@Override
	public List<Reply> selectList(int boardNo) {
		return replyDAO.selectList(boardNo);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public int insertReply(Reply reply) {
        reply.setReplyContent(replaceParameter(reply.getReplyContent()));

        // 개행문자 처리 \n -> <br>
        reply.setReplyContent(reply.getReplyContent().replaceAll("\n", "<br>"));
        // 크로스 사이트 스크립트 방지 메소드
		return replyDAO.insertReply(reply);
	}



    @Override
    @Transactional(rollbackFor = Exception.class)
	public int insertReply2(Reply reply) {
        reply.setReplyContent(replaceParameter(reply.getReplyContent()));

        // 개행문자 처리 \n -> <br>
        reply.setReplyContent(reply.getReplyContent().replaceAll("\n", "<br>"));
        // 크로스 사이트 스크립트 방지 메소드
		return replyDAO.insertReply2(reply);
	}

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




}