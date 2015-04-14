package com.dreamjust.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

public class PhotoServlet extends HttpServlet {

	// base64解码
	BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	@Override
	protected void doPost(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		ServletInputStream in = req.getInputStream();
		String string = readLine(in);
		PrintWriter writer = resp.getWriter();
		try {
			byte[] bytes1 = decoder.decodeBuffer(string);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					bytes1);
			BufferedImage bi1 = ImageIO.read(bais);
			String filename = "I://"+dateFormat(new Date())+".jpg";
			File w2 = new File(filename);// 可以是jpg,png,gif格式
			ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
			writer.print(filename);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String dateFormat(Date date) {
		// TODO Auto-generated method stub
		String format = DateFormat.getDateTimeInstance()
				.format(new Date());
		String replace = format.replaceAll("\\D", "");
		return replace;
	}

	private String readLine(ServletInputStream in)
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
}
