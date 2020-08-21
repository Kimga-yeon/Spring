package com.kh.spring.board.model.vo;

public class Attachment {
	private int fileNo;
	private int parentBoardNo;
	private String fileOriginName;
	private String fileChangeName;
	private String filePath;
	private int fileLevel;
	
	public Attachment() {
		// TODO Auto-generated constructor stub
	}
	
	
	

	public Attachment(int parentBoardNo, String fileOriginName, String fileChangeName, String filePath, int fileLevel) {
		super();
		this.parentBoardNo = parentBoardNo;
		this.fileOriginName = fileOriginName;
		this.fileChangeName = fileChangeName;
		this.filePath = filePath;
		this.fileLevel = fileLevel;
	}




	public Attachment(int fileNo, int parentBoardNo, String fileOriginName, String fileChangeName, String filePath,
			int fileLevel) {
		super();
		this.fileNo = fileNo;
		this.parentBoardNo = parentBoardNo;
		this.fileOriginName = fileOriginName;
		this.fileChangeName = fileChangeName;
		this.filePath = filePath;
		this.fileLevel = fileLevel;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public int getParentBoardNo() {
		return parentBoardNo;
	}

	public void setParentBoardNo(int parentBoardNo) {
		this.parentBoardNo = parentBoardNo;
	}

	public String getFileOriginName() {
		return fileOriginName;
	}

	public void setFileOriginName(String fileOriginName) {
		this.fileOriginName = fileOriginName;
	}

	public String getFileChangeName() {
		return fileChangeName;
	}

	public void setFileChangeName(String fileChangeName) {
		this.fileChangeName = fileChangeName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFileLevel() {
		return fileLevel;
	}

	public void setFileLevel(int fileLevel) {
		this.fileLevel = fileLevel;
	}

	@Override
	public String toString() {
		return "attachment [fileNo=" + fileNo + ", parentBoardNo=" + parentBoardNo + ", fileOriginName="
				+ fileOriginName + ", fileChangeName=" + fileChangeName + ", filePath=" + filePath + ", fileLevel="
				+ fileLevel + "]";
	}
	
	
	

}
