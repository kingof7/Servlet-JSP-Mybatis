package com.java.sawon.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;
import com.java.sawon.model.SawonDao;
import com.java.sawon.model.SawonDto;

public class ListOkCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String departmentName = request.getParameter("departmentName");
		logger.info(logMsg + departmentName);
		
		List<SawonDto> sawonList = SawonDao.getInstance().select(departmentName);
		logger.info(logMsg + sawonList.size());
		
		return null;
	}

}
