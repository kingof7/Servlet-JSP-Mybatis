package com.java.fileBoard.command;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.fileBoard.model.BoardDao;
import com.java.fileBoard.model.BoardDto;
import com.java.command.Command;

public class DeleteOkCommand implements Command{

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		String password = request.getParameter("password");
		
		logger.info(logMsg + boardNumber + "," + pageNumber + "," + password);
		
		BoardDto readBoard = BoardDao.getInstance().select(boardNumber);
		
		
		int check = BoardDao.getInstance().delete(boardNumber, password);
		
		logger.info(logMsg + check);
		
		if(check > 0 && readBoard.getPath() != null) { // 패스, 파일네임, 파일명 셋중 하나가 널이아니면 조건 달아주면됨
			File file = new File(readBoard.getPath()); // 파일 경로 확인
			if(file.exists() && file.isFile()) file.delete(); //경로가 존재하고 파일이 있다면, pds안에 파일 삭제
			
		}
		
		request.setAttribute("check", check);
		request.setAttribute("pageNumber", pageNumber);
		
		return "/WEB-INF/views/fileBoard/deleteOk.jsp";
		
	}

}
