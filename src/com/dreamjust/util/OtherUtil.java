package com.dreamjust.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.junit.Test;

import com.dreamjust.servlet.DateServlet;
import com.sina.sae.storage.SaeStorage;

import sun.misc.BASE64Decoder;


public class OtherUtil {
	static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */ 
	public static String convertMD5(String inStr){

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		
		return s;

	}
	
	public  String[] writephoto(String img) throws IOException {
		// TODO Auto-generated method stub
		String[] imgurl = (String[]) JsonUtil.jsonArray2array(img);
		if (imgurl[0].equals("")) {
			return null;
		} 
		String[] newimg=new String[imgurl.length];
		for (int i=0;i<imgurl.length;i++) {
			if (imgurl[i].equals("")) {
				break;
			}
			byte[] bytes1 = decoder.decodeBuffer(imgurl[i]);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					bytes1); 
			BufferedImage bi1 = ImageIO.read(bais);
			String filename = dateFormat(new Date())+(int)(Math.random()*16)+".jpg";
			System.out.println("--------------路径是--------------");
			System.out.println(DateServlet.path);
			System.out.println("-----------------------------");
			System.out.println(imgurl.length);
			
			SaeStorage saeStorage=new SaeStorage();
			saeStorage.write("photo",filename, bytes1);
			newimg[i]="http://dreamjust1-photo.stor.sinaapp.com/"+filename;
//			File w2 = new File(DateServlet.path+"/img/"+filename);// 可以是jpg,png,gif格式
//			ImageIO.write(bi1, "jpg", w2);
//			newimg[i]="http://192.168.2.29:8080/LostAndFoundId/img/"+filename;
		}
	
		System.out.println(Arrays.toString(newimg));
		return newimg;
	}
	
	private static String dateFormat(Date date) {
		// TODO Auto-generated method stub
		String format = DateFormat.getDateTimeInstance()
				.format(date);
		String replace = format.replaceAll("\\D", "");
		return replace;
	}
	
	@SuppressWarnings("unused")
	private static String readLine(ServletInputStream in)
			throws IOException {
		byte[] buf = new byte[8 * 1024];
		StringBuffer sbuf = new StringBuffer();
		int result;
		// String line;

		do {
			result = in.readLine(buf, 0, buf.length); // does +=
			if (result != -1) {
				sbuf.append(new String(buf, 0, result,
						"UTF-8"));
			}
		} while (result == buf.length); // loop only if the buffer was filled

		if (sbuf.length() == 0) {
			return null; // nothing read, must be at the end of stream
		}

		return sbuf.toString();
	}
	
	public static String  util2date(String json)
	{
		int start= json.indexOf("datetime");
		int end = json.indexOf("}",start);
		String sub = json.substring(start+9,end+1);
		
		return json;
		
	}
	public static void main(String[] args) {
//		new OtherUtil().getPath();
		System.out.println(OtherUtil.class.getClassLoader().getResource(".")		
);
	}

	private  void getPath() {
		// TODO Auto-generated method stub
		String filename = "/img/"+dateFormat(new Date())+".jpg";
		System.out.println(filename);
		
		//发布的时候要修改!!!!!
		File w2 = new File(filename);// 可以是jpg,png,gif格式
		System.out.println(w2.getAbsolutePath());
		
	}

	// 测试主函数
}
