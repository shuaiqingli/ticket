package com.hengyu.ticket.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestTool
{
  public static String getString(HttpServletRequest request, String key)
  {
    String pv = request.getParameter(key);
    if (pv == null) {
      return "";
    }

    return pv;
  }

  public static int getInt(HttpServletRequest request, String key,Integer defvalue) {
    String pv = getString(request, key);
    if (pv.equals("")) {
      return defvalue;
    }
    int iv = 0;
    try {
      iv = Integer.parseInt(pv);
    } catch (NumberFormatException e) {
      iv = defvalue;
    }
    return iv;
  }

  public static double getDouble(HttpServletRequest request, String key,Double defvalue) {
    String pv = getString(request, key);
    double iv = 0.0D;
    if (pv.equals(""))
      return defvalue;
    try
    {
      iv = Double.parseDouble(pv);
    } catch (NumberFormatException e) {
      iv = defvalue;
    }
    return iv;
  }
  
  public static BigDecimal getBigDecimal(HttpServletRequest request, String key,BigDecimal defvalue) {
	    String pv = getString(request, key);
	    BigDecimal iv = new BigDecimal("0");
	    if (pv.equals(""))
	      return defvalue;
	    try
	    {
	      iv = new BigDecimal(pv);
	    } catch (NumberFormatException e) {
	      iv = defvalue;
	    }
	    return iv;
	  }

  public static long getLong(HttpServletRequest request, String key,Long defvalue) {
    String pv = getString(request, key);
    long iv = 0L;
    if (pv.equals(""))
      return defvalue;
    try
    {
      iv = Long.parseLong(pv);
    } catch (NumberFormatException e) {
      iv = defvalue;
    }
    return iv;
  }

  public static Date getDate(HttpServletRequest request, String key) {
    String pv = getString(request, key);
    if (pv.equals(""))
      return null;
    Date date;
    try
    {
      date = DateUtil.stringToDate(pv);
    } catch (Exception e) {
      return null;
    }
    return date;
  }

  public static Date getDateTime(HttpServletRequest request, String key) {
    String pv = getString(request, key);
    if (pv.equals(""))
      return null;
    Date date;
    try
    {
      date = DateUtil.StringToDatetime(pv);
    } catch (Exception e) {
      return null;
    }
    return date;
  }

  public static String[] getStrings(HttpServletRequest request, String key)
  {
    String[] pv = request.getParameterValues(key);

    return pv;
  }

  public static int[] getInts(HttpServletRequest request, String key) {
    String[] strs = getStrings(request, key);
    int[] array = new int[strs.length];
    for (int i = 0; i < strs.length; ++i) {
      try {
        array[i] = Integer.parseInt(strs[i]);
      } catch (Exception e) {
        array[i] = 0;
      }
    }
    return array;
  }

  public static double[] getDoubles(HttpServletRequest request, String key) {
    String[] strs = getStrings(request, key);
    double[] array = new double[strs.length];
    for (int i = 0; i < strs.length; ++i) {
      try {
        array[i] = Double.parseDouble(strs[i]);
      } catch (Exception e) {
        array[i] = 0.0D;
      }
    }
    return array;
  }

  public static long[] getLongs(HttpServletRequest request, String key) {
    String[] strs = getStrings(request, key);
    long[] array = new long[strs.length];
    for (int i = 0; i < strs.length; ++i) {
      try {
        array[i] = Long.parseLong(strs[i]);
      } catch (Exception e) {
        array[i] = 0L;
      }
    }
    return array;
  }

  public static Date[] getDates(HttpServletRequest request, String key) {
    String[] strs = getStrings(request, key);
    Date[] array = new Date[strs.length];
    for (int i = 0; i < strs.length; ++i) {
      try {
        array[i] = DateUtil.stringToDate(strs[i]);
      } catch (Exception e) {
        array[i] = null;
      }
    }
    return array;
  }

  public static String getLan(HttpServletRequest request) {
    HttpSession session = request.getSession();
    Locale localeActrual = (Locale)session.getAttribute("org.apache.struts.action.LOCALE");
    return localeActrual.getLanguage();
  }
  
  //参数转换成map
  public static Map<String,Object> paramsAsMap(HttpServletRequest req){
	  Map<String, String[]> parameterMap = req.getParameterMap();
	  Map<String,Object> map = new HashMap<String,Object>();
	  for (String key : parameterMap.keySet()) {
		  String[] ps = parameterMap.get(key);
		  if(ps!=null&&ps.length==1){
			  map.put(key, ps[0]);
		  }else{
			  map.put(key, ps);
		  }
	}
	  return map;
  }
  
  /**
   * 获取cookie
   * @param req
   * @param key
   * @param path
   * @return
   */
  public static Cookie getCookie(HttpServletRequest req,String key,String path){
	  Cookie[] cookies = req.getCookies();
	  if(cookies!=null){
		  for (Cookie cookie : cookies) {
			if(cookie.getName().equals(key)){
				if(path==null){
					return cookie;
				}
				if(path!=null&&cookie.getPath().equals(path)){
					return cookie;
				}
			}
		}
	  }
	  return null;
  }
  
  public static Cookie getCookie(HttpServletRequest req,String key){
	  return getCookie(req, key,null);
  }
  public static String getCookieStr(HttpServletRequest req,String key,String path){
	  Cookie cookie = getCookie(req, key,path);
	  return cookie==null?null:cookie.getValue();
  }
  public static String getCookieStr(HttpServletRequest req,String key){
	  Cookie cookie = getCookie(req, key,null);
	  return cookie==null?null:cookie.getValue();
  }

  public static void main(String[] args)
  {
    String str = "";
    Integer.valueOf(str).intValue();
  }
}