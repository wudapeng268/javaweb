package com.dreamjust.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.ChangedCharSetException;

import com.dreamjust.dao.LostDAO;
import com.dreamjust.dao.PhotoDAO;
import com.dreamjust.dao.impl.LostDAOImpl;
import com.dreamjust.dao.impl.PhotoDAOImpl;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.LostThing;
import com.dreamjust.model.Photo;
import com.dreamjust.util.InformationMatch;
import com.dreamjust.util.JsonUtil;
import com.dreamjust.util.OtherUtil;
import com.dreamjust.util.StaticVariable;

public class LostThingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	LostDAO lostDAO = new LostDAOImpl();
	PhotoDAO photoDAO = new PhotoDAOImpl();
	private String[] funs={"alllost","mylost","getmatchfound","getlostphoto","updatelost","insertlost","searchlost"};

	OtherUtil otherUtil=new OtherUtil();
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
			writer.print(funalllost(req));
			break;
		case 1:
			writer.print(funmylost(req));
			break;
		case 2:
			writer.print(fungetmatchfound(req));
			break;
		case 3:
			writer.print(fungetlostphoto(req));
			break;

		default:
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

	private String fungetlostphoto(HttpServletRequest req) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		String[] photos = photoDAO
				.getPhotoByLostThing(id);
		return JsonUtil.bean2jsonArray(photos);
	}

	private String fungetmatchfound(HttpServletRequest req)
			throws IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		LostThing lost = lostDAO.getLostThingById(id);
		System.out.println("11111111111111111111111111111");
		System.out.println(JsonUtil.bean2json(lost));
		List<FoundThing> founds = InformationMatch
				.matchFoundThings(lost);
		System.out.println("****************************");
		System.out.println(JsonUtil.bean2jsonArray(founds));
		return JsonUtil.bean2jsonArray(founds);
	}

	private String funsearchlost(HttpServletRequest req)
			throws IOException {
		// TODO Auto-generated method stub
		String json = req.getParameter("json");
		System.out.println("----------------------");
		System.out.println(json);
		LostThing lostThing = (LostThing) JsonUtil
				.json2bean(json, "LostThing");
		List<FoundThing> lists = InformationMatch
				.matchFoundThings(lostThing);
		return JsonUtil.bean2jsonArray(lists);
	}

	private String funinsertlost(HttpServletRequest req)
			throws IOException {
		String json = req.getParameter("json");
		String img = req.getParameter("img");
		img = img.replaceAll("\\s", "+");
		LostThing lostThing = (LostThing) JsonUtil
				.json2bean(json, "LostThing");
		int insertLostThing = lostDAO
				.insertLostThing(lostThing);
		String photoarr[] = otherUtil.writephoto(img);
		if (photoarr != null) {
			for (int i = 0; i < photoarr.length; i++) {
				if (photoarr[i] == null
						|| photoarr[i].equals("")) {
					break;
				}
				Photo photo = new Photo(
						lostThing.getUserid(), photoarr[i],
						insertLostThing, 0);
				photoDAO.insertPhoto(photo);
			}
			lostThing.setAllphotos(photoarr);
		}
		if (insertLostThing == -1) {
			return "ÉÏ´«Ê§°Ü";
		}
		List<FoundThing> lists = InformationMatch
				.matchFoundThings(lostThing);
		return "id=" + insertLostThing + "&"
				+ JsonUtil.bean2jsonArray(lists);
	}

	private String funmylost(HttpServletRequest req) {
		// TODO Auto-generated method stub
		int userid = Integer.parseInt(req
				.getParameter("id"));
		List<LostThing> lists = lostDAO
				.getLostThingByUserId(userid);
		return JsonUtil.bean2jsonArray(lists);
	}

	private String funalllost(HttpServletRequest req) {
		// TODO Auto-generated method stub
		int nu = Integer.parseInt(req.getParameter("nu"));
		int startid = Integer.parseInt(req
				.getParameter("startid"));
		System.out.println("nu=" + nu + "startid="
				+ startid);
		List<LostThing> lists = null;
		if (startid == 0) {
			lists = lostDAO.getLostThingUnmate(nu);
		} else {
			lists = lostDAO.getLostThingUnmate(nu, startid);
		}
		String json = JsonUtil.bean2jsonArray(lists);
		System.out.println(json);
		return json;
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
		case 4:
			writer.print(funupdatelost(req));
			break;
		case 5:
			writer.print(funinsertlost(req));
			break;
		case 6:
			writer.print(funsearchlost(req));
			break;
		}
		writer.flush();
		writer.close();
	}

	private boolean funupdatelost(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String json = req.getParameter("json");
		LostThing lostThing = (LostThing) JsonUtil
				.json2bean(json, "LostThing");
		return lostDAO.updateLostThing(lostThing);
	}
}
