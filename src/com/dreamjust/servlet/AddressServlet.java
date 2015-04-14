package com.dreamjust.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamjust.dao.impl.AddressDAOImpl;
import com.dreamjust.model.Area;
import com.dreamjust.model.City;
import com.dreamjust.model.Province;
import com.dreamjust.util.JsonUtil;

public class AddressServlet extends HttpServlet {

	AddressDAOImpl addressDAOImpl = new AddressDAOImpl();

	private String[] funs={"getprovince","getcity","getarea","getprovincebyid","getcitybyid","getareabyid"};
	@Override
	protected void doGet(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter writer = resp.getWriter();
		String funcation = req.getParameter("fun");
		int code=change(funcation);
		switch (code) {
		case 0:
			writer.print(fungetprovince(req));
			break;
		case 1:
			writer.print(fungetcity(req));
			break;
		case 2:
			writer.print(fungetarea(req));
			break;
		case 3:
			writer.print(fungetprovincebyid(req));
			break;
		case 4:
			writer.print(fungetcitybyid(req));
			break;
		case 5:
			writer.print(fungetareabyid(req));
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

	private String fungetareabyid(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String id = req.getParameter("id");
		return JsonUtil.bean2json(addressDAOImpl
				.getAreaById(Integer.parseInt(id)));
	}

	private String fungetcitybyid(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String id = req.getParameter("id");
		return JsonUtil.bean2json(addressDAOImpl
				.getCityById(Integer.parseInt(id)));
	}

	private String fungetprovincebyid(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String id = req.getParameter("id");
		return JsonUtil.bean2json(addressDAOImpl
				.getProvinceById(Integer.parseInt(id)));
	}

	private String fungetarea(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String cityid = req.getParameter("fatherid");
		List<Area> areas = addressDAOImpl
				.getAreaByFather(Integer.parseInt(cityid));
		return JsonUtil.bean2jsonArray(areas);
	}

	private String fungetcity(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String city = req.getParameter("fatherid");
		List<City> citys = addressDAOImpl
				.getCityByFather(Integer.parseInt(city));
		System.out.println(JsonUtil.bean2jsonArray(citys));
		return JsonUtil.bean2jsonArray(citys);
	}

	private String fungetprovince(HttpServletRequest req) {
		// TODO Auto-generated method stub
		List<Province> findProvinces = addressDAOImpl
				.findProvinces();
		return JsonUtil.bean2jsonArray(findProvinces);
	}

}
