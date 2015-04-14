package com.dreamjust.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

import com.dreamjust.dao.FoundDAO;
import com.dreamjust.dao.LostDAO;
import com.dreamjust.dao.UserDAO;
import com.dreamjust.dao.impl.FoundDAOImpl;
import com.dreamjust.dao.impl.LostDAOImpl;
import com.dreamjust.dao.impl.UserDAOImpl;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.LostThing;
import com.dreamjust.model.User;
import com.dreamjust.util.JsonUtil;
import com.dreamjust.util.OtherUtil;
import com.dreamjust.util.StaticVariable;

public class UserServlet extends HttpServlet {

	UserDAO userDAO = new UserDAOImpl();
	BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	OtherUtil otherUtil=new OtherUtil();
	private String[] funs={"allhonour","otherlook","getuser","register","updateuser","thanks","askphone"};
	
	private int change(String funcation) {
		// TODO Auto-generated method stub
		for (int i = 0; i < funs.length; i++) {
			if (funcation.equals(funs[i])) {
				return i;
			}
		}
		return -1;
	}
	@Override
	protected void doPost(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String funcation = req.getParameter("fun");
		PrintWriter writer = resp.getWriter();

		System.out.println(funcation);
		int code=change(funcation);
		switch (code) {
		case 2:
			String jsonlogin = req.getParameter("json");
			String key = req.getParameter("md5");
			User userlogin = (User) JsonUtil.json2bean(
					jsonlogin, "User");
			User check = userDAO.getUserByEmail(userlogin
					.getEmail());
			if (check == null) {
				sayfalse(resp);
				return;
			}
			String pa = userlogin.getPassword();
			System.out.println(pa);
			if (key.equals("1")) {
				pa = OtherUtil.convertMD5(pa);
			}
			System.out.println(pa);
			System.out.println(check.getPassword());
			if (check.getPassword().equals(pa)) {
				check.setPassword(OtherUtil.convertMD5(pa));
				System.out.println("check:"+check.getPassword());
				sayjson(check, resp);
				return;
			}
			sayfalse(resp);
			break;
		case 3:
			String jsonregister = req.getParameter("json");
			System.out.println(jsonregister);
			User userreg = (User) JsonUtil.json2bean(
					jsonregister, "User");
			int insertid = userDAO.insertUser(userreg);
			userreg.setId(insertid);
			userreg.setPassword(OtherUtil
					.convertMD5(userreg.getPassword()));
			System.out.println(userreg.getPassword());
			writer.print(JsonUtil.bean2json(userreg));
			break;
		case 4:
			String jsonupdate = req.getParameter("json");

			String img = req.getParameter("img");
			System.out.println(img);
			img = img.replaceAll("\\s", "+");
			
			
			User userupdate = (User) JsonUtil.json2bean(
					jsonupdate, "User");
			if (img.equals("[\"\"]")) {
//				String s=userupdate.getImgUrl();
//				int indexOf = s.indexOf("/img/");//改!!!!
//				userupdate.setImgUrl(s.substring(indexOf));
			}
			System.out.println(userupdate.getPassword());
			userupdate.setPassword(OtherUtil
					.convertMD5(userupdate.getPassword()));
			if (!img.equals("[\"\"]")) {
				userupdate.setImgUrl(otherUtil
						.writephoto(img)[0]);
			}
			boolean b = userDAO.updateUser(userupdate);
			userupdate.setPassword(OtherUtil.convertMD5(userupdate.getPassword()));
			sayjson(userupdate, resp);
			break;
		case 5:
			sayboolean(funthanks(req), resp);
			break;
		case 6:
			writer.print(funaskphone(req));
			break;
		default:
			break;
		}
		writer.flush();
		writer.close();


	}
	@Override
	protected void doGet(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();
		String funcation = req.getParameter("fun");
		int code=change(funcation);
		switch (code) {
		case 0:
			writer.print(funallhonour());
			writer.flush();
			break;
		case 1:
			writer.print(funotherlook(req));
			writer.flush();
			break;
		default:
			break;
		}
	}

	private String funotherlook(HttpServletRequest req) {
		// TODO Auto-generated method stub
		int userid = Integer.parseInt(req
				.getParameter("userid"));
		User u = userDAO.getUserById(userid);
		u.setPhoneNum("");
		String json = JsonUtil.bean2json(u);
		
		int mingci = StaticVariable.sorts.get(userid);
		return "nu=" + mingci + "&" + json;
	}

	private String funallhonour() {
		List<User> users = userDAO.getUserByPrice(10);
		return JsonUtil.bean2jsonArray(users);
	}

	

	private String funaskphone(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String jsonlogin = req.getParameter("json");
		String key = req.getParameter("md5");
		String user=req.getParameter("user");
		User userlogin = (User) JsonUtil.json2bean(
				jsonlogin, "User");
		User check = userDAO.getUserByEmail(userlogin
				.getEmail());
		String error="对方没有留下手机号,请使用评论功能";
		boolean b=false;
		if (check == null) {
			b=false;
			return error;
		}
		String pa = userlogin.getPassword();
		System.out.println(pa);
		if (key.equals("1")) {
			pa = OtherUtil.convertMD5(pa);
		}
		System.out.println(pa);
		System.out.println(check.getPassword());
		if (check.getPassword().equals(pa)) {
			check.setPassword(OtherUtil.convertMD5(pa));
			System.out.println("check:"+check.getPassword());
			b=true;
			return userDAO.getUserById(Integer.parseInt(user)).getPhoneNum();
		}
		
		return error;
		
	}

	private void sayjson(User check,
			HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter writer = resp.getWriter();
		writer.print(JsonUtil.bean2json(check));
		writer.flush();
		writer.close();
	}

	private boolean funthanks(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));
		int price = Integer.parseInt(req
				.getParameter("price"));
		int heart = Integer.parseInt(req
				.getParameter("heart"));
		int smart = Integer.parseInt(req
				.getParameter("smart"));
		int patience = Integer.parseInt(req
				.getParameter("patience"));
		int arr[] = { heart, smart, patience };
		int overlostid = Integer.parseInt(req
				.getParameter("overlostid"));
		int overfoundid = Integer.parseInt(req
				.getParameter("overfoundid"));
		User user = userDAO.getUserById(id);
		user.setPrice(user.getPrice() + price);
		String im = user.getImpression();
		if (im==null||im.equals("")) {
			im = "heart=" + heart + "&smart=" + smart
					+ "&patience=" + patience;
		} else {
			String[] sp = im.split("&");
			im = "";
			for (int i = 0; i < sp.length; i++) {
				String[] sps = sp[i].split("=");
				if (sps.length!=2) {
					break;
				}
				sps[1] = (Integer.parseInt(sps[1]) + arr[i])
						+ "";
				im += sps[0] + "=" + sps[1] + "&";
			}
			im.substring(0, im.length() - 1);
		}
		user.setImpression(im);
		userDAO.updateUser(user);
		LostDAO lostDAO = new LostDAOImpl();
		LostThing lostThing = lostDAO
				.getLostThingById(overlostid);
		lostThing.setOtherThingid(overfoundid);
		lostDAO.updateLostThing(lostThing);
		FoundDAO foundDAO = new FoundDAOImpl();
		FoundThing foundThing = foundDAO
				.getFoundThingById(overfoundid);
		foundThing.setOtherThingid(overlostid);
		foundDAO.updateFoundThing(foundThing);
		return true;

	}

	private void sayboolean(boolean b,
			HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = resp.getWriter();
		out.print(b);
		out.flush();
		out.close();
	}

	private void sayOK(HttpServletResponse resp)
			throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = resp.getWriter();
		out.print("OK");
		out.flush();
		out.close();
	}

	private void sayfalse(HttpServletResponse resp)
			throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = resp.getWriter();
		out.print("false");
		out.flush();
		out.close();
	}
}
