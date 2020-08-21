package com.kh.spring.board.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class Board {
	private int BoardNo;
	private String boardTitle;
	private String boardContent;
	private int readCount;
	private Timestamp boardCreateDate;
	private Timestamp boardModifyDate;
	private String boardWriter;
	private String boardCategory;
	private int boardType;
	
	private String boardName;
	
	
	public Board() {
	}


	public Board(int boardNo, String boardTitle, String boardContent, int readCount, Timestamp boardCreateDate,
			Timestamp boardModifyDate, String boardWriter, String boardCategory, int boardType, String boardName) {
		super();
		BoardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.readCount = readCount;
		this.boardCreateDate = boardCreateDate;
		this.boardModifyDate = boardModifyDate;
		this.boardWriter = boardWriter;
		this.boardCategory = boardCategory;
		this.boardType = boardType;
		this.boardName = boardName;
	}


	public int getBoardNo() {
		return BoardNo;
	}


	public void setBoardNo(int boardNo) {
		BoardNo = boardNo;
	}


	public String getBoardTitle() {
		return boardTitle;
	}


	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}


	public String getBoardContent() {
		return boardContent;
	}


	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}


	public int getReadCount() {
		return readCount;
	}


	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}


	public Timestamp getBoardCreateDate() {
		return boardCreateDate;
	}


	public void setBoardCreateDate(Timestamp boardCreateDate) {
		this.boardCreateDate = boardCreateDate;
	}


	public Timestamp getBoardModifyDate() {
		return boardModifyDate;
	}


	public void setBoardModifyDate(Timestamp boardModifyDate) {
		this.boardModifyDate = boardModifyDate;
	}


	public String getBoardWriter() {
		return boardWriter;
	}


	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}


	public String getBoardCategory() {
		return boardCategory;
	}


	public void setBoardCategory(String boardCategory) {
		this.boardCategory = boardCategory;
	}


	public int getBoardType() {
		return boardType;
	}


	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}


	public String getBoardName() {
		return boardName;
	}


	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}


	@Override
	public String toString() {
		return "Board [BoardNo=" + BoardNo + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", readCount=" + readCount + ", boardCreateDate=" + boardCreateDate + ", boardModifyDate="
				+ boardModifyDate + ", boardWriter=" + boardWriter + ", boardCategory=" + boardCategory + ", boardType="
				+ boardType + ", boardName=" + boardName + "]";
	}


	
	
}
