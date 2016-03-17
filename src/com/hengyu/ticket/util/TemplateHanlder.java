package com.hengyu.ticket.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class TemplateHanlder {
	
	private final static String MSG_TEMP_SRC = "/com/msg/template/";
	public final static String MSG_ORDER_SUCCESS = "order_success.vm";

	
	/**
	 * 获取短信模板
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public static String getMsgTemplate(String name,VelocityContext ctx) throws IOException{
		String path = MSG_TEMP_SRC + name;
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			if(in!=null&&in.available()!=0){
				String s =  toString(in);
				VelocityEngine ve = new VelocityEngine();
				StringWriter out = new StringWriter();
				ve.evaluate(ctx, out, "", s);
				return out.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				in.close();
			}
		}
		return "";
	}
	
	
	
	/**
	 * 将输入流转换成字符串
	 * @param in 输入流
	 * @return string
	 * @throws IOException
	 */
	public static String toString(InputStream in) throws IOException{
		BufferedReader br = null;
		try {
			if (in != null) {
				StringBuffer sb = new StringBuffer();
				br = new BufferedReader(new InputStreamReader(
						new BufferedInputStream(in), "UTF-8"));
				while (true) {
					String readLine = br.readLine();
					if (readLine == null || readLine.isEmpty()) {
						break;
					}
					sb.append(readLine);
				}
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br!=null){br.close();}
		}
		return null;
	}
	
	
}
