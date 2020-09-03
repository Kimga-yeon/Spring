package com.kh.spring.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Reply;

@Repository
public class ReplyDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<Reply> selectList(int boardNo) {
		return sqlSession.selectList("replyMapper.selectList", boardNo);
	}

	public int insertReply(Reply reply) {
		return sqlSession.insert("replyMapper.insertReply", reply);
	}

	public int insertReply2(Reply reply) {
		return sqlSession.insert("replyMapper.insertReply2", reply);
	}

}