package com.dreamjust.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamjust.dao.CommentDAO;
import com.dreamjust.dao.UserDAO;
import com.dreamjust.dao.impl.CommentDAOImpl;
import com.dreamjust.dao.impl.UserDAOImpl;
import com.dreamjust.model.Comment;
import com.dreamjust.model.User;
import com.dreamjust.util.JsonUtil;

public class CommentServlet extends HttpServlet {

	CommentDAO commentDAO = new CommentDAOImpl();
	private String[] funs={"mymessage","getcomment","insertcomment"};
	@Override
	protected void doGet(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String funcation = req.getParameter("fun");
		PrintWriter writer = resp.getWriter();
		int code=change(funcation);
		switch (code) {
		case 0:

			writer.print(funmymessage(req
					.getParameter("userid")));
			break;
		case 1:

			writer.print(fungetmessage(req
					.getParameter("lostthingid"),req.getParameter("foundthingid")));
			break;
		
		}
		writer.flush();
		writer.close();
	}
	
	private int change(String funcation) {
		// TODO Auto-generated method stub
		for (int i = 0; i < funs.length; i++) {
			if (funcation.equals(funs[i])) {
				return i;
			}
		}
		return -1;
	}

	private String funinsertcomment(String json) {
		// TODO Auto-generated method stub\
		System.out.println(json);
		Comment comment = (Comment) JsonUtil.json2bean(json, "Comment");
		System.out.println("------------");
		System.out.println(comment);
		int insertComment = commentDAO.insertComment(comment);
		UserDAO userDAO=new UserDAOImpl();//注意:这个地方为了判断评论到底是谁发出的,设置lostuserid和founduserid只允许一个为0
		
		if (comment.getFoundUserid()==0) {
			User user=userDAO.getUserById(comment.getLostUserid());

			String lostusername=user.getUsername();
			comment.setLostUsername(lostusername);
			comment.setLostUserImg(user.getImgUrl());
		}
		else {
			User user=userDAO.getUserById(comment.getFoundUserid());

			String foundusername=user.getUsername();
			comment.setFoundUsername(foundusername);
			comment.setFoundUserImg(user.getImgUrl());
		}
		comment.setId(insertComment);
		return JsonUtil.bean2json(comment);
		
	}

	private String fungetmessage(String lostthing,
			String foundthing) {
		// TODO Auto-generated method stub
		int lostthingid=Integer.parseInt(lostthing);
		int foundthingid=Integer.parseInt(foundthing);
		List<Comment> comments = commentDAO.getComments(lostthingid, foundthingid);
		return JsonUtil.bean2jsonArray(comments);
	}

	private String funmymessage(String userid) {
		int id = Integer.parseInt(userid);
		List<Comment> comms = commentDAO
				.getCommentsByUser(id);
		return JsonUtil.bean2jsonArray(comms);

	}

	@Override
	protected void doPost(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String funcation = req.getParameter("fun");
		PrintWriter writer = resp.getWriter();
		int code=change(funcation);
		switch (code) {
			case 2:
				writer.print(funinsertcomment(req.getParameter("json")));
				break;
		}
		writer.flush();
		writer.close();
	}
}
