package com.hengyu.ticket.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class URLHanlder {
	
	/**
	 * 发送 HTTP 请求获取输入流
	 * @param url
	 * @param params
	 * @return
	 */
	public static InputStream postSteam(String url,Map<String,String> params){
		URL uri = null;
		try {
			if(params!=null&&params.size()!=0){
				StringBuffer sb = new StringBuffer();
				int index = 0;
				if(url.indexOf("?")!=-1){
					sb.append("&");
				}else{
					sb.append("?");
				}
				for (String s : params.keySet()) {
					sb.append(s);
					sb.append("=");
					sb.append(params.get(s));
					if(params.size()-1!=index){
						sb.append("&");
					}
					index++;
				}
				url+=sb.toString();
			}
			uri = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("POST");
			int l = conn.getContentLength();
			if(l>0){
				return conn.getInputStream();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 发送输入流数据
	 * @param url
	 * @param in
	 * @return
	 */
	public static InputStream post(String url,InputStream in){
		URL uri = null;
		try {
			uri = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStream out = conn.getOutputStream();
			byte[] bs = new byte[1024];
			int len = -1;
			while ((len = in.read(bs))!=-1) {
				out.write(bs,0,len);
			}
			in.close();
			out.flush();
			out.close();
			int l = conn.getContentLength();
			if(l>0){
				return conn.getInputStream();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 发送输入流数据
	 * @param url
	 * @param content
	 * @return
	 */
	public static InputStream post(String url,String content,String charset){
		URL uri = null;
		try {
			uri = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStream out = conn.getOutputStream();
			OutputStreamWriter ow = new OutputStreamWriter(out,charset);
			ow.write(content);
			ow.flush();
			ow.close();
			out.flush();
			out.close();
			int len = conn.getContentLength();
			if(len>0){
				return conn.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static InputStream post(String url,String content){
		return post(url,content,"UTF-8");
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
	
	
	/**
	 * 发送 HTTP 请求获取输入流
	 * @param url
	 * @return
	 */
	public static InputStream postSteam(String url){
		return postSteam(url,null);
	}
	
	//输入流转字符
	public static String post(String url,Map<String,String> params) throws IOException{
		return toString(postSteam(url,params));
	}
	
	//输入流转字符
	public static String post(String url) throws IOException{
		return toString(postSteam(url,null));
	}
}
