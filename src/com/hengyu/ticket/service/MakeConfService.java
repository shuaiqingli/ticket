package com.hengyu.ticket.service;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.MakeConfDao;
import com.hengyu.ticket.entity.MakeConf;
import com.hengyu.ticket.exception.BaseException;
import com.hengyu.ticket.util.Log;
import com.hengyu.ticket.util.NumberCreate;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
 @Service
public class MakeConfService{
	
	@Autowired
	private MakeConfDao makeConfDao;
	
	/**
	 * 保存一个对象
	 * @param makeConf
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(MakeConf makeConf) throws Exception{
		return makeConfDao.save(makeConf);
	}
	
	/**
	 * 更新一个对象
	 * @param makeConf
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(MakeConf makeConf) throws Exception{
		return makeConfDao.update(makeConf);
	}
	
	//获取主键生成配置，主键自动加1
	public <k,v> k findMakeConf(v obj,Execute<k,v> e) throws Exception{
		return findMakeConf(obj,null,null,true,e);
	}
	
	//获取主键生成配置，主键自动加1
	public <k,v> k findMakeConf(v obj,Integer num,Execute<k,v> e) throws Exception{
		return findMakeConf(obj,num,null,true,e);
	}
	
	
	/**
	 * 获取主键生成配置
	 * @param obj
	 * 类名，或自定义字符主键
	 * @param num
	 * 不够位数补0
	 * @param initKeyVal
	 * 如果没有创建主键的初始值 
	 * @param isAutoUpdateVal
	 * 查询后主键是否自动+1
	 * @param e 
	 * 	执行自己的逻辑代码
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <k,v> k findMakeConf(v obj,Integer num,Integer initKeyVal,boolean isAutoUpdateVal,Execute<k,v> e) throws Exception{
		if(num==null){
			num = 0;
		}
		String name = null;
		if(obj.getClass()  == String.class){
			name = obj.toString();
		}else{
			name = obj.getClass().getSimpleName();
		}
		MakeConf mc = makeConfDao.find(name);
		if (mc == null) {
			mc = new MakeConf();
			if(initKeyVal!=null){
				mc.setCurrentValue(initKeyVal);
			}else{
				mc.setCurrentValue(1);
			}
			mc.setTableName(name);
			makeConfDao.save(mc);
		}
		String id = NumberCreate.createNumber(mc.getCurrentValue(), num);
		if(isAutoUpdateVal){
			//并发生成id处理
			for (int i = 0; i < 5; i++) {
				int updateVal = makeConfDao.updateVal(mc);
				if(updateVal==1){
					return e.execute(mc,obj,id);
				}else{
					mc.setCurrentValue(mc.getCurrentValue()+1);
					Log.error(this.getClass(),"=========**** 并发处理 **** =========");
				}
			}
			//
			throw new BaseException();
		}
		return (k) id;
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param tableName 主键
	 * @return 返回MakeConf对象
	 * @throws Exception
	 */
	public MakeConf find(String tableName) throws Exception{
		return makeConfDao.find(tableName);
	}
	
	public void setMakeConfDao(MakeConfDao makeConfDao){
		this.makeConfDao = makeConfDao;
	}
}

/**
 * 查询配置接口
 * @author LGF
 *
 */
interface Execute<k,v>{
	 k execute(MakeConf mc,v o,String id) throws Exception;
}
