package com.java.reply.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;
import com.java.reply.model.ReplyDao;
import com.java.reply.model.ReplyDto;

public class ReplyWriteCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		//js에는 setAttribute로 데이터를 못보내기에, 텍스트로 보내야함
		
		
		String writeReply=request.getParameter("writeReply");
		logger.info(logMsg + writeReply);
		
		ReplyDto replyDto = new ReplyDto();
		replyDto.setLine_reply(writeReply);
		
		int check = ReplyDao.getInstance().insert(replyDto);
		logger.info(logMsg + check);
		
		if(check > 0) {
			int bunho = ReplyDao.getInstance().getBunho();
			logger.info(logMsg + writeReply + "," + bunho);
			
			//str을 어떻게 보내지~?
			String str=bunho + "," + writeReply; //JSON -- SPRING에서 바꿔줌
			// setContentType에 대해 api읽어보기
			response.setContentType("application/text;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(str); //요렇게 str을 js에 보낸다
			
			
		}
				
		return null;
	}

}
