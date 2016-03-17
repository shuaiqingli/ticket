package com.hengyu.ticket.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.hengyu.ticket.entity.API;


public class APIUtil {
	
	
	/**
	 * 对象转字符
	 * @param source
	 * 转换对象
	 * @param w
	 * 输出对象
	 * @param filterClass
	 * 过滤的类型
	 * @param isinclude
	 * 是否包含
	 * @param properties
	 * 过滤的字段
	 * @throws IOException
	 */
	public static void toJSONString(Object source,Writer w,Class<?> filterClass,String  properties,boolean isinclude) throws IOException{
		toJSON(w, source, new Class[]{filterClass}, new String[]{properties},isinclude,false);
	}
	
	public static void toJSONString(Object source,Writer w,Class<?> filterClass,String  properties) throws IOException{
		toJSON(w, source, new Class[]{filterClass}, new String[]{properties},false,false);
	}
	
	public static void toJSONString(Object source,Writer w) throws IOException{
		toJSON(w, source, null, null,false,false);
	}

	
	/**
	 * json转换
	 * @param 
	 * 转换的对象
	 * @param filterClass
	 * 过滤的实体类型
	 * @param isinclude
	 * 是否包含属性， true 包含 ，false 不包含
	 * @param properties
	 * 过滤的属性,逗号分隔  ---  a,b,c
	 * @param w
	 */
	public static void toJSON(Writer w,Object source,Class<?>[] filterClass,String[]  properties,boolean isinclude,boolean isformat){
		try {
			DecimalFormat df = null;
			SerializeConfig sc = null;
			if(isformat){
				df = new DecimalFormat("0.0");
				final DecimalFormat tempdf = df;
				sc = new SerializeConfig();
				sc.put(BigDecimal.class,new BigDecimalCodec(){
					@Override
					public void write(JSONSerializer a, Object b, Object c, Type d, int e) throws IOException {
						b = new BigDecimal(tempdf.format(b));
						super.write(a, b, c, d, e);
					}
				});
			}
			
			String jsonString = "";
			if(filterClass!=null&&properties!=null){
				int index = 0;
				SimplePropertyPreFilter[] sppfs = new SimplePropertyPreFilter[filterClass.length];
				for (Class<?> cls : filterClass) {
					if(properties==null){
						break;
					}
					String props = "";
					try {
						props = properties[index];
					} catch (Exception e) {
						Log.info(APIUtil.class,e," ===》类型数组和属性数组不一致...");
					}
					if(sppfs==null||sppfs.length==0){
						continue;
					}
					sppfs[index] = new SimplePropertyPreFilter(cls);
						String[] ps = props==null?new String[]{}:props.split(",");
						for (String p : ps) {
							if(isinclude){
								sppfs[index].getIncludes().add(p);
							}else{
								sppfs[index].getExcludes().add(p);
							}
						}
				}
				if(isformat){
					jsonString = JSON.toJSONString(source,sc,sppfs,SerializerFeature.PrettyFormat);
				}else{
					jsonString = JSON.toJSONString(source,sppfs);
				}
			}else{
				if(isformat){
					jsonString = JSON.toJSONString(source,sc,SerializerFeature.PrettyFormat);
				}else{
					jsonString = JSON.toJSONString(source);
				}
			}
			Log.info(jsonString);
			w.write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {w.close();} catch (IOException e) {}
		}
	}
	
	/**
	 * json转换
	 * @param 
	 * 接口通用的实体
	 * @param filterClass
	 * 过滤的实体类型
	 * @param properties
	 * 过滤的属性,逗号分隔  ---  a,b,c
	 * @param w
	 */
	public static void APIToJSON(API api,Writer w,Class<?>[] filterClass,String[]  properties){
		toJSON(w, api, filterClass, properties,false,false);
	}
	
	
	public static void toJSON(API api,Writer w){
		APIToJSON(api, w, null,null);
	}
	
	//格式化json
	public static void toJSON(API api,Writer w,boolean isformat) throws IOException{
		toJSON(w, api, null, null, false, isformat);
	}
	
	/**
	 * json转换
	 * @param api
	 * 接口通用的实体
	 * @param filterClass
	 * 过滤的实体类型
	 * @param properties
	 * 过滤的属性,逗号分隔  ---  a,b,c
	 * @param w
	 */
	public static void APIToJSON(List<?> list,Writer w,Class<?> filterClass,String properties){
		API api = new API();
		api.setDatas(list);
		APIToJSON(api,w,new Class[]{filterClass},new String[]{properties});
	}
	
	public static void APIToJSON(List<?> list,Writer w){
		APIToJSON(list,w,null,null);
	}
	
	/**
	 * json转换
	 * @param obj
	 * 转换对象
	 * @param filterClass
	 * 过滤的实体类型
	 * @param properties
	 * 过滤的属性,逗号分隔  ---  a,b,c
	 * @param w
	 */
	@SuppressWarnings("unchecked")
	public static void APIToJSON(Writer w,Object obj,Class<?> filterClass,String properties){
		API api = new API();
		api.getDatas().add(obj);
		APIToJSON(api, w,new Class[]{filterClass},new String[]{properties});
	}
	
	
	@SuppressWarnings("unchecked")
	public static void APIToJSON(Writer w,Object obj){
		API api = new API();
		api.getDatas().add(obj);
		APIToJSON(api, w, null, null);
	}
	
	/**
	 * 错误消息处理
	 * @param w
	 * @param objs
	 * @return
	 * @throws IOException
	 */
	public static boolean isNotNull(Writer w,Object... objs) throws IOException{
		API api = new API();
		if(objs!=null){
			for (Object o : objs) {
				if(o==null||o.toString().isEmpty()){
					api.setCode(5006);
					String jsonString = JSON.toJSONString(api);
					w.write(jsonString);
					w.close();
					Log.info("\n\n",jsonString,"\n");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 错误消息处理
	 * @param api
	 * @param w
	 * @param br
	 * @return
	 */
	public static boolean isNotError(Writer w,BindingResult... br){
		try {
			API api = new API();
			List<String> errors = new ArrayList<String>();
			for (BindingResult bindingResult : br) {
				if (bindingResult.hasErrors()) {
					List<FieldError> fe =bindingResult.getFieldErrors();
					for (FieldError f : fe) {
						errors.add(f.getField()+f.getDefaultMessage());
					}
				}
			}
			api.setMsg(errors.toString());
			String jsonString = JSON.toJSONString(api);
			Log.info("\n\n",jsonString,"\n");
			w.write(jsonString);
			w.close();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * 获取JSON数据
	 * @param key
	 * @param source
	 * @return
	 */
	public static String getJSONVal(String key,String json){
		if(key!=null&&key.trim().isEmpty()==false&&key.indexOf(".")!=-1&&json!=null&&json.trim().isEmpty()==false){
			String[] ks = key.split("[.]");
			List<String> list = Arrays.asList(ks);
			return getJSONVal(json,list.iterator());
		}else if(key!=null&&key.trim().isEmpty()==false){
			JSONObject p = (JSONObject) JSONObject.parse(json);
			return p.get(key).toString();
		}
		return null;
	}
	
	/**
	 * 获取JSON数据
	 * @param source
	 * @param ks
	 * @return
	 */
	private static String getJSONVal(Object source,Iterator<String> ks){
		if(ks.hasNext()){
			JSONObject p = (JSONObject) JSONObject.parse(source.toString());
			source = p.get(ks.next());
			if(source==null){
				return null;
			}
			return getJSONVal(source,ks);
		}
		return  source.toString();
	}
	
}
