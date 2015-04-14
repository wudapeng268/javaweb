package com.dreamjust.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamjust.dao.FoundDAO;
import com.dreamjust.dao.PhotoDAO;
import com.dreamjust.dao.impl.FoundDAOImpl;
import com.dreamjust.dao.impl.PhotoDAOImpl;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.LostThing;
import com.dreamjust.model.Photo;
import com.dreamjust.util.InformationMatch;
import com.dreamjust.util.JsonUtil;
import com.dreamjust.util.OtherUtil;

public class FoundThingServlet extends HttpServlet {

	FoundDAO foundDAO = new FoundDAOImpl();
	PhotoDAO photoDAO = new PhotoDAOImpl();
	OtherUtil otherUtil=new OtherUtil();
	private String[] funs={"allfound","myfound","getmatchlost","getfoundphoto","updatefound","insertfound","searchfound"};
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
			writer.print(funallfound(req));
			break;
		case 1:
			writer.print(funmyfound(req));
			break;
		case 2:
			writer.print(fungetmatchlost(req));
			break;
		case 3:
			writer.print(fungetfoundphoto(req));
			break;

		default:
			break;
		}
		writer.flush();
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


	private String fungetfoundphoto(HttpServletRequest req) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		String[] photos = photoDAO.getPhotoByFoundThing(id);
		
		return JsonUtil.bean2jsonArray(photos);
	}

	private String fungetmatchlost(HttpServletRequest req)
			throws IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		FoundThing found = foundDAO.getFoundThingById(id);
		List<LostThing> losts = InformationMatch
				.matchLostThings(found);
		System.out.println(JsonUtil.bean2jsonArray(losts));
		return JsonUtil.bean2jsonArray(losts);
	}

	private String funsearchfound(HttpServletRequest req)
			throws IOException {
		// TODO Auto-generated method stub
		String json = req.getParameter("json");
		FoundThing foundThing = (FoundThing) JsonUtil
				.json2bean(json, "FoundThing");
		List<LostThing> lists = InformationMatch
				.matchLostThings(foundThing);
		return JsonUtil.bean2jsonArray(lists);
	}

	private String funinsertfound(HttpServletRequest req)
			throws IOException {
		String json = req.getParameter("json");
		String img = req.getParameter("img");
		img = img.replaceAll("\\s", "+");

		FoundThing foundThing = (FoundThing) JsonUtil
				.json2bean(json, "FoundThing");
		int insertfound = foundDAO
				.insertFoundThing(foundThing);
		if (insertfound == -1) {

			return "ÉÏ´«Ê§°Ü";
		}
		String photoarr[] = otherUtil.writephoto(img);
		if (photoarr != null) {
			for (int i = 0; i < photoarr.length; i++) {
				if (photoarr[i] == null
						|| photoarr[i].equals("")) {
					break;
				}
				Photo photo = new Photo(
						foundThing.getUserid(),
						photoarr[i], 0, insertfound);
				photoDAO.insertPhoto(photo);
			}
			foundThing.setAllphotos(photoarr);
		}
		List<LostThing> lists = InformationMatch
				.matchLostThings(foundThing);
		return "id=" + insertfound + "&"
				+ JsonUtil.bean2jsonArray(lists);
	}

	private String funmyfound(HttpServletRequest req) {
		// TODO Auto-generated method stub
		int userid = Integer.parseInt(req
				.getParameter("id"));
		List<FoundThing> lists = foundDAO
				.getFoundThingByUserId(userid);
		return JsonUtil.bean2jsonArray(lists);
	}

	private String funallfound(HttpServletRequest req) {
		// TODO Auto-generated method stub
		int nu = Integer.parseInt(req.getParameter("nu"));
		int startid = Integer.parseInt(req
				.getParameter("startid"));
		List<FoundThing> lists = null;
		System.out.println("----------------" + startid);
		if (startid == 0) {
			lists = foundDAO.getFoundThingUnmate(nu);
		} else {
			// System.out.println("----------------"+startid);
			lists = foundDAO.getFoundThingUnmate(nu,
					startid);
		}
		String json = JsonUtil.bean2jsonArray(lists);

		System.out.println(json);
		System.out.println(lists.size());
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
			writer.print(funupdatefound(req));
		case 5:
			writer.print(funinsertfound(req));
			break;
		case 6:
			writer.print(funsearchfound(req));
			break;
		}
		writer.flush();
		writer.close();
	}

	private boolean funupdatefound(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String json = req.getParameter("json");
		FoundThing foundThing = (FoundThing) JsonUtil
				.json2bean(json, "FoundThing");
		return foundDAO.updateFoundThing(foundThing);
	}
}
