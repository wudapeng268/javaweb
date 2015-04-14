package com.dreamjust.util;


import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.dreamjust.model.Address;
import com.dreamjust.model.LostThing;

public class JsonUtil {

	public static Object json2bean(String json,
			String classname) {
		Class<?> class1=null;
		try {
			class1 = Class.forName("com.dreamjust.model."+classname);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());

		JSONObject jsonObject = JSONObject.fromObject(json,jsonConfig);
		return JSONObject.toBean(jsonObject, class1);
	}
	
	public static Object jsonArray2array(String json)
	{
		JSONArray jsonArray=JSONArray.fromObject(json);
		return JSONArray.toArray(jsonArray,String.class);
	}
	
	

	public static String bean2json(Object bean) {
		
		JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
		return JSONObject.fromObject(bean,jsonConfig).toString();
	}
	
	public static String bean2jsonArray(Object bean) {
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setExcludes(new String[]{"password"});
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());

		return JSONArray.fromObject(bean,jsonConfig).toString();
	}
	public static void main(String[] args)
			throws JSONException {
//		Address address=new Address(1, 2, 2, "adfl;ak");
//		List<LostThing> list=new ArrayList<>();
//		list.add(new LostThing("1", "1", new Date(), address, "1", 1));
//		list.add(new LostThing("2", "2", new Date(), address, "2", 2));
//		System.out.println(bean2jsonArray(list));
		String[] arrString={"1","2"};
		String bean2jsonArray = bean2jsonArray(arrString);
		System.out.println(bean2jsonArray);
//		Address address=new Address(1, 2, 2, "adfl;ak");
//		String bean2json = bean2json(new LostThing("1", "1", new Date(), address, "1", 1));
//		System.out.println(bean2json);
//		LostThing lostThing = (LostThing) json2bean(bean2json, "LostThing");
//		System.out.println(lostThing.getDatetime().toString());
	}
}
