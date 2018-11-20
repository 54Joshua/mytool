package com.hcb.demo.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 
 * @author hcb
 * 	验证码：通过Session.getAttribut("code")得到验证码
 *
 */
public class VerificationCode {
	private static final int IMG_WIDTH = 100;
	private static final int IMG_HEIGHT = 30;
	private static final int CODE_LONG = 4;
	private static final String CODE_VALUE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public void verificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedImage bi = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bi.getGraphics();
		graphics.setColor(new Color(169,169,169)); // 使用RGB设置背景颜色
		graphics.fillRect(0, 0, 100, 30); // 填充矩形区域
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		graphics.setFont(new Font("微软雅黑", Font.BOLD, 20));
		for (int i = 0; i < CODE_LONG; i++) {
			char code = CODE_VALUE.charAt(random.nextInt(CODE_VALUE.length()));
			// 随机生成验证码颜色
			graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			graphics.drawString(code + "", (i * 20) + 15, 25);
			sb.append(code);
			//生成干扰线
			for (int j = 0; j < (random.nextInt(2) + 1); j++) {
				graphics.setColor(new Color(random.nextInt(255) + 1, random.nextInt(255) + 1, random.nextInt(255) + 1));
				graphics.drawLine(random.nextInt(100), random.nextInt(30), random.nextInt(100), random.nextInt(30));
			}

		}
		HttpSession session = request.getSession();
		session.setAttribute("code", sb.toString());//把值保存在session中
		// 通过ImageIO将图片输出
		OutputStream os = response.getOutputStream();
		ImageIO.write(bi, "png", os);
		os.flush();
		os.close();

	}
}
