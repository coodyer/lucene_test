package com.bigdb.server.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.bigdb.server.data.DataService;
import com.bigdb.server.entity.MsgEntity;

@SuppressWarnings("serial")
public class DataSearchServlet extends HttpServlet {
	
	private static final Logger logger=Logger.getLogger(DataSearchServlet.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String keyworld=request.getParameter("keyworld");
		if(keyworld==null){
			response.getWriter().print(JSON.toJSON(new MsgEntity(-1, "请输入关键词")));
			return;
		}
		keyworld=keyworld.trim();
		if(keyworld.length()<2||keyworld.length()>48){
			response.getWriter().print(JSON.toJSON(new MsgEntity(-1, "关键词不合法")));
			return;
		}
		logger.info("搜索关键字:"+keyworld);
		List<String> results=DataService.search(keyworld);
		if(results==null||results.isEmpty()){
			response.getWriter().print(JSON.toJSON(new MsgEntity(-1, "未找到结果")));
			return;
		}
		response.getWriter().print(JSON.toJSON(new MsgEntity(0, "结果数:"+results.size(),results)));
		return;
	}

}
