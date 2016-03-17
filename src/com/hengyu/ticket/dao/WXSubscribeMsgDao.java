package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.WXSubscribeMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李冠锋 2016-02-24
 *
 */
public interface WXSubscribeMsgDao {
	
	/**
	 * 保存一个对象
	 * @param wxSubscribeMsg
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(WXSubscribeMsg wxSubscribeMsg) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param wxSubscribeMsg
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(WXSubscribeMsg wxSubscribeMsg) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回WxSubscribeMsg对象
	 * @throws Exception
	 */
	WXSubscribeMsg find(Integer id) throws Exception;

	List<WXSubscribeMsg> findList(Page page) throws Exception;
	
	long findListCount(Page page) throws Exception;

	List<Map<String,String>> findSubscribeMsg() throws Exception;

}
